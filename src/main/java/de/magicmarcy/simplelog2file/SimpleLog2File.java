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
    writeMessageToFile(LogLevel.TRACE, object.toString());
  }

  /**
   * creates a new line in the logfile with level TRACE and the output "Entry"
   * to mark the method entry in the logfile
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Entry
   * </pre></blockquote>
   */
  public void traceEntry() {
    writeMessageToFile(LogLevel.TRACE, "Entry");
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
    writeMessageToFile(LogLevel.TRACE, "Entry -> " + (object == null ? "null" : object.toString()));
  }

  /**
   * creates a new line in the logfile with level TRACE and the output "Exit"
   * to mark the method exit in the logfile
   * <blockquote><pre>
   * 2024-01-10 17:23:02.009 [TRACE] [Main.main()] Exit
   * </pre></blockquote>
   */
  public void traceExit() {
    writeMessageToFile(LogLevel.TRACE, "Exit");
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
    writeMessageToFile(LogLevel.TRACE, "Exit -> " + (result == null ? "null" : result.toString()));

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
    writeMessageToFile(LogLevel.DEBUG, object.toString());
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
    writeMessageToFile(LogLevel.INFO, object.toString());
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
    writeMessageToFile(LogLevel.ERROR, object.toString());
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
    writeMessageToFile(LogLevel.WARN, object.toString());
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
    writeMessageToFile(LogLevel.SQL, "Folgender SQL wird ausgefuehrt:\n" + object.toString());
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
    return getFormattedDateOfNow(Konst.DATETIME_PATTERN) +
        " [" + loglevel.getLoglevelString() + "] " +
        "[" + getFullMethodName() + "] " +
        message;
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

    return simplename + Konst.METHOD_DELIMITER + methodname + Konst.METHOD_BRACES;
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
}
