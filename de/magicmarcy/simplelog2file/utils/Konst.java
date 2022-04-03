package de.magicmarcy.simplelog2file.utils;

/**
 * @author MagicMarcy | 03.04.2022
 */
public final class Konst {
  static final String TRACE_MESSAGE = "TRACE";
  static final String DEBUG_MESSAGE = "DEBUG";
  static final String INFO_MESSAGE = "INFO ";
  static final String ERROR_MESSAGE = "ERROR";
  static final String WARN_MESSAGE = "WARN ";

  public static final String FILENAME_DATE_PATTERN = "yyyy-MM-dd";
  public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
  public static final String FILENAME_EXTENSION = ".log";

  public static final String METHOD_BRACES = "()";
  public static final String METHOD_DELIMITER = ".";

  public static final String CLASS_SPLIT_REGEX = "\\.";

  private Konst() {
    // private empty constructor to hide the public one
  }
}
