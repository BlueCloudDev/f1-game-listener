package MessageHandler.Runners;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Map;

import com.google.gson.Gson;

import F12021Packet.F12021PacketFactory;
import F12021Packet.F12021PacketHeader;
import F12021Packet.F12021PacketSessionData;
import MessageHandler.F12021UDPPacketHandler;
import OCIStreaming.OCIStreamingMessage;
import UDPServer.UDPServer;
import Configuration.Configuration;

public class F12021PacketSessionDataRunner implements Runnable {
  private static final Logger logger = LogManager.getLogger(F12021PacketSessionDataRunner.class);
  private F12021PacketFactory factory = new F12021PacketFactory();
  public F12021PacketHeader header;
  public ByteBuffer bb;
  private Gson gson = new Gson();
  public String key = null;
  public String headerJson = null;

  @Override
  public void run() {
    try {
      F12021PacketSessionData session = factory.CreatePacksetSessionData(bb);
      if (!Configuration.EnvVars.get("UDP_SERVER").equals("")) {
        var udp = new UDPServer();
        udp.UpdateSession(session);
      }
      var body = gson.toJson(session);
      var msg = new OCIStreamingMessage(key, headerJson, body);
      F12021UDPPacketHandler.AddMessage(msg);
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
