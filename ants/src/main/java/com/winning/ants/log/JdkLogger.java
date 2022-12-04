package com.winning.ants.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * jdk自带logger实现
 * 日志级别 ： SEVERE（最高值）
 * WARNING 、INFO 、CONFIG
 * FINE 、FINER 、FINEST（最低值）
 * ALL(记录所有信息)  OFF(不记录任何级别信息)
 */
public class JdkLogger extends AbstractLogger {

    final transient Logger logger;

    static final String SELF = JdkLogger.class.getName();

    static final String SUPER = AbstractLogger.class.getName();

    JdkLogger(Logger logger) {
        super(logger.getName());
        this.logger = logger;
    }
    @Override
    public boolean isTraceEnabled() {
        return this.logger.isLoggable(Level.FINEST);
    }

    private void log(String callerFQCN, Level level, String msg, Throwable t) {
        // millis and thread are filled by the constructor
        LogRecord record = new LogRecord(level, msg);
        record.setLoggerName(name());
        record.setThrown(t);
        fillCallerData(callerFQCN, record);
        logger.log(record);
    }

    private static void fillCallerData(String callerFQCN, LogRecord record) {
        StackTraceElement[] steArray = new Throwable().getStackTrace();

        int selfIndex = -1;
        for (int i = 0; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (className.equals(callerFQCN) || className.equals(SUPER)) {
                selfIndex = i;
                break;
            }
        }

        int found = -1;
        for (int i = selfIndex + 1; i < steArray.length; i++) {
            final String className = steArray[i].getClassName();
            if (!(className.equals(callerFQCN) || className.equals(SUPER))) {
                found = i;
                break;
            }
        }

        if (found != -1) {
            StackTraceElement ste = steArray[found];
            // setting the class name has the side effect of setting
            // the needToInferCaller variable to false.
            record.setSourceClassName(ste.getClassName());
            record.setSourceMethodName(ste.getMethodName());
        }
    }

    @Override
    public void trace(String msg) {
        if (logger.isLoggable(Level.FINEST)) {
            log(SELF, Level.FINEST, msg, null);
        }
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (logger.isLoggable(Level.FINEST)) {
            FormattingTuple ft = MessageFormatter.format(format, arguments);
            log(SELF, Level.FINEST, ft.getMessage(), ft.getThrowable());
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isLoggable(Level.FINE);
    }

    @Override
    public void debug(String msg) {
        if (logger.isLoggable(Level.FINE)) {
            log(SELF, Level.FINE, msg, null);
        }
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (logger.isLoggable(Level.FINE)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            log(SELF, Level.FINE, ft.getMessage(), ft.getThrowable());
        }
    }

    @Override
    public boolean isInfoEnabled() {
        return this.logger.isLoggable(Level.INFO);
    }

    @Override
    public void info(String msg) {
        if (logger.isLoggable(Level.INFO)) {
            log(SELF, Level.INFO, msg, null);
        }
    }

    @Override
    public void info(String format, Object... arguments) {
        if (logger.isLoggable(Level.INFO)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public void warn(String msg) {
        if (logger.isLoggable(Level.WARNING)) {
            log(SELF, Level.WARNING, msg, null);
        }
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (logger.isLoggable(Level.WARNING)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            log(SELF, Level.WARNING, ft.getMessage(), ft.getThrowable());
        }
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public void error(String msg) {
        if (logger.isLoggable(Level.SEVERE)) {
            log(SELF, Level.SEVERE, msg, null);
        }
    }

    @Override
    public void error(String format, Object... arguments) {
        if (logger.isLoggable(Level.SEVERE)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            log(SELF, Level.SEVERE, ft.getMessage(), ft.getThrowable());
        }
    }
}
