package com.example;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.RouteContext;

import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;

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
}
