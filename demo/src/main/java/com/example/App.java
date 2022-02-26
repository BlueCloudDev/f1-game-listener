package com.example;

import java.util.Timer;
import java.util.TimerTask;

import F12020Packet.F12020PacketSessionData;
import OCIStreaming.OCIStreaming;
import Repository.OracleDataSourceProvider;
import Repository.SessionDataRepository;
import UDPListener.UDPListener;
import oracle.jdbc.pool.OracleDataSource;
/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    Timer timer = new Timer();
    TimerTask getMsgTask = GetMessagesTask();
    timer.scheduleAtFixedRate(getMsgTask, 1000, 1000);
    
    try {

      UDPListener listener = new UDPListener();
      listener.Listen();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    System.out.println("exiting");
  }

  public static TimerTask GetMessagesTask() {
    return new TimerTask() {
      @Override
      public void run() {
        try {
          OCIStreaming stream = new OCIStreaming();
          stream.GetMessage();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    };
  }

}
