package com.winning.ants.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Record {

    private String scheme;

    private String tableName;

    private List<KeyValue> primaryKeys = new ArrayList<>();

    private List<KeyValue> columns = new ArrayList<>();
}
