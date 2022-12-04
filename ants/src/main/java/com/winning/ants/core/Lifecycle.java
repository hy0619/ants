package com.winning.ants.core;

import com.winning.ants.core.exception.LifecycleException;

/**
 * 组件生命周期接口
 */
public interface Lifecycle {

    /**
     * 生命周期事件： 初始化之前
     */
     static final String BEFORE_INIT_EVENT = "before_init";
    /**
     * 生命周期事件： 初始化之后
     */
     static final String AFTER_INIT_EVENT = "after_init";


    /**
     * 生命周期事件： 组件启动
     */
     static final String START_EVENT = "start";


    /**
     * 生命周期事件： 组件启动之前
     */
     static final String BEFORE_START_EVENT = "before_start";


    /**
     * 生命周期事件： 组件启动后
     */
     static final String AFTER_START_EVENT = "after_start";


    /**
     * 生命周期事件： 组件停止
     */
     static final String STOP_EVENT = "stop";


    /**
     * 生命周期事件： 组件停止前
     */
    public static final String BEFORE_STOP_EVENT = "before_stop";


    /**
     * 生命周期事件： 组件停止后
     */
    public static final String AFTER_STOP_EVENT = "after_stop";



    /**
     * 初始化
     */
    void init() throws LifecycleException;

    /**
     * 启动
     */
    void start() throws LifecycleException;

    /**
     * 停止
     */
    void stop() throws LifecycleException;

    /**
     * 添加监听事件
     * @param listener
     */
    void addLifecycleListener(LifecycleListener listener) throws LifecycleException;

}
