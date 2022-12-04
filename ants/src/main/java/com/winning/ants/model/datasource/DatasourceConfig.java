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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((encode == null) ? 0 : encode.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        result = prime * result + ((dbType == null) ? 0 : dbType.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DatasourceConfig other = (DatasourceConfig) obj;
        if (encode == null) {
            if (other.encode != null) return false;
        } else if (!encode.equals(other.encode)) return false;
        if (password == null) {
            if (other.password != null) return false;
        } else if (!password.equals(other.password)) return false;
        if (properties == null) {
            if (other.properties != null) return false;
        } else if (!properties.equals(other.properties)) return false;
        if (dbType != other.dbType) return false;
        if (url == null) {
            if (other.url != null) return false;
        } else if (!url.equals(other.url)) return false;
        if (username == null) {
            if (other.username != null) return false;
        } else if (!username.equals(other.username)) return false;
        return true;
    }

}
