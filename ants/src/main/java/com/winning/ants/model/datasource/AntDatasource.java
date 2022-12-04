package com.winning.ants.model.datasource;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.sql.DataSource;

@Data
@Accessors(chain = true)
public class AntDatasource {

    private DataSource dataSource;

    private boolean createSuccess;


}
