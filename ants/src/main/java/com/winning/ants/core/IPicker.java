package com.winning.ants.core;

import java.util.List;

/**
 * 数据提取接口
 */
public interface IPicker<T> {

    List<T> pick();
}
