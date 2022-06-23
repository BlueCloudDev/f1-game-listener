package com.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import F12021Packet.F12021PacketHeader;
import MessageHandler.F12021MessageHandler;

import java.util.Base64;

import org.apache.commons.lang3.exception.ExceptionUtils;

import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;
import Repository.F12021.PacketHeaderRepository2021;
import oracle.ucp.jdbc.PoolDataSource;

public class ProcessMessagesTask implements Runnable {
  private static final Logger logger = LogManager.getLogger(ProcessMessagesTask.class);
  public JSONObject msg = null;
  public PoolDataSource pds = null;
  private String Base64Decode(String base64Encoded) {
    byte[] bytes = Base64.getDecoder().decode(base64Encoded);
    return new String(bytes);
  }

  private void processEntry(JSONObject msg, PoolDataSource pds) {
    Gson gson = new Gson();
    String value = Base64Decode(msg.getString("value"));
    if (!value.startsWith("[")) {
      logger.warn("Invalid message format received: " + value);
      return;
    }
    JSONArray ja = new JSONArray(value);
    if (ja.length() == 0 || !ja.get(0).toString().startsWith("{")) {
      logger.warn("Invalid message format received: " + value);
      return;
    }
    F12021PacketHeader header = gson.fromJson(ja.get(0).toString(), F12021PacketHeader.class);
    PacketHeaderRepository2021 prepo = new PacketHeaderRepository2021();
    long id = prepo.InsertPacketHeader(header, pds);
    if (id == 0) {
      return;
    }
    if (header.PacketFormat == 2020) {
    } else if (header.PacketFormat == 2021) {
      F12021MessageHandler msgHandler = new F12021MessageHandler();
      msgHandler.ProcessMessage(header, id, ja, pds);
    }
  }
  @Override
  public void run() {
    try {
      processEntry(msg, pds);
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.fatal(stackTrace);
    }
  }
}
