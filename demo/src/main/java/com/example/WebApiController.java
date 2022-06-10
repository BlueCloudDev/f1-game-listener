package com.example;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.annotation.route.PUT;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.annotation.route.DELETE;
import com.hellokaton.blade.mvc.RouteContext;
import com.hellokaton.blade.mvc.ui.ResponseType;

import F12021Packet.F12021Event;
import F12021Packet.F12021PacketHeader;
import MessageHandler.F12020MessageHandler;
import MessageHandler.F12021MessageHandler;
import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;
import Repository.F12021.EventRepository2021;
import Repository.F12021.PacketHeaderRepository2021;
import Repository.UDPServer.UDPServerRepository;
import UDPServerModel.PlayerBays;
import oracle.ucp.jdbc.PoolDataSource;

@Path
public class WebApiController {
  private static final Logger logger = LogManager.getLogger(App.class);
  private static OCIStreaming streaming = null;
  public WebApiController() throws OCIStreamingException, Exception {
    super();
    if (streaming == null) {
      streaming = new OCIStreaming();
    }
  }

  @POST("/f12021/original") 
  public String post(RouteContext ctx){ 
    String body = ctx.bodyToString();
    try {
      streaming.SendMessage(body);
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
    }
    return "Body length: " + body.length(); 
  }

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
      F12020MessageHandler msgHandler = new F12020MessageHandler();
      msgHandler.ProcessMessage(header, id, ja, pds);
    } else if (header.PacketFormat == 2021) {
      F12021MessageHandler msgHandler = new F12021MessageHandler();
      msgHandler.ProcessMessage(header, id, ja, pds);
    }
  }

  @POST("/f12021") 
  public String postDatabase(RouteContext ctx){ 
    String body = ctx.bodyToString();
    try {
      JSONObject obj = new JSONObject(body);
      var arr = obj.getJSONArray("messages");
      for(var i = 0; i < arr.length(); i++) {
        JSONObject msg = arr.getJSONObject(i);
        processEntry(msg, App.pds);
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
    }
    return "Body length: " + body.length(); 
  }

  @GET(value = "/udpserver", responseType = ResponseType.JSON)
  public List<PlayerBays> getPlayerBays() {
    UDPServerRepository repo = new UDPServerRepository();
    try {
      var res = repo.GetPlayerBays();
      return res;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return new ArrayList<PlayerBays>();
    }
  }

  @POST("/udpserver")
  public String insertPlayerBays(RouteContext ctx) {
    UDPServerRepository repo = new UDPServerRepository();
    try {
      String portString = ctx.query("port");
      int port = Integer.parseInt(portString);
      String eventIdString = ctx.query("eventId");
      int eventId = Integer.parseInt(eventIdString);
      if (port < 5000) {
        return "Port needs to be greater than 5000";
      }
      repo.InsertPlayerBay(port, eventId);
      return "Success";
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return "Error";
    }
  }

  @PUT("/udpserver")
  public String updatePlayerBayName(RouteContext ctx) {
    UDPServerRepository repo = new UDPServerRepository();
    try {
      String portString = ctx.query("port");
      String nameString = ctx.query("name");
      String eventIdString = ctx.query("eventId");
      int eventId = Integer.parseInt(eventIdString);
      int port = Integer.parseInt(portString);
      if (port < 5000) {
        return "Port needs to be greater than 5000";
      }
      repo.UpdatePlayerBaysNameAndEventId(port, nameString, eventId);
      return "Success";
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return "Error";
    }
  }

  @DELETE("/udpserver")
  public String deletePlayerBay(RouteContext ctx) {
    UDPServerRepository repo = new UDPServerRepository();
    try {
      String portString = ctx.query("port");
      int port = Integer.parseInt(portString);
      if (port < 5000) {
        return "Port needs to be greater than 5000";
      }
      repo.DeletePlayerBay(port);
      if (App.udpListeners.containsKey(port)) {
        App.udpListeners.get(port).interrupt();
        App.udpListeners.remove(port);
      }
      return "Success";
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return "Error";
    }
  }

  @POST("f12021/event")
  public String insertEvent(RouteContext ctx) {
    try {
      String body = ctx.bodyToString();
      Gson gson = new Gson();

      F12021Event event = gson.fromJson(body, F12021Event.class);
      if (event.Name == null || event.Name.length() == 0) {
        return "Invalid name";
      }
      var repo = new EventRepository2021();
      repo.InsertEvent(event, App.pds);
      return "Success";
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return "Error";
    }
  }

  @GET(value = "f12021/event", responseType = ResponseType.JSON)
  public ArrayList<F12021Event> getEvents() {
    ArrayList<F12021Event> res = new ArrayList<F12021Event>();
    try {
      var repo = new EventRepository2021();
      res = repo.SelectEvents(App.pds);
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return null;
    }
    return res;
  }
}
