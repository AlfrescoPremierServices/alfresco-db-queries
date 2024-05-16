package com.alfresco.support.alfrescodb.export.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;


public class DbMSSQLBean implements Serializable{
    private String schemaname;
    private String tablename;
    private String rowestimates;
    private BigInteger table_size;
    private String pretty_size;
    private BigInteger index_bytes;
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
    public BigInteger getTable_size() {
        return table_size;
    }
    public void setTable_size(BigInteger table_size) {
        this.table_size = table_size;
    }
    public String getPretty_size() {
        return pretty_size;
    }
    public void setPretty_size(String pretty_size) {
        this.pretty_size = pretty_size;
    }
    public BigInteger getIndex_bytes() {
        return index_bytes;
    }
    public void setIndex_bytes(BigInteger index_bytes) {
        this.index_bytes = index_bytes;
    }
    public Timestamp getLast_vacuum() {
        return last_vacuum;
    }
    public void setLast_vacuum(Timestamp last_vacuum) {
        this.last_vacuum = last_vacuum;
    }
    public Timestamp getLast_autovacuum() {
        return last_autovacuum;
    }
    public void setLast_autovacuum(Timestamp last_autovacuum) {
        this.last_autovacuum = last_autovacuum;
    }
    public Timestamp getLast_analyze() {
        return last_analyze;
    }
    public void setLast_analyze(Timestamp last_analyze) {
        this.last_analyze = last_analyze;
    }
    public Timestamp getLast_autoanalyze() {
        return last_autoanalyze;
    }
    public void setLast_autoanalyze(Timestamp last_autoanalyze) {
        this.last_autoanalyze = last_autoanalyze;
    }
}
