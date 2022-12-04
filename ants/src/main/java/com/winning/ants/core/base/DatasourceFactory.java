package com.winning.ants.core.base;

import com.alibaba.druid.pool.DruidDataSource;
import com.winning.ants.core.base.LifecycleBase;
import com.winning.ants.model.datasource.AntDatasource;
import com.winning.ants.core.base.dbops.DbHelperChooser;
import com.winning.ants.core.base.dbops.IDbHelper;
import com.winning.ants.core.base.dbops.IDsConfigLoader;
import com.winning.ants.model.datasource.DatasourceConfig;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源工厂
 */
public class DatasourceFactory extends LifecycleBase {

    private static final DatasourceFactory INSTANCE = new DatasourceFactory();

    public DatasourceFactory getInstance(){
        return INSTANCE;
    }
    private IDsConfigLoader configLoader;

    private static DbHelperChooser dbHelperChooser = DbHelperChooser.getInstance();

    private DatasourceFactory(){}
    private Map<DatasourceConfig , AntDatasource> dataSourceMap ;

    public void setConfigLoader(IDsConfigLoader configLoader){
        this.configLoader = configLoader;
    }

    /**
     * 是否是创建成功的数据源
     * @param config
     * @return
     */
    public boolean isCreatedDs(DatasourceConfig config){
        AntDatasource dataSource = this.getDataSource(config);
        return dataSource == null ? false : dataSource.isCreateSuccess();
    }

    public AntDatasource getDataSource(DatasourceConfig config) {
        return dataSourceMap.get(config);
    }
    @Override
    protected void doInit() {
        dataSourceMap = new ConcurrentHashMap<>();
    }

    @Override
    protected void doStart() {
        if(null == dataSourceMap){
            doInit();
        }
        if(null == configLoader){
            // todo
            //  defaultConfigLoader
        }
        List<DatasourceConfig> configList = configLoader.load();
        if(configList == null || configList.isEmpty()){
            return;
        }
        for(DatasourceConfig dsConfig : configList){
            IDbHelper dbHelper = DbHelperChooser.choose(dsConfig.getDbType());
            dataSourceMap.put(dsConfig , dbHelper.createDs(dsConfig));
        }
    }



    @Override
    protected void doStop() {
        for (AntDatasource source : dataSourceMap.values()) {
            if(source.isCreateSuccess()){
                DataSource ds = source.getDataSource();
                if(ds instanceof DruidDataSource){
                    DruidDataSource dataSource = (DruidDataSource)ds;
                    dataSource.close();
                }
            }
        }

        dataSourceMap.clear();
    }
}
