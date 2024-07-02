package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DbMsSQLTableBean implements Serializable {
    private String tableName;
    private BigInteger totalReservedBytes;
    private BigInteger usedSpaceBytes;
    private BigInteger rowsCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp statisticsUpdateDate;

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public BigInteger getTotalReservedBytes() {
        return totalReservedBytes;
    }
    public void setTotalReservedBytes(BigInteger totalReservedBytes) {
        this.totalReservedBytes = totalReservedBytes;
    }
    public BigInteger getUsedSpaceBytes() {
        return usedSpaceBytes;
    }
    public void setUsedSpaceBytes(BigInteger usedSpaceBytes) {
        this.usedSpaceBytes = usedSpaceBytes;
    }
    public BigInteger getRowsCount() {
        return rowsCount;
    }
    public void setRowsCount(BigInteger rowsCount) {
        this.rowsCount = rowsCount;
    }
    public Timestamp getStatisticsUpdateDate() {
        return statisticsUpdateDate;
    }
    public void setStatisticsUpdateDate(Timestamp statisticsUpdateDate) {
        this.statisticsUpdateDate = statisticsUpdateDate;
    }
}
