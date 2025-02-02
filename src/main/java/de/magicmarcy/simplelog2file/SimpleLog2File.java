package de.magicmarcy.simplelog2file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import de.magicmarcy.simplelog2file.utils.Konst;
import de.magicmarcy.simplelog2file.utils.LogLevel;

/**
 * Just a simple file-Logger. Per default the file is stored in the project root-folder but the destination can be overwritten
 * in the constructor of this class.
 *
 * @author MagicMarcy | 02.04.2022
 */
public class SimpleLog2File {

  /** Default log path can be overwritten with the constructor of the class */
  private String logPath = "";

  /** Default filename-pattern (default: YYYY-MM-DD.log). Can be overwritten with the constructor of the class */
  private String logFileName = createLogFileNameByDate();

  public SimpleLog2File() {
    super();
  }

  public SimpleLog2File(final String logPath) {
    this.logPath = logPath;
  }

  public SimpleLog2File(final String logPath, final String logFilename) {
    this.logPath = logPath;
    this.logFileName = logFilename;
  }

  /**
   * Creates a String for the filename with the actual date
   *
   * @return the formatted filename, e.g. 2022-03-01.log
   */
  private String createLogFileNameByDate() {
    return getFormattedDateOfNow(Konst.FILENAME_DATE_PATTERN) + Konst.FILENAME_EXTENSION;
  }

  /**
   * creates a new line in the logfile with level TRACE
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Proceed with method
   * </pre></blockquote>
   *
   * @param object the log-message or an object
   */
  public void trace(final Object object) {
    writeMessageToFile(LogLevel.TRACE, getString(object));
  }

  /**
   * creates a new line in the logfile with level TRACE with a String
   * <blockquote><pre>
   * logger.trace("Proceed with {}", "method");
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Proceed with method
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   */
  public void trace(final String message) {
    writeMessageToFile(LogLevel.TRACE, message);
  }

  /**
   * creates a new line in the logfile with level TRACE with a formatted String
   * <blockquote><pre>
   * logger.trace("Proceed with {}", "method");
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Proceed with method
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   * @param replacements the replacement values
   */
  public void trace(final String message, final String... replacements) {
    writeMessageToFile(LogLevel.TRACE, format(message, replacements));
  }

  /**
   * creates a new line in the logfile with level TRACE and the output "Entry"
   * to mark the method entry in the logfile
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Entry
   * </pre></blockquote>
   */
  public void traceEntry() {
    writeMessageToFile(LogLevel.TRACE, Konst.ENTRY);
  }

  /**
   * creates a new line in the logfile with level TRACE and the output "Entry"
   * followed by the object to mark the method entry in the logfile
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Entry -> name=simple
   * </pre></blockquote>
   *
   * @param object the log-message or an object
   */
  public void traceEntry(final Object object) {
    writeMessageToFile(LogLevel.TRACE, Konst.ENTRY_VALUE + getString(object));
  }

  /**
   * creates a new line in the logfile with level TRACE and the output "Entry"
   * followed by the object to mark the method entry in the logfile
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Entry -> name=simple
   * </pre></blockquote>
   *
   * @param message the log-message
   */
  public void traceEntry(final String message) {
    writeMessageToFile(LogLevel.TRACE, Konst.ENTRY_VALUE + message);
  }

  /**
   * creates a new line in the logfile with level TRACE and the output "Entry"
   * followed by the object to mark the method entry in the logfile and a formatted String
   * <blockquote><pre>
   * logger.traceEntry("name={}", "simple");
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Entry -> name=simple
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   * @param replacements the replacement values
   */
  public void traceEntry(final String message, final String... replacements) {
    writeMessageToFile(LogLevel.TRACE, Konst.ENTRY_VALUE + format(message, replacements));
  }

  /**
   * creates a new line in the logfile with level TRACE and the output "Exit"
   * to mark the method exit in the logfile
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Exit
   * </pre></blockquote>
   */
  public void traceExit() {
    writeMessageToFile(LogLevel.TRACE, Konst.EXIT);
  }

  /**
   * creates a new line in the logfile with level TRACE and the output "Exit"
   * followed by the object to mark the method exit in the logfile
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Exit -> 1
   * </pre></blockquote>
   *
   * @param result the log-message or an object
   * @return returns the result as passed in
   */
  public <T> T traceExit(final T result) {
    writeMessageToFile(LogLevel.TRACE, Konst.EXIT_VALUE + getString(result));

    return result;
  }

