package com.winning.ants.core.base.dbops;

import com.alibaba.druid.pool.DruidDataSource;
import com.winning.ants.model.datasource.AntDatasource;
import com.winning.ants.model.datasource.DatasourceConfig;
import com.winning.ants.model.datasource.DbType;

import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作接口
 */
public interface IDbHelper {

    int maxWait = 5 * 1000;
    int minIdle = 0;
    int initialSize = 0;

    int maxActive = 32;


    default AntDatasource createDs(DatasourceConfig config){
        AntDatasource ds = new AntDatasource();
        final String url = config.getUrl();
        final String userName = config.getUsername();
        final String password = config.getPassword();
        final Properties props = config.getProperties();
        final DbType dbType = config.getDbType();
        try {
            int maxActive = Integer.valueOf(props.getProperty("maxActive", "10"));
            if (maxActive < 0) {
                maxActive = 200;
            }
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            dataSource.setUseUnfairLock(true);
            dataSource.setNotFullTimeoutRetryCount(2);
            dataSource.setInitialSize(initialSize);
            dataSource.setMinIdle(minIdle);
            dataSource.setMaxActive(maxActive);
            dataSource.setMaxWait(maxWait);
            dataSource.setDriverClassName(dbType.getDriverClassName());
            // 动态的参数
            if (props != null && props.size() > 0) {
                for (Map.Entry<Object, Object> entry : props.entrySet()) {
                    dataSource.addConnectionProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
            return ds.setDataSource(dataSource).setCreateSuccess(true);
        } catch (Throwable e) {
            //todo logs
            return ds.setDataSource(null).setCreateSuccess(false);
        }
    }



}
