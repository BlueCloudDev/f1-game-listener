package OCIStreaming;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

public class OCIStreamingMessage {
  public String Build(String key, String json) throws UnsupportedEncodingException {
    JSONObject payload = new JSONObject();
    JSONArray msgs = new JSONArray();
    JSONObject msg = new JSONObject();
    msg.put("key", Base64.getEncoder().encodeToString(key.getBytes("UTF-8")));
    String enc = Base64.getEncoder().encodeToString(json.getBytes("UTF-8"));
    msg.put("value", enc);
    msgs.put(msg);
    payload.put("messages", msgs);
    return payload.toString();
  }
}
