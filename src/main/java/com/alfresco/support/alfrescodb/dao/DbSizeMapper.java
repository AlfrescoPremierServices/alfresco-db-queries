package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.MSSqlRelationInfo;
import com.alfresco.support.alfrescodb.model.OracleRelationInfo;
import com.alfresco.support.alfrescodb.model.RelationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Value;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface DbSizeMapper {

    // Postgres Queries
    @Select("SELECT table_name as tableName, (total_bytes / 1024 / 1024) AS total, " +
            "row_estimate as rowEstimate , (index_bytes / 1024 / 1024) AS INDEX, (table_bytes / 1024 / 1024) AS TABLE " +
            "FROM (SELECT *, total_bytes-index_bytes-COALESCE(toast_bytes,0) AS table_bytes " +
            "FROM (SELECT c.oid,nspname AS table_schema, relname AS TABLE_NAME, " +
            "c.reltuples AS row_estimate, pg_total_relation_size(c.oid) AS total_bytes, " +
            "pg_indexes_size(c.oid) AS index_bytes, pg_total_relation_size(reltoastrelid) AS toast_bytes " +
            "FROM pg_class c LEFT JOIN pg_namespace n ON n.oid = c.relnamespace " +
            "WHERE relkind = 'r') a) a " +
            "where table_schema = 'public' order by total_bytes desc")
    List<RelationInfo> findTablesInfoPostgres();

    @Select("SELECT pg_catalog.pg_size_pretty(pg_catalog.pg_database_size(current_database()));")
    String findDbSizePostgres();

    // MySQL Queries
    @Select("SELECT TABLE_NAME 'tableName', round(((data_length + index_length) / 1024 / 1024),2) 'total', " +
            "table_rows 'rowEstimate', round(((index_length) / 1024 / 1024),2) 'index',  " +
            "round(((data_length) / 1024 / 1024),2) 'table' " +
            "FROM information_schema.TABLES " +
            "WHERE table_schema like '%' and TABLE_TYPE='BASE TABLE' ORDER BY data_length DESC")
    List<RelationInfo> findTablesInfoMysql();


    @Select("SELECT concat(Round(Sum(data_length + index_length) / 1024 / 1024, 1), ' MB') 'total'" +
            "FROM information_schema.tables")
    String findDbSizeMysql();

    // Oracle Queries
    @Select("select sum(bytes)/1048576 SizeMB, segment_name tableName" +
            "from user_extents" +
            "where segment_name in (" +
            "     select table_name from all_tables)" +
            "group by segment_name")
    List<OracleRelationInfo> findTablesInfoOracle();

    @Select("select sum(u.bytes)/1048576 SizeMB, u.segment_name indexName, i.table_name tableName" +
            "from user_extents u" +
            "join all_ind_columns i" +
            "     on u.segment_name = i.index_name" +
            "     and i.column_position = 1" +
            "group by u.segment_name, i.table_name")
    List<OracleRelationInfo> findIndexesInfoOracle();

    // MS SQL Queries
    @Select("SELECT " +
            "    t.NAME AS TableName," +
            "    p.rows AS RowCounts," +
            "    (SUM(a.total_pages) * 8 / 1024) AS TotalSpace, " +
            "    (SUM(a.used_pages) * 8 / 1024) AS UsedSpace, " +
            "    ((SUM(a.total_pages) - SUM(a.used_pages)) * 8 / 1024) AS UnusedSpace" +
            "FROM " +
            "    sys.tables t" +
            "INNER JOIN " +
            "    sys.schemas s ON s.schema_id = t.schema_id" +
            "INNER JOIN      " +
            "    sys.indexes i ON t.OBJECT_ID = i.object_id" +
            "INNER JOIN " +
            "    sys.partitions p ON i.object_id = p.OBJECT_ID AND i.index_id = p.index_id" +
            "INNER JOIN " +
            "    sys.allocation_units a ON p.partition_id = a.container_id" +
            "WHERE " +
            "    t.NAME NOT LIKE 'dt%'    -- filter out system tables for diagramming" +
            "    AND t.is_ms_shipped = 0" +
            "    AND i.OBJECT_ID > 255 " +
            "GROUP BY " +
            "    t.Name, s.Name, p.Rows")
    List<MSSqlRelationInfo> findTablesInfoMSSql();

    @Select("SELECT" +
            "OBJECT_NAME(i.OBJECT_ID) AS TableName," +
            "i.name AS IndexName," +
            "i.index_id AS IndexID," +
            "(8 * SUM(a.used_pages) / 1024) AS 'IndexSize'" +
            "FROM sys.indexes AS i" +
            "JOIN sys.partitions AS p ON p.OBJECT_ID = i.OBJECT_ID AND p.index_id = i.index_id" +
            "JOIN sys.allocation_units AS a ON a.container_id = p.partition_id" +
            "GROUP BY i.OBJECT_ID,i.index_id,i.name")
    List<MSSqlRelationInfo> findIndexesInfoMSSql();
}