package com.winning.jdbctest.base;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JdbcTestBase {

    public volatile static DruidDataSource dataSource ;


    @BeforeAll
    public static void initDs(){
        InputStream in  = ClassLoader.getSystemResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataSource = new DruidDataSource();
        dataSource.setName((String)properties.get("druid.name"));
        dataSource.setUrl((String)properties.get("druid.url"));
        dataSource.setUsername((String)properties.get("druid.username"));
        dataSource.setPassword((String)properties.get("druid.password"));
        dataSource.setMaxActive(Integer.valueOf((String)properties.get("druid.maxActive")));
        dataSource.setInitialSize(Integer.valueOf((String)properties.get("druid.initialSize")));
        dataSource.setMinIdle(Integer.valueOf((String)properties.get("druid.minIdle")));
        //dataSource = dataSource;
    }

    @AfterAll
    public static void clean(){
        dataSource.close();
    }

}
