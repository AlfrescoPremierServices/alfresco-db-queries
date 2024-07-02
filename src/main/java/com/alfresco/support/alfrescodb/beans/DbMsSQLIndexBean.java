package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DbMsSQLIndexBean implements Serializable {
    private String tableName;
    private String indexName;
    private Integer indexID;
    private BigInteger indexSizeBytes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp statisticsUpdateDate;

    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getIndexName() {
        return indexName;
    }
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
    public Integer getIndexID() {
        return indexID;
    }
    public void setIndexID(Integer indexID) {
        this.indexID = indexID;
    }
    public BigInteger getIndexSizeBytes() {
        return indexSizeBytes;
    }
    public void setIndexSizeBytes(BigInteger indexSizeBytes) {
        this.indexSizeBytes = indexSizeBytes;
    }
    public Timestamp getStatisticsUpdateDate() {
        return statisticsUpdateDate;
    }
    public void setStatisticsUpdateDate(Timestamp statisticsUpdateDate) {
        this.statisticsUpdateDate = statisticsUpdateDate;
    }
}
