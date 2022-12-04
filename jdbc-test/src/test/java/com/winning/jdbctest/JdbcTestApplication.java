package com.winning.jdbctest;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JdbcTestApplication {

    public static String STUDENT_FORMAT = "insert into student({0}) values ('{1}');";



    @Test
    public void testJdbcExecute(){
        DruidDataSource ds = initDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        String studentSql = STUDENT_FORMAT.replace("{0}" , "name")
                .replace("{1}" , "hello");

        jdbcTemplate.batchUpdate(studentSql);
    }


    public static DruidDataSource initDataSource(){
        InputStream in  = ClassLoader.getSystemResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName((String)properties.get("druid.name"));
        dataSource.setUrl((String)properties.get("druid.url"));
        dataSource.setUsername((String)properties.get("druid.username"));
        dataSource.setPassword((String)properties.get("druid.password"));
        dataSource.setMaxActive(Integer.valueOf((String)properties.get("druid.maxActive")));
        dataSource.setInitialSize(Integer.valueOf((String)properties.get("druid.initialSize")));
        dataSource.setMinIdle(Integer.valueOf((String)properties.get("druid.minIdle")));
        return dataSource;
    }

}
