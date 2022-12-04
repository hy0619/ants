package com.winning.jdbctest;

import cn.hutool.core.util.IdcardUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.winning.jdbctest.base.JdbcTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JdbcTestApplication extends JdbcTestBase {
    public static String STUDENT_FORMAT = "insert into student({0}) values ('{1}');";


    @Test
    public void testJdbcExecute(){
        DruidDataSource ds =JdbcTestBase.dataSource;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        String studentSql = STUDENT_FORMAT.replace("{0}" , "name")
                .replace("{1}" , "hello");

        jdbcTemplate.batchUpdate(studentSql);
    }



}
