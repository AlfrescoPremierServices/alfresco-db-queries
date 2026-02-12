package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DbOracleIndexBean implements Serializable {
    private String indexName;
    private BigInteger bytes;
    private String tableName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss.SSSZ")
    private Timestamp lastAnalyzed;
    private BigInteger numRows;
    private BigInteger distinctKeys;
    private String status;
    
    public String getIndexName() {
        return indexName;
    }
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
    public BigInteger getBytes() {
        return bytes;
    }
    public void setBytes(BigInteger bytes) {
        this.bytes = bytes;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public Timestamp getLastAnalyzed() {
        return lastAnalyzed;
    }
    public void setLastAnalyzed(Timestamp lastAnalyzed) {
        this.lastAnalyzed = lastAnalyzed;
    }
    public BigInteger getNumRows() {
        return numRows;
    }
    public void setNumRows(BigInteger numRows) {
        this.numRows = numRows;
    }
    public BigInteger getDistinctKeys() {
        return distinctKeys;
    }
    public void setDistinctKeys(BigInteger distinctKeys) {
        this.distinctKeys = distinctKeys;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
