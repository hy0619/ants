package com.winning.ants.core.base.dbops;

import com.winning.ants.model.datasource.DatasourceConfig;

import java.util.List;

public interface IDsConfigLoader {

    List<DatasourceConfig> load();
}
