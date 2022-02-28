package OCIStreaming;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

public class OCIStreamingMessage {
  public String Build(String key, String headerJson, String body) throws UnsupportedEncodingException {
    JSONArray ja = new JSONArray();
    ja.put(0, headerJson);
    ja.put(1, body);
    JSONObject payload = new JSONObject();
    JSONArray msgs = new JSONArray();
    JSONObject msg = new JSONObject();
    msg.put("key", Base64.getEncoder().encodeToString(key.getBytes("UTF-8")));
    String enc = Base64.getEncoder().encodeToString(ja.toString().getBytes("UTF-8"));
    msg.put("value", enc);
    msgs.put(msg);
    payload.put("messages", msgs);
    return payload.toString();
  }
}
