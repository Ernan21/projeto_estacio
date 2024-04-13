package com.okayjava.html.controller;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TableInfo {
    @Id
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}