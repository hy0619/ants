package com.winning.ants.core.base;

import com.winning.ants.core.Lifecycle;
import com.winning.ants.core.LifecycleEvent;
import com.winning.ants.core.LifecycleListener;
import com.winning.ants.core.exception.LifecycleException;
import com.winning.ants.core.utils.StringManager;
import com.winning.ants.log.ILogger;
import com.winning.ants.log.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 组件模板类
 */
public abstract class LifecycleBase implements Lifecycle {
    public final static ILogger log = LoggerFactory.getInstance(LifecycleBase.class);
    private final List<LifecycleListener> lifecycleListeners = new CopyOnWriteArrayList<>();

    private volatile LifecycleState state = LifecycleState.NEW;

    private static final StringManager sm = StringManager.getManager(LifecycleBase.class);


    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycleListeners.add(listener);
    }


    /**
     * 执行生命周期事件
     * @param type
     * @param data
     */
    protected void fireLifecycleEvent(String type, Object data) {
        LifecycleEvent event = new LifecycleEvent(this, type, data);
        for (LifecycleListener listener : lifecycleListeners) {
            listener.fireLifecycleEvent(event);
        }
    }

    private synchronized void setState(LifecycleState state, Object data, boolean check)
            throws LifecycleException {
        if (log.isDebugEnabled()) {
            log.debug(sm.getString("lifecycleBase.setState", this, state));
        }

        if (check) {
            if (state == null) {
                unPower("null");
                return;
            }

            if (!(state == LifecycleState.FAILED ||
                    (this.state == LifecycleState.STARTING_PREP &&
                            state == LifecycleState.STARTING) ||
                    (this.state == LifecycleState.STOPPING_PREP &&
                            state == LifecycleState.STOPPING) ||
                    (this.state == LifecycleState.FAILED &&
                            state == LifecycleState.STOPPING))) {
                unPower(state.name());
            }
        }

        this.state = state;
        String lifecycleEvent = state.getLifecycleEvent();
        if (lifecycleEvent != null) {
            fireLifecycleEvent(lifecycleEvent, data);
        }
    }


    /**
     * 初始化模板方法
     * @throws LifecycleException
     */
    @Override
    public final synchronized void init() throws LifecycleException {
        if (!state.equals(LifecycleState.NEW)) {
            unPower(Lifecycle.BEFORE_INIT_EVENT);
        }
        setState(LifecycleState.INITIALIZING , null , false);
        doInit();
        setState(LifecycleState.INITIALIZED , null , false);
    }


    /**
     * 组件初始化
     */
    protected abstract void doInit();

    @Override
    public final synchronized void start() throws LifecycleException {
        if (LifecycleState.STARTING_PREP.equals(state) || LifecycleState.STARTING.equals(state) ||
                LifecycleState.STARTED.equals(state)) {

            if (log.isDebugEnabled()) {
                Exception e = new LifecycleException("alreadyStarted");
                log.debug(sm.getString("lifecycleBase.alreadyStarted", toString()), e);
            } else if (log.isInfoEnabled()) {
                log.info(sm.getString("lifecycleBase.alreadyStarted", toString()));
            }

            return;
        }

        if (state.equals(LifecycleState.NEW)) {
            init();
        } else if (state.equals(LifecycleState.FAILED)) {
            stop();
        } else if (!state.equals(LifecycleState.INITIALIZED) &&
                !state.equals(LifecycleState.STOPPED)) {
            unPower(Lifecycle.BEFORE_START_EVENT);
        }

        try{
            setState(LifecycleState.STARTING_PREP , null , false);
            doStart();
            if(state.equals(LifecycleState.FAILED)){
                stop();
            }else if(!state.equals(LifecycleState.STARTING)){
                unPower(Lifecycle.AFTER_START_EVENT);
            }else{
                setState(LifecycleState.STARTED , null , false);
            }
        }catch(Throwable t){
            handleLifecycleException(t, "lifecycleBase.startFail", toString());
        }


    }


    private void handleLifecycleException(Throwable t , String key , Object... args) throws LifecycleException {
        setState(LifecycleState.FAILED, null, false);
        String msg = sm.getString(key, args);
        log.error(msg, t);
    }

    protected abstract void doStart();

    @Override
    public final synchronized void stop() throws LifecycleException {
        if (LifecycleState.STOPPING_PREP.equals(state) || LifecycleState.STOPPING.equals(state) ||
                LifecycleState.STOPPED.equals(state)) {

            if (log.isDebugEnabled()) {
                Exception e = new LifecycleException("alreadyStopped");
                log.debug(sm.getString("lifecycleBase.alreadyStopped", toString()), e);
            } else if (log.isInfoEnabled()) {
                log.info(sm.getString("lifecycleBase.alreadyStopped", toString()));
            }

            return;
        }

        if (state.equals(LifecycleState.NEW)) {
            state = LifecycleState.STOPPED;
            return;
        }


        if (!state.equals(LifecycleState.STARTED) && !state.equals(LifecycleState.FAILED)) {
            unPower(Lifecycle.BEFORE_STOP_EVENT);
        }

        try {
            if (state.equals(LifecycleState.FAILED)) {
                fireLifecycleEvent(BEFORE_STOP_EVENT, null);
            } else {
                setState(LifecycleState.STOPPING_PREP, null, false);
            }

            doStop();
            if (!state.equals(LifecycleState.STOPPING) && !state.equals(LifecycleState.FAILED)) {
                unPower(Lifecycle.AFTER_STOP_EVENT);
            }

            setState(LifecycleState.STOPPED, null, false);
        } catch (Throwable t) {
            handleLifecycleException(t , "lifecycleBase.stop" , toString());
        }
    }


    protected abstract void doStop();


    /**
     *
     * @param type
     * @throws LifecycleException
     */
    private void unPower(String type) throws LifecycleException {
        String msg = sm.getString("lifecycleBase.unPower", type, toString(), state);
        throw new LifecycleException(msg);
    }




}
