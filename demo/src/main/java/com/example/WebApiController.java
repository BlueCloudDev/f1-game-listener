package com.example;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.annotation.route.PUT;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.annotation.route.DELETE;
import com.hellokaton.blade.mvc.RouteContext;
import com.hellokaton.blade.mvc.ui.ResponseType;

import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;
import Repository.UDPServer.UDPServerRepository;
import UDPServerModel.PlayerBays;

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
  @POST("/f12021") 
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
      if (port < 5000) {
        return "Port needs to be greater than 5000";
      }
      repo.InsertPlayerBay(port);
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
      int port = Integer.parseInt(portString);
      if (port < 5000) {
        return "Port needs to be greater than 5000";
      }
      repo.UpdatePlayerBaysName(port, nameString);
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
}
