package com.winning.ants.model.datasource;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Properties;

/**
 * 数据源配置类
 */
@Data
@Accessors(chain = true)
public class DatasourceConfig implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * url
     */
    private String url;

    /**
     * 编码
     */
    private String encode;

    /**
     * 连接属性
     */
    private Properties properties = new Properties();

    /**
     * 数据库类型
     */
    private DbType dbType;

}
