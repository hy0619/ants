/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.winning.ants.log;


import java.io.Serializable;

public abstract class AbstractLogger implements ILogger, Serializable {

    private static final long serialVersionUID = -6382972526573193470L;

    private final String name;

    /**
     * Creates a new instance.
     */
    protected AbstractLogger(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }


    @Override
    public void log(InternalLogLevel level, String format, Object... arguments){
        switch (level) {
            case TRACE:
                trace(format, arguments);
                break;
            case DEBUG:
                debug(format, arguments);
                break;
            case INFO:
                info(format, arguments);
                break;
            case WARN:
                warn(format, arguments);
                break;
            case ERROR:
                error(format, arguments);
                break;
            default:
                throw new Error();
        }
    }






}
