package com.winning.ants.log;

import java.util.logging.Logger;

public class JdkLoggerFactory extends LoggerFactory{
    public static final LoggerFactory INSTANCE = new JdkLoggerFactory();

    public ILogger newInstance(String name){
        return new JdkLogger(Logger.getLogger(name));
    }
}
