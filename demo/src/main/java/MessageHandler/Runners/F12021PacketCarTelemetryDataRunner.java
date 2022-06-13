package MessageHandler.Runners;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Map;

import com.google.gson.Gson;

import F12021Packet.F12021CarTelemetryData;
import F12021Packet.F12021PacketCarTelemetryData;
import F12021Packet.F12021PacketFactory;
import F12021Packet.F12021PacketHeader;
import MessageHandler.F12021UDPPacketHandler;
import OCIStreaming.OCIStreamingMessage;

public class F12021PacketCarTelemetryDataRunner implements Runnable {
  private static final Logger logger = LogManager.getLogger(F12021PacketCarTelemetryDataRunner.class);
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
      F12021PacketCarTelemetryData cartelemetry = factory.CreatePacketCarTelemetryData(bb);
      if (!sessionParticipants.containsKey(header.SessionUID)) {
        return;
      }
      var numActiveCars = sessionParticipants.get(header.SessionUID);
      if (numActiveCars <= 0) {
        return;
      }
      F12021CarTelemetryData[] trunc3 = new F12021CarTelemetryData[numActiveCars];
      for (int i = 0; i < numActiveCars; i++) {
        trunc3[i] = cartelemetry.CarTelemetryData[i];
      }
      cartelemetry.CarTelemetryData = trunc3;
      var body = gson.toJson(cartelemetry);
      var msg = new OCIStreamingMessage(key, headerJson, body);
      F12021UDPPacketHandler.AddMessage(msg);
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
