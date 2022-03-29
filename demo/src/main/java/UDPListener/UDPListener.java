package UDPListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;

import F12020Packet.F12020PacketFactory;
import F12020Packet.F12020PacketHeader;
import F12021Packet.F12021PacketFactory;
import F12021Packet.F12021PacketHeader;
import MessageHandler.F12020UDPPacketHandler;
import MessageHandler.F12021UDPPacketHandler;
import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;
import Configuration.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UDPListener {
  private static final Logger logger = LogManager.getLogger(UDPListener.class);
  CloseableHttpClient httpClient;
  private int port = Integer.parseInt(Configuration.EnvVars.get("LISTEN_PORT"));
  private static int MAX_BUFFER = 2048;
  private OCIStreaming streaming;
  

  public UDPListener() throws OCIStreamingException, Exception{
    super();
    streaming = new OCIStreaming();

  }

  public void Listen() throws SocketException, IOException, Exception {
    DatagramSocket socket = new DatagramSocket(port);
    byte[] buf = new byte[MAX_BUFFER];
    int count = 0;
    while (true) {
      DatagramPacket packet 
        = new DatagramPacket(buf, buf.length);
      socket.receive(packet);
      
      InetAddress address = packet.getAddress();
      int port = packet.getPort();
      packet = new DatagramPacket(buf, buf.length, address, port);
      ReadPacket(packet.getData());
      count = count + 1;
      if (count % 1000 == 0) {
        logger.info("Messages Processed: " + count);
      }
    }
  }

  

  public void ReadPacket(byte[] bytes) throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    
    F12021PacketFactory factory = new F12021PacketFactory();
    F12021PacketHeader header = factory.CreatePacketHeader(bb);
    try {
      if(header.PacketFormat == 2020) {
        F12020UDPPacketHandler udpHandler = new F12020UDPPacketHandler();
        udpHandler.ReadPacket(header, bb, streaming);
      } else if (header.PacketFormat == 2021) {
        F12021UDPPacketHandler udpHandler = new F12021UDPPacketHandler();
        udpHandler.ReadPacket(header, bb, streaming);
      }
    } catch (IllegalArgumentException ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.fatal(stackTrace);
    }
  }
}
