package com.alfresco.support.alfrescodb.export.beans;

import java.io.Serializable;
import java.math.BigInteger;


public class DbMySQLBean implements Serializable {
    private String schemaname;
    private String tablename;
    private String engine;
    private String rowestimates;
    private BigInteger table_size_bytes;
    private String total_size_bytes;
    private BigInteger index_size_bytes;

    public String getSchemaname() {
        return schemaname;
    }
    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }
    public String getTablename() {
        return tablename;
    }
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public String getRowestimates() {
        return rowestimates;
    }
    public void setRowestimates(String rowestimates) {
        this.rowestimates = rowestimates;
    }
    public BigInteger getTable_size_bytes() {
        return table_size_bytes;
    }
    public void setTable_size_bytes(BigInteger table_size_bytes) {
        this.table_size_bytes = table_size_bytes;
    }
    public String getTotal_size_bytes() {
        return total_size_bytes;
    }
    public void setTotal_size_bytes(String total_size_bytes) {
        this.total_size_bytes = total_size_bytes;
    }
    public BigInteger getIndex_size_bytes() {
        return index_size_bytes;
    }
    public void setIndex_size_bytes(BigInteger index_size_bytes) {
        this.index_size_bytes = index_size_bytes;
    }    
}