  /**
   * creates a new line in the logfile with level DEBUG
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [DEBUG] [Main.main()] Set name to 'peter'
   * </pre></blockquote>
   *
   * @param object the log-message or an object
   */
  public void debug(final Object object) {
    writeMessageToFile(LogLevel.DEBUG, getString(object));
  }

  /**
   * creates a new line in the logfile with level DEBUG with a String
   * <blockquote><pre>
   * logger.debug("Set name to peter");
   * 2024-01-10 17:23:02.009 [DEBUG] [Main.main()] Set name to 'peter'
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   */
  public void debug(final String message) {
    writeMessageToFile(LogLevel.DEBUG, message);
  }

  /**
   * creates a new line in the logfile with level DEBUG with a formattet String
   * <blockquote><pre>
   * logger.debug("Set name to {}", "peter");
   * 2024-01-10 17:23:02.009 [DEBUG] [Main.main()] Set name to 'peter'
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   * @param replacements the replacement values
   */
  public void debug(final String message, final String... replacements) {
    writeMessageToFile(LogLevel.DEBUG, format(message, replacements));
  }

  /**
   * creates a new line in the logfile with level INFO
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [INFO ] [Main.main()] User is not an admin
   * </pre></blockquote>
   *
   * @param object the log-message or an object
   */
  public void info(final Object object) {
    writeMessageToFile(LogLevel.INFO, getString(object));
  }

  /**
   * creates a new line in the logfile with level INFO and a String
   * <blockquote><pre>
   * logger.info("User is not am admin");
   * 2024-01-10 17:23:02.009 [INFO ] [Main.main()] User is not an admin
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   */
  public void info(final String message) {
    writeMessageToFile(LogLevel.INFO, message);
  }

  /**
   * creates a new line in the logfile with level INFO and a formatted String
   * <blockquote><pre>
   * logger.info("User is not am {}", "admin");
   * 2024-01-10 17:23:02.009 [INFO ] [Main.main()] User is not an admin
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   * @param replacements the replacement values
   */
  public void info(final String message, final String... replacements) {
    writeMessageToFile(LogLevel.INFO, format(message, replacements));
  }

  /**
   * creates a new line in the logfile with level ERROR as followed
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [ERROR] [Main.main()] Error in main-method!
   * </pre></blockquote>
   *
   * @param object the log-message or an object
   */
  public void error(final Object object) {
    writeMessageToFile(LogLevel.ERROR, getString(object));
  }

  /**
   * creates a new line in the logfile with level ERROR as followed with a String
   * <blockquote><pre>
   * logger.error("Error in main-method");
   * 2024-01-10 17:23:02.009 [ERROR] [Main.main()] Error in main-method!
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   */
  public void error(final String message) {
    writeMessageToFile(LogLevel.ERROR, message);
  }

  /**
   * creates a new line in the logfile with level ERROR as followed with a formatted String
   * <blockquote><pre>
   * logger.error("Error in {}-method", "main");
   * 2024-01-10 17:23:02.009 [ERROR] [Main.main()] Error in main-method!
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   * @param replacements the replacement values
   */
  public void error(final String message, final String... replacements) {
    writeMessageToFile(LogLevel.ERROR, format(message, replacements));
  }

  /**
   * creates a new line in the logfile with level WARN as followed
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [WARN ] [Main.main()] Could not found a match
   * </pre></blockquote>
   *
   * @param object the log-message or an object
   */
  public void warn(final Object object) {
    writeMessageToFile(LogLevel.WARN, getString(object));
  }

  /**
   * creates a new line in the logfile with level WARN as followed and a String
   * <blockquote><pre>
   * logger.warn("Could not found a match for test");
   * 2024-01-10 17:23:02.009 [WARN ] [Main.main()] Could not found a match for test
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   */
  public void warn(final String message) {
    writeMessageToFile(LogLevel.WARN, message);
  }

  /**
   * creates a new line in the logfile with level WARN as followed and a formatted String
   * <blockquote><pre>
   * logger.warn("Could not found a match for {}", "test");
   * 2024-01-10 17:23:02.009 [WARN ] [Main.main()] Could not found a match for test
   * </pre></blockquote>
   *
   * @param message the given message with {} to replace
   * @param replacements the replacement values
   */
  public void warn(final String message, final String... replacements) {
    writeMessageToFile(LogLevel.WARN, format(message, replacements));
  }

