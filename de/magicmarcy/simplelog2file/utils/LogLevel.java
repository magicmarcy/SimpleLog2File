package de.magicmarcy.simplelog2file.utils;

/**
 * @author MagicMarcy | 02.04.2022
 */
public enum LogLevel {
  TRACE(Konst.TRACE_MESSAGE),
  DEBUG(Konst.DEBUG_MESSAGE),
  INFO(Konst.INFO_MESSAGE),
  ERROR(Konst.ERROR_MESSAGE),
  WARN(Konst.WARN_MESSAGE);

  private final String loglevelString;

  LogLevel(final String errorString) {
    this.loglevelString = errorString;
  }

  public String getLoglevelString() {
    return this.loglevelString;
  }
}
