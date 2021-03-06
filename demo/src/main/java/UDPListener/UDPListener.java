package UDPListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.ObjectInputFilter.Config;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;

import F12021Packet.F12021PacketFactory;
import F12021Packet.F12021PacketHeader;
import MessageHandler.F12021UDPPacketHandler;
import Repository.UDPServer.UDPServerRepository;
import UDPServer.UDPServer;
import Configuration.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UDPListener extends Thread{
  private static final Logger logger = LogManager.getLogger(UDPListener.class);
  CloseableHttpClient httpClient;
  private int port = 0;
  private static int MAX_BUFFER = 2048;
  private static int EventId;
  private DatagramSocket socket = null;

  public UDPListener(Integer _port) throws SocketException {
    super();
    if (_port > 0) {
      port = _port;
    } else {
      port = Integer.parseInt(Configuration.EnvVars.get("LISTEN_PORT"));
    }
    if (!Configuration.EnvVars.get("UDP_SERVER").equals("")){
      Runnable server = new UDPServer();
      Thread thread = new Thread(server);

      System.out.println("Starting UDP Server thread...");
      thread.start();
    }
  }

  @Override
   public void interrupt(){
     super.interrupt();  
     this.socket.close();
   }
  public void run() {
    try {
      this.socket = new DatagramSocket(port);
      byte[] buf = new byte[MAX_BUFFER];
      int count = 0;
      logger.info("Starting UDP listener on port: " + port);
      while (!this.socket.isClosed()) {
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        ReadPacket(packet.getData());
        count = count + 1;
        if (count % 100 == 0) {
          var repo = new UDPServerRepository();
          repo.UpdatePlayerBaysLastPacket(this.port);
        }
        if (count % 1000 == 0) {
          logger.info("Messages Processed: " + count);
        }
      }
    } catch (Exception ex) {
      String stacktrace = ExceptionUtils.getStackTrace(ex);
      logger.info(stacktrace);
    }
  }

  

  public void ReadPacket(byte[] bytes) throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
    var clone = bytes.clone();
    ByteBuffer bb = ByteBuffer.wrap(clone);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    
    F12021PacketFactory factory = new F12021PacketFactory();
    F12021PacketHeader header = null;
    if (Configuration.EnvVars.get("APPLICATION_MODE").equals("local-server")){
      UDPServerRepository repo = new UDPServerRepository();
      var res = repo.GetPlayerNameAndEventIdByPort(this.port);
      //TODO: All this to avoid making an object... :/
      Entry<String,Integer> entry = res.entrySet().iterator().next();
      String key = entry.getKey();
      int value = entry.getValue();
      header = factory.CreatePacketHeader(bb, key, value);
    } else {
      header = factory.CreatePacketHeader(bb);
    }
    header.EventId = EventId;
    try {
      if(header.PacketFormat == 2020) {
        logger.fatal("2020 not supported at the moment");
        //F12020UDPPacketHandler udpHandler = new F12020UDPPacketHandler();
        //udpHandler.ReadPacket(header, bb, streaming);
      } else if (header.PacketFormat == 2021) {
        String APPLICATION_MODE = Configuration.EnvVars.get("APPLICATION_MODE").toLowerCase();
        if (APPLICATION_MODE.equals("listener")) {
          F12021UDPPacketHandler udpHandler = new F12021UDPPacketHandler(false);
          udpHandler.ReadPacket(header, bb);
        } else if (APPLICATION_MODE.equals("local-listener") || APPLICATION_MODE.equals("local-server")) {
          F12021UDPPacketHandler udpHandler = new F12021UDPPacketHandler(true);
          udpHandler.ReadPacket(header, bb);
        }
      }
    } catch (IllegalArgumentException ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.fatal(stackTrace);
    }
  }
}
