package com.winning.ants.core;

public interface LifecycleListener {
    /**
     * 添加生命周期事件
     * @param event
     */
     void fireLifecycleEvent(LifecycleEvent event);
}
