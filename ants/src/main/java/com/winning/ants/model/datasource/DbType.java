package com.winning.ants.model.datasource;

public enum DbType {
    MYSQL(1, "com.xxxx");
    private int code;
    private String driverClassName;
    DbType(int code, String driverClassName) {
        this.code = code;
        this.driverClassName = driverClassName;
    }

    public int getCode() {
        return code;
    }

    public String getDriverClassName() {
        return driverClassName;
    }
}
