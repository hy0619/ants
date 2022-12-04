package com.winning.ants.log;

public interface ILogger {
    /**
     * return logger instance name
     * @return
     */
    String name();

    /**
     * is the logger instance enabled the trace level ?
     * @return True if this Logger is enabled for the TRACE level,
     *         false otherwise
     */
    boolean isTraceEnabled();


    /**
     * Log a message at the TRACE level.
     * @param msg  log message
     */
    void trace(String msg);

    /**
     *  Log a message at the TRACE level according to the specified format
     * and argument.
     * @param format
     * @param arguments
     */
    void trace(String format, Object... arguments);


    /**
     * is the logger instance enabled the debug level ?
     * @return
     */
    boolean isDebugEnabled();

    /**
     * Log a message at the debug level.
     * @param msg
     */
    void debug(String msg);

    /**
     * Log a message at the debug level according to the specified format
     * and argument.
     * @param format
     * @param arguments
     */
    void debug(String format, Object... arguments);

    /**
     * is the logger instance enabled  info level ?
     * @return
     */
    boolean isInfoEnabled();

    void info(String msg);

    void info(String format, Object... arguments);


    /**
     * is the logger instance enabled  warn level ?
     * @return
     */
    boolean isWarnEnabled();


    void warn(String msg);

    void warn(String format, Object... arguments);

    /**
     * is the logger instance enabled  error level ?
     * @return
     */
    boolean isErrorEnabled();

    void error(String msg);

    void error(String format, Object... arguments);

    void log(InternalLogLevel level, String format, Object... arguments);
}
