package OCIStreaming;

import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import F12020Packet.F12020PacketMotionData;
import F12020Packet.F12020PacketParticipantData;
import F12020Packet.F12020PacketSessionData;
import F12020Packet.F12020ParticipantData;
import Repository.CarSetupDataRepository;
import Repository.CarStatusDataRepository;
import Repository.CarTelemetryRepository;
import Repository.LapDataRepository;
import Repository.MotionDataRepository;
import Repository.OracleDataSourceProvider;
import Repository.PacketHeaderRepository;
import Repository.ParticipantDataRepository;
import Repository.SessionDataRepository;
import oracle.jdbc.pool.OracleDataSource;
import F12020Packet.F12020CarSetupData;
import F12020Packet.F12020CarStatusData;
import F12020Packet.F12020CarTelemetryData;
import F12020Packet.F12020LapData;
import F12020Packet.F12020LobbyInfoData;
import F12020Packet.F12020PacketCarSetupData;
import F12020Packet.F12020PacketCarStatusData;
import F12020Packet.F12020PacketCarTelemetryData;
import F12020Packet.F12020PacketEventData;
import F12020Packet.F12020PacketFinalClassificationData;
import F12020Packet.F12020PacketHeader;
import F12020Packet.F12020PacketLapData;

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
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

public class OCIStreaming {
  private CloseableHttpClient  httpClient; 
  private Map<String, String> config = new HashMap<String, String>();
  private String streamID = "ocid1.stream.oc1.uk-london-1.amaaaaaaywfcc6aabs22e3r4cwsqmh63gjej4lhsybgtdnfbogxckwp3cdha";
  private String host = "cell-1.streaming.uk-london-1.oci.oraclecloud.com";
  private String configFilePath = "/home/opc/.oci/config";
  private String cursorID = "";
  private static long offset = 0;

  public OCIStreaming() throws OCIStreamingException, Exception {
    
    config = new HashMap<String, String>(); 
    if(!readEnvVariables()) {
      OCIStreamingException ex = new OCIStreamingException("Error reading configuration file");
      throw ex;
    }
    httpClient = HttpClients.custom()
    .setMaxConnTotal(100)
    .setMaxConnPerRoute(20)
    .build();
    cursorID = getCursorID("AT_TIME", offset);
  }

  public void SendMessage(String body) throws IOException, ClientProtocolException, Exception {
    //BODY MUST END IN \n
    HttpUriRequest req = buildPostRequest(body, "messages");
    if (req != null) {
      try (CloseableHttpResponse response = httpClient.execute(req)) {
        EntityUtils.consumeQuietly(response.getEntity());
      }
      //System.out.println(res);
    }
  }

  

  public void GetMessage() throws IOException, ClientProtocolException, SQLException, Exception {
    OracleDataSourceProvider odsp = new OracleDataSourceProvider();
    OracleDataSource ods = odsp.GetOracleDataSource();
    
    HttpUriRequest req = buildGetRequest();
    HttpResponse resp = httpClient.execute(req);
    HttpEntity entity = resp.getEntity();
    String result = EntityUtils.toString(entity);
    if (offset > 0) {
      cursorID = getCursorID("AFTER_OFFSET", offset);
    }
    if (!result.equals("[]")) {
      if (!result.startsWith("[")){
        System.out.println(result);
        return;
      }
      JSONArray respBody = new JSONArray(result);
      List<Integer> range = new ArrayList<Integer>();
      for (int i = 0; i < respBody.length(); i++) {
        range.add(i);
      }
      range.parallelStream().forEach(number ->
        processEntry((JSONObject)respBody.get(number), ods)
      );
      /*for(int i=0; i<respBody.length(); i++) {
        JSONObject msg = respBody.getJSONObject(i);
        
      }*/
    }
  }

