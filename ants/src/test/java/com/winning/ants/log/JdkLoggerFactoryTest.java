package com.winning.ants.log;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JdkLoggerFactoryTest {

    @Test
    public void testJdkLoggerFactory(){
        ILogger foo = JdkLoggerFactory.INSTANCE.newInstance("foo");
        assertTrue(foo instanceof JdkLogger);
        assertEquals("foo", foo.name());

    }
}
