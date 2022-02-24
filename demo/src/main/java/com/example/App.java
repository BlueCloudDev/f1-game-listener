package com.example;

import UDPListener.UDPListener;
/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    try {
      UDPListener listener = new UDPListener();
      listener.Listen();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
