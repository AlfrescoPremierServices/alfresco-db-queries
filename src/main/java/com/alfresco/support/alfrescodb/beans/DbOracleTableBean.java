package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DbOracleTableBean implements Serializable {
    private String tableName;
    private BigInteger bytes;
    private BigInteger numRows;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss.SSSZ")
    private Timestamp lastAnalyzed;
    private BigInteger averageRowLength;

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public BigInteger getBytes() {
        return bytes;
    }
    public void setBytes(BigInteger bytes) {
        this.bytes = bytes;
    }
    public BigInteger getNumRows() {
        return numRows;
    }
    public void setNumRows(BigInteger numRows) {
        this.numRows = numRows;
    }
    public Timestamp getLastAnalyzed() {
        return lastAnalyzed;
    }
    public void setLastAnalyzed(Timestamp lastAnalyzed) {
        this.lastAnalyzed = lastAnalyzed;
    }
    public BigInteger getAverageRowLength() {
        return averageRowLength;
    }
    public void setAverageRowLength(BigInteger averageRowLength) {
        this.averageRowLength = averageRowLength;
    }
}
