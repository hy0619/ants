package com.winning.ants.core;

import java.util.List;

/**
 * 数据灌入
 */
public interface IPourer<T , V> extends Lifecycle{

    V pour(List<T> list);
}
