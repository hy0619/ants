package com.winning.jdbctest;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.common.base.Stopwatch;
import com.winning.jdbctest.base.JdbcTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Jdbc100wDataTest extends JdbcTestBase {

    public static final String SQl_TEMPLATE = "insert into student(name) values ('?')";
    public static final Integer INSERT_COUNT = 1000000;

  /*  public static void main(String[] args) {
        System.out.println(122 % 123);
    }
*/

    /**
     * rewriteBatchedStatements 参数开启
     * insert100wDataWithBatch：1459007ms
     * jdbcTemplate.batchUpdate(final String... sql) 批量提交后会从新获取连接 所以慢
     */
    @Test
    public void batchInsert(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> batchSqlList = new LinkedList<>();
        for (int i = 0 ; i < INSERT_COUNT; i++){
            batchSqlList.add(SQl_TEMPLATE.replace("?" , "姓名" + i));
            if(i % 3000 == 0 || i == INSERT_COUNT -1) {
                jdbcTemplate.batchUpdate(batchSqlList.toArray(new String[0]));
                batchSqlList.clear();
            }
        }


        long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("insert100wDataWithBatch：" + time + "ms");
        stopwatch.stop();
    }





    /**
     * 批量提交不关闭连接
     * 82099ms
     */
    @Test
    public void batchInsert2(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> batchList = new LinkedList<>();
        for (int i = 0 ; i < INSERT_COUNT; i++){
            batchList.add(i + "");
            if(i % 3000 == 0){
                jdbcTemplate.batchUpdate(" insert into student(name) values (?) ",
                        new BatchPreparedStatementSetter(){
                            @Override
                            public void setValues(PreparedStatement ps, int i)
                                    throws SQLException {
                                ps.setString(1, batchList.get(i));

                            }
                            @Override
                            public int getBatchSize() {
                                return batchList.size();
                            }
                        });
                batchList.clear();
            }
            }

        // 提交剩余sql
        jdbcTemplate.batchUpdate(" insert into student(name) values (?) ",
                new BatchPreparedStatementSetter(){
                    @Override
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        ps.setString(1, batchList.get(i));

                    }
                    @Override
                    public int getBatchSize() {
                        return batchList.size();
                    }
                });
        batchList.clear();
        long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("insert100wDataWithBatch：" + time + "ms");
        stopwatch.stop();
    }


    /**
     * 使用原生Jdbc插入数据
     * insert100wDataWithBatch：20770ms
     */
    @Test
    public void batchInsertJdbcNative(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            DruidPooledConnection connection = dataSource.getConnection();
            //connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("insert into student(name) values (?)");
            //ps.setFetchSize(3000);
            //ps.setMaxFieldSize(10000);
            for (int i = 0 ; i < INSERT_COUNT; i++){
                ps.setString(1, i +"");
                ps.addBatch();
                if((i+1) % 3000 == 0){
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
            //connection.commit();
            ps.clearBatch();
            long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            System.out.println("insert100wDataWithBatch：" + time + "ms");
            stopwatch.stop();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }finally {
            dataSource.close();
        }

    }


    @Test
    public void batchInsertJdbcNative2(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        DruidPooledConnection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("insert into student(name) values (?)");
            for (int i = 0 ; i < INSERT_COUNT; i++){
                ps.setString(1, i +"");
                ps.addBatch();
                if(i % 3000 == 0){
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
            ps.clearBatch();
            connection.commit();
            long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            System.out.println("insert100wDataWithBatch：" + time + "ms");
            stopwatch.stop();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            dataSource.close();
        }

    }



    @Test
    public void batchInsertJdbcNative3(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        DruidPooledConnection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into student (name) values ";
            StringBuilder sb = new StringBuilder();
            Statement ps = connection.createStatement();
            for (int i = 0 ; i < INSERT_COUNT; i++){
                sb.append("(")
                        .append("\"测试"+i)
                        .append("\"),");
                if((i+1) % 30000 == 0){
                    sb.deleteCharAt(sb.lastIndexOf(","));
                    String exSql =  new StringBuilder(sql).append(sb).toString();
                    System.out.println(exSql);

                    ps.addBatch(exSql);

                    sb = new StringBuilder();
                }
            }

            sb.deleteCharAt(sb.lastIndexOf(","));
            String exSql =  new StringBuilder(sql).append(sb).toString();
            ps.addBatch(exSql);
            ps.executeBatch();
            connection.commit();

            long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            System.out.println("insert100wDataWithBatch：" + time + "ms");
            stopwatch.stop();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally {
            dataSource.close();
        }

    }

}