  public void processEntry(JSONObject msg, OracleDataSource ods) {
        Gson gson = new Gson();
        long msgOffset = msg.getLong("offset");
        if (offset < msgOffset) {
          offset = msgOffset;
        }
        //String key = Base64Decode(msg.getString("key"));
        //String[] keyItems = key.split(",");
        //int packetId =  Integer.parseInt(keyItems[0]);
        String value = Base64Decode(msg.getString("value"));
        if (!value.startsWith("[")) {
          return;
        }
        JSONArray ja = new JSONArray(value);
        if (ja.length() == 0 || !ja.get(0).toString().startsWith("{")) {
          return;
        }
        F12020PacketHeader header = gson.fromJson(ja.get(0).toString(), F12020PacketHeader.class);
        PacketHeaderRepository prepo = new PacketHeaderRepository();
        long id = prepo.InsertPacketHeader(header, ods);
        if (id == 0) {
          return;
        }
        switch (header.PacketId) {
          case 0:
            F12020PacketMotionData p = gson.fromJson(ja.get(1).toString(), F12020PacketMotionData.class);
            MotionDataRepository mrepo = new MotionDataRepository();
            for(int j = 0; j < p.CarMotionData.length; j++) {
              long mdid = mrepo.InsertMotionData(id, p.CarMotionData[j], ods);
              if (j == (int)header.PlayerCarIndex){
                mrepo.InsertMotionDataPlayer(mdid, p, ods);
              }
            }
            break;
          case 1:
            F12020PacketSessionData p1 = gson.fromJson(ja.get(1).toString(), F12020PacketSessionData.class);
            SessionDataRepository repo = new SessionDataRepository();
            repo.InsertSessionData(id, p1, ods);
            break;
          case 2:
            F12020PacketLapData p2 = gson.fromJson(ja.get(1).toString(), F12020PacketLapData.class);
            LapDataRepository repo2 = new LapDataRepository();
            for (F12020LapData data : p2.LapData) {
              repo2.InsertLapData(id, data, ods);
            }
            break;
          case 3:
            F12020PacketEventData p3 = gson.fromJson(ja.get(1).toString(), F12020PacketEventData.class);
            break;
          case 4:
            F12020PacketParticipantData p4 = gson.fromJson(ja.get(1).toString(), F12020PacketParticipantData.class);
            ParticipantDataRepository repo3 = new ParticipantDataRepository();
            for (F12020ParticipantData data : p4.ParticipantData) {
              repo3.InsertParticipantData(id, data, ods);
            }
            break;
          case 5:
            F12020PacketCarSetupData p5 = gson.fromJson(ja.get(1).toString(), F12020PacketCarSetupData.class);
            CarSetupDataRepository repo4 = new CarSetupDataRepository();
            for (F12020CarSetupData data : p5.CarSetups) {
              repo4.InsertCarSetupData(id, data, ods);
            }
            break;
          case 6:
            F12020PacketCarTelemetryData p6 = gson.fromJson(ja.get(1).toString(), F12020PacketCarTelemetryData.class);
            CarTelemetryRepository repo5 = new CarTelemetryRepository();
            for (F12020CarTelemetryData data : p6.CarTelemetryData) {
              if (data != null) {
                repo5.InsertCarTelemetryData(id, data, ods);
              }
            }
            break;
          case 7:
            F12020PacketCarStatusData p7 = gson.fromJson(ja.get(1).toString(), F12020PacketCarStatusData.class);
            CarStatusDataRepository repo6 = new CarStatusDataRepository();
            for (F12020CarStatusData data : p7.CarStatusData) {
              repo6.InsertCarStatusData(id, data, ods);
            }
            break;  
          case 8:
            F12020PacketFinalClassificationData p8 = gson.fromJson(ja.get(1).toString(), F12020PacketFinalClassificationData.class);
            break;
          case 9:
            F12020LobbyInfoData p9 = gson.fromJson(ja.get(1).toString(), F12020LobbyInfoData.class);
            break;
        }
  }

  public static String Base64Decode(String base64Encoded) {
    byte[] bytes = Base64.getDecoder().decode(base64Encoded);
    return new String(bytes);
  }

  public HttpUriRequest buildPostRequest(String body, String endpoint) throws URISyntaxException, Exception {
    body = body + "\n";
    String restAPI = "/20180418/streams/"+ streamID + "/" + endpoint;
    DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
    ZonedDateTime now = ZonedDateTime.now();
    String dateRFC1123Now = dtf.format(now);
    String bodyDigest = digestBody(body);
    String bodyDigestLength = String.valueOf(body.length());
    String headers = "(request-target) date host x-content-sha256 content-type content-length";
    String requestTarget = "(request-target): post " + restAPI;
    String dateHeader = "date: " + dateRFC1123Now;
    String hostHeader = "host: " + host;
    String contentSha256Header = "x-content-sha256: " + bodyDigest;
    String contentTypeHeader = "content-type: application/json";
    String contentLengthHeader = "content-length: " + bodyDigestLength;
    String signature = postSignature(requestTarget, dateHeader, hostHeader, contentSha256Header, contentTypeHeader, contentLengthHeader, config.get("key_file"));
    String payload = "Signature version=\"1\",keyId=\"" + config.get("tenancy") + "/" + config.get("user") + "/" + config.get("fingerprint") + "\",algorithm=\"rsa-sha256\",headers=\"" + headers + "\",signature=\"" + signature +"\"";

    URI url = new URI("https://" + host + restAPI);
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
      String restAPI = "/20180418/streams/" + streamID + "/messages?cursor="+cursorID;
      
      DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
      ZonedDateTime now = ZonedDateTime.now();
      String dateRFC1123Now = dtf.format(now);
      String headers = "(request-target) date host";
      String requestTarget = "(request-target): get " + restAPI;
      String dateHeader = "date: " + dateRFC1123Now;
      String hostHeader = "host: " + host;
      String signature = getSignature(requestTarget, dateHeader, hostHeader, config.get("key_file"));
      String payload = "Signature version=\"1\",keyId=\"" + config.get("tenancy") + "/" + config.get("user") + "/" + config.get("fingerprint") + "\",algorithm=\"rsa-sha256\",headers=\"" + headers + "\",signature=\"" + signature +"\"";

      URI url = new URI("https://" + host + restAPI);
      request = RequestBuilder.get()
      .setUri(url)
      .setHeader("content-type", "application/json")
      .setHeader("date", dateRFC1123Now)
      .setHeader("Authorization", payload)
      .build();
    } catch (Exception ex) {
      ex.printStackTrace();
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
    body.put("offset", offset);
    if (type.equals("AT_TIME")){
      //One hour back
      //Date startTime = new Date(System.currentTimeMillis() - 3600 * 1000);
      Date startTime = new Date(System.currentTimeMillis() - 60 * 1000);
      String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(startTime);
      body.put("time", timestamp);
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
      ex.printStackTrace();
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
      ex.printStackTrace();
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

  private boolean readEnvVariables() {
    String[] vars = new String[]{"key_file", "fingerprint", "tenancy", "user"};
    try {
      File myObj = new File(configFilePath);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        if (data.contains("=")){
          String[] kv = data.split("=");
          config.put(kv[0], kv[1]);
        }
      }
      for (String var : vars) {
        if (!config.containsKey(var) || config.get(var) == "") {
          System.out.println(var + "is missing or invalid from " + configFilePath); 
          return false;
        }
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();  
      return false;
    }
    return true;
  }
}
