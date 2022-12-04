package com.winning.ants.core.base;

import com.winning.ants.core.LifecycleEvent;

public interface LifecycleListener {

    void fire(LifecycleEvent lifecycleEvent);
}
