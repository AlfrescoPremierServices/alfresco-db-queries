package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.MSSqlRelationInfo;
import com.alfresco.support.alfrescodb.model.OracleRelationInfo;
import com.alfresco.support.alfrescodb.model.RelationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Select("select sum(bytes)/1048576 SizeMB, segment_name tableName\n" +
            "from user_extents\n" +
            "where segment_name in (\n" +
            "     select table_name from all_tables)\n" +
            "group by segment_name\n" +
            "order by 1 desc")
    List<OracleRelationInfo> findTablesInfoOracle();

    @Select("select sum(u.bytes)/1048576 SizeMB, u.segment_name indexName, i.table_name tableName\n" +
            "from user_extents u\n" +
            "join all_ind_columns i\n" +
            "     on u.segment_name = i.index_name\n" +
            "     and i.column_position = 1\n" +
            "group by u.segment_name, i.table_name\n" +
            "order by 1 desc")
    List<OracleRelationInfo> findIndexesInfoOracle();

    // MS SQL Queries
    @Select("SELECT \n" +
            "    t.NAME AS TableName,\n" +
            "    p.rows AS RowCounts,\n" +
            "    (SUM(a.total_pages) * 8 / 1024) AS TotalSpace, \n" +
            "    (SUM(a.used_pages) * 8 / 1024) AS UsedSpace, \n" +
            "    ((SUM(a.total_pages) - SUM(a.used_pages)) * 8 / 1024) AS UnusedSpace\n" +
            "FROM \n" +
            "    sys.tables t\n" +
            "INNER JOIN \n" +
            "    sys.schemas s ON s.schema_id = t.schema_id\n" +
            "INNER JOIN      \n" +
            "    sys.indexes i ON t.OBJECT_ID = i.object_id\n" +
            "INNER JOIN \n" +
            "    sys.partitions p ON i.object_id = p.OBJECT_ID AND i.index_id = p.index_id\n" +
            "INNER JOIN \n" +
            "    sys.allocation_units a ON p.partition_id = a.container_id\n" +
            "WHERE \n" +
            "    t.NAME NOT LIKE 'dt%'    -- filter out system tables for diagramming\n" +
            "    AND t.is_ms_shipped = 0\n" +
            "    AND i.OBJECT_ID > 255 \n" +
            "GROUP BY \n" +
            "    t.Name, s.Name, p.Rows\n" +
            "ORDER BY \n" +
            "    TotalSpace DESC")
    List<MSSqlRelationInfo> findTablesInfoMSSql();

    @Select("SELECT\n" +
            "OBJECT_NAME(i.OBJECT_ID) AS TableName,\n" +
            "i.name AS IndexName,\n" +
            "i.index_id AS IndexID,\n" +
            "(8 * SUM(a.used_pages) / 1024) AS 'IndexSize'\n" +
            "FROM sys.indexes AS i\n" +
            "JOIN sys.partitions AS p ON p.OBJECT_ID = i.OBJECT_ID AND p.index_id = i.index_id\n" +
            "JOIN sys.allocation_units AS a ON a.container_id = p.partition_id\n" +
            "GROUP BY i.OBJECT_ID,i.index_id,i.name\n" +
            "ORDER BY IndexSize DESC")
    List<MSSqlRelationInfo> findIndexesInfoMSSql();
}