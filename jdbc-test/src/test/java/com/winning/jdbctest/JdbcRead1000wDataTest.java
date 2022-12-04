package com.winning.jdbctest;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.common.base.Stopwatch;
import com.winning.jdbctest.base.JdbcTestBase;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class JdbcRead1000wDataTest extends JdbcTestBase {
    public static final String QUERY_SQL = "SELECT  * FROM student";
    @Test
    public void read1000WData(){
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            DruidPooledConnection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL ,ResultSet.TYPE_FORWARD_ONLY);
            ResultSet resultSet = preparedStatement.executeQuery(QUERY_SQL);
            long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            System.out.println("insert100wDataWithBatch：" + time + "ms");
            stopwatch.stop();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
