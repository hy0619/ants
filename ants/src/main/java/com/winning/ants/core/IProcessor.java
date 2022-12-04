package com.winning.ants.core;

/**
 * 数据处理接口
 */
public interface IProcessor<T , V>  extends Lifecycle{

    V process(T data);
}
