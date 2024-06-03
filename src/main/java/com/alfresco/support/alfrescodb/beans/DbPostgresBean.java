package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;


public class DbPostgresBean implements Serializable{
    private String schemaName;
    private String tableName;
    private BigInteger rowEstimates;
    private BigInteger tableSizeBytes;
    private String prettySize;
    private BigInteger indexSizeBytes;
    private Timestamp lastVacuum;
    private Timestamp lastAutoVacuum;
    private Timestamp lastAnalyze;
    private Timestamp lastAutoAnalyze;

    public String getSchemaName() {
        return schemaName;
    }
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public BigInteger getRowEstimates() {
        return rowEstimates;
    }
    public void setRowEstimates(BigInteger rowEstimates) {
        this.rowEstimates = rowEstimates;
    }

    public BigInteger getTableSizeBytes() {
        return tableSizeBytes;
    }
    public void setTableSizeBytes(BigInteger tableSizeBytes) {
        this.tableSizeBytes = tableSizeBytes;
    }

    public String getPrettySize() {
        return prettySize;
    }
    public void setPrettySize(String prettySize) {
        this.prettySize = prettySize;
    }

    public BigInteger getIndexSizeBytes() {
        return indexSizeBytes;
    }
    public void setIndexSizeBytes(BigInteger indexSizeBytes) {
        this.indexSizeBytes = indexSizeBytes;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public Timestamp getLastVacuum() {
        return lastVacuum;
    }
    public void setLastVacuum(Timestamp lastVacuum) {
        this.lastVacuum = lastVacuum;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public Timestamp getLastAutoVacuum() {
        return lastAutoVacuum;
    }
    public void setLastAutoVacuum(Timestamp lastAutoVacuum) {
        this.lastAutoVacuum = lastAutoVacuum;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public Timestamp getLastAnalyze() {
        return lastAnalyze;
    }
    public void setLastAnalyze(Timestamp lastAnalyze) {
        this.lastAnalyze = lastAnalyze;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public Timestamp getLastAutoAnalyze() {
        return lastAutoAnalyze;
    }
    public void setLastAutoAnalyze(Timestamp lastAutoAnalyze) {
        this.lastAutoAnalyze = lastAutoAnalyze;
    }
}
