package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.RelationInfo;
import com.alfresco.support.alfrescodb.model.Workflow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DbSizeMapper {
    @Select("SELECT table_name as tableName, pg_size_pretty(total_bytes) AS total, " +
            "row_estimate as rowEstimate , pg_size_pretty(index_bytes) AS INDEX, pg_size_pretty(table_bytes) AS TABLE " +
            "FROM (SELECT *, total_bytes-index_bytes-COALESCE(toast_bytes,0) AS table_bytes " +
            "FROM (SELECT c.oid,nspname AS table_schema, relname AS TABLE_NAME, " +
            "c.reltuples AS row_estimate, pg_total_relation_size(c.oid) AS total_bytes, " +
            "pg_indexes_size(c.oid) AS index_bytes, pg_total_relation_size(reltoastrelid) AS toast_bytes " +
            "FROM pg_class c LEFT JOIN pg_namespace n ON n.oid = c.relnamespace " +
            "WHERE relkind = 'r') a) a " +
            "where table_schema = 'public' order by total_bytes desc") //SQL
    List<RelationInfo> findTablesInfo();

    @Select("SELECT pg_catalog.pg_size_pretty(pg_catalog.pg_database_size(current_database()));") //SQL
    String findDbSize();
}