package com.alfresco.support.alfrescodb.export.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;


public class DbPostgresBean implements Serializable{
    private String schemaname;
    private String tablename;
    private String rowestimates;
    private BigInteger table_size_bytes;
    private String pretty_size;
    private BigInteger index_size_bytes;
    private Timestamp last_vacuum;
    private Timestamp last_autovacuum;
    private Timestamp last_analyze;
    private Timestamp last_autoanalyze;

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

    public String getRowestimates() {
        return rowestimates;
    }
    public void setRowestimates(String rowestimates) {
        this.rowestimates = rowestimates;
    }

    public BigInteger getTable_size_bytes() {
        return table_size_bytes;
    }
    public void setTable_size_bytes(BigInteger table_size) {
        this.table_size_bytes = table_size;
    }

    public String getPretty_size() {
        return pretty_size;
    }
    public void setPretty_size(String pretty_size) {
        this.pretty_size = pretty_size;
    }

    public BigInteger getIndex_size_bytes() {
        return index_size_bytes;
    }
    public void setIndex_size_bytes(BigInteger index_bytes) {
        this.index_size_bytes = index_bytes;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public Timestamp getLast_vacuum() {
        return last_vacuum;
    }
    public void setLast_vacuum(Timestamp last_vacuum) {
        this.last_vacuum = last_vacuum;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public Timestamp getLast_autovacuum() {
        return last_autovacuum;
    }
    public void setLast_autovacuum(Timestamp last_autovacuum) {
        this.last_autovacuum = last_autovacuum;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public Timestamp getLast_analyze() {
        return last_analyze;
    }
    public void setLast_analyze(Timestamp last_analyze) {
        this.last_analyze = last_analyze;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    public Timestamp getLast_autoanalyze() {
        return last_autoanalyze;
    }
    public void setLast_autoanalyze(Timestamp last_autoanalyze) {
        this.last_autoanalyze = last_autoanalyze;
    }
}
