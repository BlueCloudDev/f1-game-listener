package MessageHandler.Runners;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Map;

import com.google.gson.Gson;

import F12021Packet.F12021LapData;
import F12021Packet.F12021PacketFactory;
import F12021Packet.F12021PacketHeader;
import F12021Packet.F12021PacketLapData;
import MessageHandler.F12021UDPPacketHandler;
import OCIStreaming.OCIStreamingMessage;
import UDPServer.UDPServer;
import Configuration.Configuration;

public class F12021PacketLapDataRunner implements Runnable {
  private static final Logger logger = LogManager.getLogger(F12021PacketLapDataRunner.class);
  private F12021PacketFactory factory = new F12021PacketFactory();
  public Map<String, Integer> sessionParticipants = null;
  public F12021PacketHeader header;
  public ByteBuffer bb;
  private Gson gson = new Gson();
  public String key = null;
  public String headerJson = null;

  @Override
  public void run() {
    try {
      F12021PacketLapData lap = factory.CreatePacketLapData(bb);
      if (!Configuration.EnvVars.get("UDP_SERVER").equals("")) {
        var udp = new UDPServer();
        udp.UpdateLapData(lap);
      }
      if (!sessionParticipants.containsKey(header.SessionUID)) {
        return;
      }
      var numActiveCars = sessionParticipants.get(header.SessionUID);
      if (numActiveCars <= 0) {
        return;
      }
      F12021LapData[] trunc1 = new F12021LapData[numActiveCars];
      for (int i = 0; i < numActiveCars; i++) {
        trunc1[i] = lap.LapData[i];
      }
      lap.LapData = trunc1;
      var body = gson.toJson(lap);
      var msg = new OCIStreamingMessage(key, headerJson, body);
      F12021UDPPacketHandler h = new F12021UDPPacketHandler(true);
      h.AddMessage(msg);
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
