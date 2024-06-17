package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;


public class DbMySQLBean implements Serializable {
    private String schemaName;
    private String tableName;
    private String engine;
    private String rowEstimates;
    private BigInteger tableSizeBytes;
    private String totalSizeBytes;
    private BigInteger indexSizeBytes;

    public String getSchemaName() {
        return schemaName;
    }
    public void setSchemaName(String schemaname) {
        this.schemaName = schemaname;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tablename) {
        this.tableName = tablename;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(String engine) {
        this.engine = engine;
    }
    public String getRowEstimates() {
        return rowEstimates;
    }
    public void setRowEstimates(String rowestimates) {
        this.rowEstimates = rowestimates;
    }
    public BigInteger getTableSizeBytes() {
        return tableSizeBytes;
    }
    public void setTableSizeBytes(BigInteger table_size_bytes) {
        this.tableSizeBytes = table_size_bytes;
    }
    public String getTotalSizeBytes() {
        return totalSizeBytes;
    }
    public void setTotalSizeBytes(String total_size_bytes) {
        this.totalSizeBytes = total_size_bytes;
    }
    public BigInteger getIndexSizeBytes() {
        return indexSizeBytes;
    }
    public void setIndexSizeBytes(BigInteger index_size_bytes) {
        this.indexSizeBytes = index_size_bytes;
    }    
}
