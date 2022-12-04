package com.winning.ants.core;

import com.winning.ants.core.Lifecycle;

/**
 * 生命周期状态枚举
 */
public enum LifecycleState {

    NEW(false, null),
    INITIALIZING(false, Lifecycle.BEFORE_INIT_EVENT),
    INITIALIZED(false, Lifecycle.AFTER_INIT_EVENT),
    STARTING_PREP(false, Lifecycle.BEFORE_START_EVENT),
    STARTING(true, Lifecycle.START_EVENT),
    STARTED(true, Lifecycle.AFTER_START_EVENT),
    STOPPING_PREP(true, Lifecycle.BEFORE_STOP_EVENT),
    STOPPING(false, Lifecycle.STOP_EVENT),
    STOPPED(false, Lifecycle.AFTER_STOP_EVENT),
    FAILED(false, null);

    private final boolean available;
    private final String lifecycleEvent;

    private LifecycleState(boolean available, String lifecycleEvent) {
        this.available = available;
        this.lifecycleEvent = lifecycleEvent;
    }

    /**
     * 组件是否可用
     * @return
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * 获取生命周期事件
     * @return
     */
    public String getLifecycleEvent() {
        return lifecycleEvent;
    }

}
