package com.winning.jdbctest;

import cn.hutool.core.date.StopWatch;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.common.base.Stopwatch;
import com.winning.jdbctest.base.JdbcTestBase;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JdbcSyncTest extends JdbcTestBase {
    public LinkedBlockingQueue<String> linkedBlockingDeque = new LinkedBlockingQueue<>(FETCH_SIZE * 2);;
    public volatile static int  FETCH_SIZE = 5000;

    public  volatile int  status = 0 ;

    public static final String QUERY_SQL = "SELECT  * FROM student";

    @Test
    public void syncData() throws SQLException {
        StopWatch readWatch = new StopWatch("读数据");
        readWatch.start();
        StopWatch stopwatch = new StopWatch("写数据");
        stopwatch.start();
        Thread t = new Thread(() -> {

            DruidPooledConnection connection ;
            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            PreparedStatement ps  = null;
            try {
                ps = connection.prepareStatement("insert into student1(name) values (?)");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int i = 0 ;
            for( ; ; ){
                String name  = linkedBlockingDeque.poll();
                if (null == name){
                    continue;
                }
                i++;
                if(status == 1){
                    try {
                        if(null != ps){
                            ps.executeBatch();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                try {
                    ps.setString(1, name);
                    ps.addBatch();
                    if(i % 3000 == 0){
                        ps.executeBatch();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }
            stopwatch.prettyPrint();
            stopwatch.stop();
        });
        t.start();
        ResultSet rs = read();
        rs.setFetchSize(FETCH_SIZE);
        while(rs.next()){
            String name = rs.getString("name");
            try {
                linkedBlockingDeque.put(name);
            } catch (InterruptedException e) {

            }
        }
        readWatch.prettyPrint();
       readWatch.stop();
        rs.close();
        // end
        status = 1;
    }


    public ResultSet read(){
        try {
            DruidPooledConnection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SQL , ResultSet.TYPE_FORWARD_ONLY);
            ResultSet resultSet = preparedStatement.executeQuery(QUERY_SQL);
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