  /**
   * creates a new line in the logfile with level SQL to
   * log sql statements
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [SQL  ] [Main.main()] Folgender SQL wird ausgefuehrt:
   * SELECT * FROM PERSON
   * </pre></blockquote>
   *
   * @param object the log-message or an object
   */
  public void sql(final Object object) {
    writeMessageToFile(LogLevel.SQL, Konst.SQL_LOG_MESSAE + getString(object));
  }

  /**
   * Writes the logfile and appends the given message to a new line. If the logfile doesn't exist, a new file will be created.
   * Catches an {@link java.io.IOException} if there's an error with the filehandling
   *
   * @param loglevel the given {@link de.magicmarcy.simplelog2file.utils.LogLevel}
   * @param message the message which will be appended in the logfile
   */
  private void writeMessageToFile(final LogLevel loglevel, final String message) {
    try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullFilePath(), true))) {
      String logFileMessage = createLogFileMessage(loglevel, message);

      bufferedWriter.newLine();
      bufferedWriter.write(logFileMessage);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates the full logfile message with date, time, loglevel, method and message
   *
   * @param loglevel {@link de.magicmarcy.simplelog2file.utils.LogLevel}
   * @param message the log message
   * @return complete line for the logfile
   */
  private String createLogFileMessage(final LogLevel loglevel, final String message) {
    final String dateTimeString = getFormattedDateOfNow(Konst.DATETIME_PATTERN);
    final String logLevelString = Konst.SQUARE_BRACKET_LEFT + loglevel.getLoglevelString() + Konst.SQUARE_BRACKET_RIGHT;
    final String methodString = Konst.SQUARE_BRACKET_LEFT + getFullMethodName() + Konst.SQUARE_BRACKET_RIGHT;

    return String.join(Konst.SPACE, dateTimeString, logLevelString, methodString, message);
  }

  /**
   * Creates the full method name from the calling method
   * NOTE: not sure if this is everytime the fifth element in the array, but it seems to work fine
   *
   * @return methodname e.g. Main.main()
   */
  private String getFullMethodName() {
    String simplename = "";
    String methodname = "";

    try {
      final StackTraceElement[] stackTraceArray = Thread.currentThread().getStackTrace();
      final StackTraceElement stackTraceElement = stackTraceArray[5];

      final String[] classNameArray = stackTraceElement.getClassName().split(Konst.CLASS_SPLIT_REGEX);
      simplename = classNameArray[classNameArray.length - 1];
      methodname = stackTraceElement.getMethodName();

    } catch (final Exception e) {
      e.printStackTrace();
    }

    return simplename + Konst.METHOD_DELIMITER + methodname + Konst.METHOD_BRACKETS;
  }

  /**
   * Creates the full qualified path to the logfile
   *
   * @return path with filename e.g. c:\dev\2022-03-01.log
   */
  private String fullFilePath() {
    return this.logPath + this.logFileName;
  }

  /**
   * Creates an actual date-string of the given format
   *
   * @param format the pattern for the date
   * @return the formatted date-string
   */
  private String getFormattedDateOfNow(final String format) {
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
    final LocalDateTime now = LocalDateTime.now();

    return dateFormatter.format(now);
  }

  /**
   * Returns the toString-Method if the object is not null otherwise the string 'null'
   * @param object passed object
   * @return toString of the object, else the string 'null'
   */
  private String getString(final Object object) {
    return object == null ? Konst.NULL : object.toString();
  }

  private String format(final String message, final String... replacements) {
    if (message == null || replacements == null || replacements.length == 0) {
      return message;
    }

    final StringBuilder result = new StringBuilder();
    int replacementIndex = 0;

    for (int i = 0; i < message.length(); i++) {
      if (message.charAt(i) == '{' && (i + 1) < message.length() && message.charAt(i + 1) == '}') {
        if (replacementIndex < replacements.length) {
          result.append(replacements[replacementIndex++]);
        } else {
          result.append("{}");
        }

        i++;
      } else {
        result.append(message.charAt(i));
      }
    }

    return result.toString();
  }
}
