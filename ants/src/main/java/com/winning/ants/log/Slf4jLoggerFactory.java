package com.winning.ants.log;

import org.slf4j.helpers.NOPLoggerFactory;

public class Slf4jLoggerFactory extends LoggerFactory{

    public static final LoggerFactory INSTANCE = new Slf4jLoggerFactory();

    /**
     * @deprecated Use {@link #INSTANCE} instead.
     */
    @Deprecated
    public Slf4jLoggerFactory() {
    }

    Slf4jLoggerFactory(boolean failIfNOP) {
        assert failIfNOP; // Should be always called with true.
        if (org.slf4j.LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory) {
            throw new NoClassDefFoundError("NOPLoggerFactory not supported");
        }
    }

    @Override
    public ILogger newInstance(String name) {
        return null;
        //return new Slf4JLogger(logger);
    }


    static LoggerFactory getInstanceWithNopCheck() {
        return NopInstanceHolder.INSTANCE_WITH_NOP_CHECK;
    }

    private static final class NopInstanceHolder {
        private static final LoggerFactory INSTANCE_WITH_NOP_CHECK = new Slf4jLoggerFactory(true);
    }
}
