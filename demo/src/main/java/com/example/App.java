package com.example;

import java.util.Timer;
import java.util.TimerTask;

import OCIStreaming.OCIStreaming;
import UDPListener.UDPListener;

public class App {

  public static void main(String[] args) {
    Timer timer = new Timer();
    TimerTask getMsgTask = GetMessagesTask();
    timer.scheduleAtFixedRate(getMsgTask, 5000, 5000);
    
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
