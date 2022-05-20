package OCIStreaming;

import org.apache.http.HttpResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import Configuration.Configuration;
import F12021Packet.F12021PacketHeader;
import MessageHandler.F12020MessageHandler;
import MessageHandler.F12021MessageHandler;
import Repository.F12020.OracleDataSourceProvider;
import Repository.F12021.PacketHeaderRepository2021;
import oracle.ucp.jdbc.PoolDataSource;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.MessageDigest;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OCIStreaming {
  private static final Logger logger = LogManager.getLogger(OCIStreaming.class);
  private static PoolDataSource pds = null;
  private CloseableHttpClient  httpClient; 
  private final String APPLICATION_MODE = Configuration.EnvVars.get("APPLICATION_MODE");
  private final String API_HOST = Configuration.EnvVars.get("API_HOST");
  private final String STREAM_OCID = Configuration.EnvVars.get("STREAM_OCID");
  private final String TENANCY_OCID = Configuration.EnvVars.get("TENANCY_OCID");
  private final String USER_OCID = Configuration.EnvVars.get("USER_OCID");
  private final String FINGERPRINT = Configuration.EnvVars.get("FINGERPRINT");
  private final String OCI_PRIV_KEY_PATH = Configuration.EnvVars.get("OCI_PRIV_KEY_PATH");
  private String cursorID = "";
  private static long offset = 0;

  public OCIStreaming() throws OCIStreamingException, Exception {
    httpClient = HttpClients.custom()
    .setMaxConnTotal(100)
    .setMaxConnPerRoute(20)
    .build();
    if (pds == null) {
      OracleDataSourceProvider odsp = new OracleDataSourceProvider();
      pds = odsp.GetOraclePoolDataSource();
    }
    if (APPLICATION_MODE.equals("consumer") && OCIStreaming.offset == 0) {
      cursorID = getCursorID("AT_TIME", offset);
    }
  }

  public void SendMessage(String body) throws IOException, ClientProtocolException, Exception {
    //BODY MUST END IN \n
    HttpUriRequest req = buildPostRequest(body, "messages");
    if (req != null) {
      try (CloseableHttpResponse response = httpClient.execute(req)) {
        EntityUtils.consumeQuietly(response.getEntity());
        var string = response.getStatusLine();
        System.out.println(string);
      }
    }
  }

  

  public void GetMessage() throws IOException, ClientProtocolException, SQLException, Exception {
    
    if (OCIStreaming.offset > 0) {
      cursorID = getCursorID("AFTER_OFFSET", OCIStreaming.offset);
    }
    HttpUriRequest req = buildGetRequest();
    HttpResponse resp = httpClient.execute(req);
    HttpEntity entity = resp.getEntity();
    String result = EntityUtils.toString(entity);
    if (!result.equals("[]")) {
      if (!result.startsWith("[")){
        System.out.println(result);
        logger.warn("Invalid message format received: " + result);
        return;
      }
      JSONArray respBody = new JSONArray(result);
      List<Integer> range = new ArrayList<Integer>();
      for (int i = 0; i < respBody.length(); i++) {
        range.add(i);
      }
      range.parallelStream().forEach(number ->
        processEntry((JSONObject)respBody.get(number), pds)
      );
    }
  }

  public void processEntry(JSONObject msg, PoolDataSource pds) {
    Gson gson = new Gson();
    long msgOffset = msg.getLong("offset");
    if (offset < msgOffset) {
      offset = msgOffset;
    }

    String value = Base64Decode(msg.getString("value"));
    if (!value.startsWith("[")) {
      logger.warn("Invalid message format received: " + value);
      return;
    }
    JSONArray ja = new JSONArray(value);
    if (ja.length() == 0 || !ja.get(0).toString().startsWith("{")) {
      logger.warn("Invalid message format received: " + value);
      return;
    }
    F12021PacketHeader header = gson.fromJson(ja.get(0).toString(), F12021PacketHeader.class);
    PacketHeaderRepository2021 prepo = new PacketHeaderRepository2021();
    long id = prepo.InsertPacketHeader(header, pds);
    if (id == 0) {
      return;
    }
    if (header.PacketFormat == 2020) {
      F12020MessageHandler msgHandler = new F12020MessageHandler();
      msgHandler.ProcessMessage(header, id, ja, pds);
    } else if (header.PacketFormat == 2021) {
      F12021MessageHandler msgHandler = new F12021MessageHandler();
      msgHandler.ProcessMessage(header, id, ja, pds);
    }
    
  }

  public static String Base64Decode(String base64Encoded) {
    byte[] bytes = Base64.getDecoder().decode(base64Encoded);
    return new String(bytes);
  }

  public HttpUriRequest buildPostRequest(String body, String endpoint) throws URISyntaxException, Exception {
    body = body + "\n";
    String restAPI = "/20180418/streams/"+ STREAM_OCID + "/" + endpoint;
    DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
    ZonedDateTime now = ZonedDateTime.now();
    String dateRFC1123Now = dtf.format(now);
    String bodyDigest = digestBody(body);
    String bodyDigestLength = String.valueOf(body.length());
    String headers = "(request-target) date host x-content-sha256 content-type content-length";
    String requestTarget = "(request-target): post " + restAPI;
    String dateHeader = "date: " + dateRFC1123Now;
    String hostHeader = "host: " + API_HOST;
    String contentSha256Header = "x-content-sha256: " + bodyDigest;
    String contentTypeHeader = "content-type: application/json";
    String contentLengthHeader = "content-length: " + bodyDigestLength;
    String signature = postSignature(requestTarget, dateHeader, hostHeader, contentSha256Header, contentTypeHeader, contentLengthHeader, OCI_PRIV_KEY_PATH);
    String payload = "Signature version=\"1\",keyId=\"" + TENANCY_OCID + "/" + USER_OCID + "/" + FINGERPRINT + "\",algorithm=\"rsa-sha256\",headers=\"" + headers + "\",signature=\"" + signature +"\"";

    URI url = new URI("https://" + API_HOST + restAPI);
    StringEntity entity = new StringEntity(body);
    entity.setContentType("application/json");
    HttpUriRequest request = RequestBuilder.post()
      .setUri(url)
      .setHeader("date", dateRFC1123Now)
      .setHeader("x-content-sha256", bodyDigest)
      .setHeader("content-type", "application/json")
      //.setHeader("content-length", bodyDigestLength)
      .setHeader("Authorization", payload)
      .setEntity(entity)
      .build();
    return request;
  }

  public HttpUriRequest buildGetRequest() {
    HttpUriRequest request = null;
    try {
      String restAPI = "/20180418/streams/" + STREAM_OCID + "/messages?cursor="+cursorID;
      
      DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
      ZonedDateTime now = ZonedDateTime.now();
      String dateRFC1123Now = dtf.format(now);
      String headers = "(request-target) date host";
      String requestTarget = "(request-target): get " + restAPI;
      String dateHeader = "date: " + dateRFC1123Now;
      String hostHeader = "host: " + API_HOST;
      String signature = getSignature(requestTarget, dateHeader, hostHeader, OCI_PRIV_KEY_PATH);
      String payload = "Signature version=\"1\",keyId=\"" + TENANCY_OCID + "/" + USER_OCID + "/" + FINGERPRINT + "\",algorithm=\"rsa-sha256\",headers=\"" + headers + "\",signature=\"" + signature +"\"";

      URI url = new URI("https://" + API_HOST + restAPI);
      request = RequestBuilder.get()
      .setUri(url)
      .setHeader("content-type", "application/json")
      .setHeader("date", dateRFC1123Now)
      .setHeader("Authorization", payload)
      .build();
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return request;
  }

  public String getCursorID(String type, long offset) throws URISyntaxException, IOException, ParseException, Exception {
    String cursorID = "";
    String cursBody = getCursorRequestBody(type, offset);
    HttpUriRequest cursReq = buildPostRequest(cursBody, "cursors");
    HttpResponse cursResp = httpClient.execute(cursReq);
    HttpEntity cursEntity = cursResp.getEntity();
    String cursRes = EntityUtils.toString(cursEntity);
    JSONObject cursRespBody = new JSONObject(cursRes);
    if (cursRespBody.has("value")) {
      cursorID = cursRespBody.getString("value");    
    }
    return cursorID;
  }

  public String getCursorRequestBody(String type, long offset) {
    JSONObject body = new JSONObject();
    body.put("partition", "0");
    body.put("type", type);
    if (type.equals("AT_TIME")){
      //One hour back
      //Date startTime = new Date(System.currentTimeMillis() - 3600 * 1000);
      Date startTime = new Date(System.currentTimeMillis() - 60 * 1000);
      String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(startTime);
      logger.info("Creating cursor at time: " + timestamp);
      body.put("time", timestamp);
    } else {
      logger.info("Creating cursor at offset: " + OCIStreaming.offset);
      body.put("offset", offset);
    }
    return body.toString();
  }

  public static String digestBody(String body) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(body.getBytes("UTF-8"));
      byte[] shaValue = md.digest();
      String result = Base64.getEncoder().encodeToString(shaValue);
      return result;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
      return "";
    }
  }

  public String postSignature(String requestTarget, String dateHeader, String hostHeader, String contentSha256Header, String contentTypeHeader, String contentLengthHeader,  String privateKeyPath) throws Exception {
    PrivateKey privateKey = getPrivateKey(privateKeyPath);
    String alg = "SHA256withRSA";
    String payload = requestTarget + "\n" + dateHeader + "\n" + hostHeader + "\n" + contentSha256Header + "\n" + contentTypeHeader + "\n" + contentLengthHeader;
    Signature sig = Signature.getInstance(alg); 
    sig.initSign(privateKey);
    
    sig.update(payload.getBytes("UTF-8"));
    byte[] signature = sig.sign();
    String base64enc = Base64.getEncoder().encodeToString(signature);
    String cleaned = base64enc.replace("\n", "").replace("\r", "");
    return cleaned;
  }

  public static String getSignature(String requestTarget, String dateHeader, String hostHeader, String privateKeyPath) {
    try {
      PrivateKey privateKey = getPrivateKey(privateKeyPath);
      String alg = "SHA256withRSA";
      String payload = requestTarget + "\n" + dateHeader + "\n" + hostHeader;
      Signature sig = Signature.getInstance(alg); 
      sig.initSign(privateKey);
      
      sig.update(payload.getBytes("UTF-8"));
      byte[] signature = sig.sign();
      String base64enc = Base64.getEncoder().encodeToString(signature);
      String cleaned = base64enc.replace("\n", "").replace("\r", "");
      return cleaned;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
      return "";
    }
  }

  public static PrivateKey getPrivateKey(String filename)
    throws Exception {

    String key = new String(Files.readAllBytes(Paths.get(filename)), Charset.defaultCharset());

    String privateKeyPEM = key
      .replace("-----BEGIN PRIVATE KEY-----", "")
      .replaceAll(System.lineSeparator(), "")
      .replace("-----END PRIVATE KEY-----", "");

    byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
    return keyFactory.generatePrivate(keySpec);
  }
}
