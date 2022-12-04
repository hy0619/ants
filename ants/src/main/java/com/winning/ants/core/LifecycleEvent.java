package com.winning.ants.core;


public class LifecycleEvent {

    /**
     * 事件来源组件
     */
    private transient Lifecycle source;

    /**
     * 事件数据
     */
    private final Object data;


    /**
     * 事件类型
     */
    private final String type;


    public Object getSource() {
        return source;
    }

    public String toString() {
        return getClass().getName() + "[source=" + source + "]";
    }


    public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
        if(null == lifecycle){
            throw new IllegalArgumentException("null source");
        }
        this.source = lifecycle;
        this.type = type;
        this.data = data;
    }
}
