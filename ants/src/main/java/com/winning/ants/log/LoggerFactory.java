package com.winning.ants.log;

import java.util.logging.Logger;

public abstract class LoggerFactory {

    public static volatile LoggerFactory defaultFactory;

    public ILogger newInstance(String name){
        return new JdkLogger(Logger.getLogger(name));
    }

    public static LoggerFactory getDefaultFactory() {
        if (defaultFactory == null) {
            defaultFactory = newDefaultFactory(LoggerFactory.class.getName());
        }
        return defaultFactory;
    }

    private static LoggerFactory useSlf4JLoggerFactory(String name) {
        try {
            LoggerFactory f = Slf4jLoggerFactory.getInstanceWithNopCheck();
            f.newInstance(name).debug("Using SLF4J as the default logging framework");
            return f;
        } catch (LinkageError ignore) {
            return null;
        } catch (Exception ignore) {
            // We catch Exception and not ReflectiveOperationException as we still support java 6
            return null;
        }
    }

    private static LoggerFactory useLog4J2LoggerFactory(String name) {
        try {
            LoggerFactory f = Log4j2LoggerFactory.INSTANCE;
            f.newInstance(name).debug("Using Log4J2 as the default logging framework");
            return f;
        } catch (LinkageError ignore) {
            return null;
        } catch (Exception ignore) {
            // We catch Exception and not ReflectiveOperationException as we still support java 6
            return null;
        }
    }

    private static  LoggerFactory useLog4JLoggerFactory(String name) {
        try {
            LoggerFactory f = Log4j2LoggerFactory.INSTANCE;
            f.newInstance(name).debug("Using Log4J as the default logging framework");
            return f;
        } catch (LinkageError ignore) {
            return null;
        } catch (Exception ignore) {
            // We catch Exception and not ReflectiveOperationException as we still support java 6
            return null;
        }
    }

    private static LoggerFactory useJdkLoggerFactory(String name) {
        LoggerFactory f = JdkLoggerFactory.INSTANCE;
        f.newInstance(name).debug("Using java.util.logging as the default logging framework");
        return f;
    }

    private static LoggerFactory newDefaultFactory(String name) {
        LoggerFactory f = useSlf4JLoggerFactory(name);
        if (f != null) {
            return f;
        }

        f = useLog4J2LoggerFactory(name);
        if (f != null) {
            return f;
        }

        f = useLog4JLoggerFactory(name);
        if (f != null) {
            return f;
        }

        return useJdkLoggerFactory(name);
    }



    public static ILogger getInstance(String name) {
        return getDefaultFactory().newInstance(name);
    }

    public static ILogger getInstance(Class<?> clazz) {
        return getInstance(clazz.getName());
    }

}
