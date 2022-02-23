package OCIStreaming;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.MessageDigest;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.net.URI;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

public class OCIStreaming {
  private HttpClient client;
  private Map<String, String> config = new HashMap<String, String>();
  private String streamID = "ocid1.stream.oc1.us-sanjose-1.amaaaaaapwneysaavpcrulmeaiaycj6ktnuqsdyl3qacdjiq3bf4hkzsipnq";
  private String configFilePath = "/home/opc/.oci/config";

  public OCIStreaming() throws OCIStreamingException {
    config = new HashMap<String, String>(); 
    if(!readEnvVariables()) {
      OCIStreamingException ex = new OCIStreamingException("Error reading configuration file");
      throw ex;
    }
    client = HttpClients.custom().build();
  }

  public void SendMessage(String body) {
    //BODY MUST END IN \n
    body = body + "\n";
    try {
      /*String tenancyOcid = "ocid1.tenancy.oc1..aaaaaaaacg2kx2vh5y62jvq7bqpgmt7komml6rshkw4hlidt5y2su5gacyja";
      String userOcid = "ocid1.user.oc1..aaaaaaaakszp25s3driopzu3da65femkrrpy7fwjtkyb2iz2ytpelsqu2x4a";
      String privateKeyPath = "/home/opc/.oci/oci_private.pem";
      String fingerprint = "1e:29:4a:ca:7f:2c:18:44:2a:b8:1f:ff:a9:c3:a4:be";*/
      String restAPI = "/20180418/streams/"+ streamID + "/messages";
      String host = "cell-1.streaming.us-sanjose-1.oci.oraclecloud.com";
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
      HttpUriRequest request = RequestBuilder.post()
        .setUri(url)
        .setHeader("date", dateRFC1123Now)
        .setHeader("x-content-sha256", bodyDigest)
        .setHeader("content-type", "application/json")
        //.setHeader("content-length", bodyDigestLength)
        .setHeader("Authorization", payload)
        .setEntity(entity)
        .build();
      HttpResponse res = client.execute(request);
      System.out.print(res);

    } catch (Exception ex) {
      System.out.print(ex);
    } finally {

    };
  }

  public static String digestBody(String body) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(body.getBytes("UTF-8"));
      byte[] shaValue = md.digest();
      String result = Base64.getEncoder().encodeToString(shaValue);
      return result;
    } catch (Exception ex) {
      return "";
    }
  }

  public String postSignature(String requestTarget, String dateHeader, String hostHeader, String contentSha256Header, String contentTypeHeader, String contentLengthHeader,  String privateKeyPath) {
    try {
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
    } catch (Exception ex) {
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

  private  boolean readEnvVariables() {
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
