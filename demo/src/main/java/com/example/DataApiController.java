package com.example;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Query;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.mvc.ui.ResponseType;

import F12021Packet.F12021PacketSessionData;
import F12021Packet.F12021PacketSessionHistoryData;
import F12021Packet.F12021SessionLookup;
import F12021Packet.Response.CarTelemetryByLap;
import F12021Packet.Response.LeaderboardResponse2021;
import Repository.F12021.CarTelemetryRepository2021;
import Repository.F12021.LeaderboardRepository2021;
import Repository.F12021.SessionDataRepository2021;
import Repository.F12021.SessionHistoryDataRepository2021;
import Repository.F12021.SessionLookupRepository2021;

@Path("/data")
public class DataApiController {
  private static final Logger logger = LogManager.getLogger(App.class);

  @GET(value = "/leaderboard", responseType = ResponseType.JSON)
  public ArrayList<LeaderboardResponse2021> getLeaderboard(@Query int EventId) {
    LeaderboardRepository2021 repo = new LeaderboardRepository2021();
    ArrayList<LeaderboardResponse2021> resp = new ArrayList<LeaderboardResponse2021>();
    try {
      resp = repo.SelectLeaderboard(EventId, App.pds);
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
    }
    return resp;
  }


  @GET(value = "/session", responseType = ResponseType.JSON)
  public F12021PacketSessionData getSessionData(@Query String SessionUID) {
    SessionDataRepository2021 repo = new SessionDataRepository2021();
    try {
      var res = repo.SelectSessionData(SessionUID, App.pds);
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
      var res = repo.SelectPlayerSessions(App.pds);
      return res;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return new ArrayList<F12021SessionLookup>();
    }
  }

  @GET(value = "/session/lap", responseType = ResponseType.JSON)
  public List<CarTelemetryByLap> getSessionLap(@Query String SessionUID) {
    CarTelemetryRepository2021 repo = new CarTelemetryRepository2021();
    try {
      List<CarTelemetryByLap> res = repo.SelectCarTelemetryBySessionUID(SessionUID, App.pds);
      return res;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return new ArrayList<CarTelemetryByLap>();
    }
  }

  @GET(value = "/session/history", responseType = ResponseType.JSON)
  public F12021PacketSessionHistoryData getSessionHistory(@Query String SessionUID, @Query int Index) {
    SessionHistoryDataRepository2021 repo = new SessionHistoryDataRepository2021();
    try {
      var res = repo.SelectSessionHistoryData(SessionUID, Index, App.pds);
      return res;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
      return new F12021PacketSessionHistoryData();
    }
  }
}
