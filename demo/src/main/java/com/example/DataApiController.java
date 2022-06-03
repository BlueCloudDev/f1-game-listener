package com.example;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Query;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.annotation.route.PUT;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.annotation.route.DELETE;
import com.hellokaton.blade.mvc.RouteContext;
import com.hellokaton.blade.mvc.ui.ResponseType;

import F12021Packet.F12021PacketSessionData;
import F12021Packet.F12021PacketSessionHistoryData;
import F12021Packet.F12021SessionLookup;
import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;
import Repository.F12020.OracleDataSourceProvider;
import Repository.F12020.SessionDataRepository;
import Repository.F12021.SessionDataRepository2021;
import Repository.F12021.SessionHistoryDataRepository2021;
import Repository.F12021.SessionLookupRepository2021;
import Repository.UDPServer.UDPServerRepository;
import UDPServerModel.PlayerBays;
import oracle.ucp.jdbc.PoolDataSource;

@Path("/data")
public class DataApiController {
  private static final Logger logger = LogManager.getLogger(App.class);
  private static PoolDataSource pds = null;
  public DataApiController() throws OCIStreamingException, Exception {
    super();
    if (pds == null) {
      OracleDataSourceProvider odsp = new OracleDataSourceProvider();
      pds = odsp.GetOraclePoolDataSource();
    }
  }

  @GET(value = "/session", responseType = ResponseType.JSON)
  public F12021PacketSessionData getSessionData(@Query String SessionUID) {
    SessionDataRepository2021 repo = new SessionDataRepository2021();
    try {
      var res = repo.SelectSessionData(SessionUID, pds);
      return res;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return new F12021PacketSessionData();
    }
  }

  @GET(value = "/session/player", responseType = ResponseType.JSON)
  public List<F12021SessionLookup> getPlayerSession() {
    SessionLookupRepository2021 repo = new SessionLookupRepository2021();
    try {
      var res = repo.SelectPlayerSessions(pds);
      return res;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return new ArrayList<F12021SessionLookup>();
    }
  }

  @GET(value = "/session/history", responseType = ResponseType.JSON)
  public F12021PacketSessionHistoryData getSessionHistory(@Query String SessionUID) {
    SessionHistoryDataRepository2021 repo = new SessionHistoryDataRepository2021();
    try {
      var res = repo.SelectSessionHistoryData(SessionUID, pds);
      return res;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return new F12021PacketSessionHistoryData();
    }
  }
}
