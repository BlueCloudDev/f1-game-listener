package OCIStreaming;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.json.JSONArray;

public class OCIStreamingMessage {
  public String key;
  public String value;

  public OCIStreamingMessage(String _key, String packetHeaderJson, String bodyJson) throws UnsupportedEncodingException {
    super();
    JSONArray ja = new JSONArray();
    ja.put(0, packetHeaderJson);
    ja.put(1, bodyJson);
    key = Base64.getEncoder().encodeToString(_key.getBytes("UTF-8"));
    value = Base64.getEncoder().encodeToString(ja.toString().getBytes("UTF-8"));
  }

}
