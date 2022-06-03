package com.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;

public class ProcessMessagesTask implements Runnable {
  private static final Logger logger = LogManager.getLogger(ProcessMessagesTask.class);
  private static OCIStreaming stream = null;
  public ProcessMessagesTask() throws OCIStreamingException, Exception {
    super();
    if (stream == null) {
      stream = new OCIStreaming();
    }
  }
  @Override
  public void run() {
    try {
      stream.GetMessage();
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.fatal(stackTrace);
    }
  }
}
