package com.winning.ants.core.base.dbops;

import com.alibaba.druid.pool.DruidDataSource;
import com.winning.ants.core.utils.StringManager;
import com.winning.ants.log.ILogger;
import com.winning.ants.log.LoggerFactory;
import com.winning.ants.model.datasource.AntDatasource;
import com.winning.ants.model.datasource.DatasourceConfig;
import com.winning.ants.model.datasource.DbType;

import javax.sql.DataSource;

public class MysqlHelper implements IDbHelper{
    private static final StringManager sm = StringManager.getManager(MysqlHelper.class);

    public final static ILogger log = LoggerFactory.getInstance(MysqlHelper.class);
    private static final MysqlHelper helper = new MysqlHelper();

    static {
        DbHelperChooser.getInstance().register( DbType.MYSQL ,helper);
    }

    private MysqlHelper(){}

    public static MysqlHelper getInstance(){
        return helper;
    }


    @Override
    public AntDatasource createDs(DatasourceConfig config) {
        AntDatasource antDatasource = IDbHelper.super.createDs(config);
        DataSource ds = antDatasource.getDataSource();
        if(ds != null && ds instanceof DruidDataSource){
            DruidDataSource dataSource = (DruidDataSource) ds;
            dataSource.addConnectionProperty("useServerPrepStmts", "false");
            dataSource.addConnectionProperty("rewriteBatchedStatements", "true");
            dataSource.addConnectionProperty("allowMultiQueries", "true");
            dataSource.addConnectionProperty("readOnlyPropagatesToServer", "false");
            dataSource.setValidationQuery("select 1");
            try {
                dataSource.setExceptionSorter("com.alibaba.druid.pool.vendor.MySqlExceptionSorter");
                dataSource.setValidConnectionCheckerClassName("com.alibaba.druid.pool.vendor.MySqlValidConnectionChecker");
            } catch (Exception e) {
                String msg = sm.getString("mysqlHelper.createDs", config);
                log.debug(msg, e);
                return antDatasource.setDataSource(null).setCreateSuccess(false);
            }

        }
        return antDatasource;
    }
}
