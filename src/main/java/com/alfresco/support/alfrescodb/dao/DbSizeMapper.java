package com.alfresco.support.alfrescodb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.alfresco.support.alfrescodb.model.MSSqlRelationInfo;
import com.alfresco.support.alfrescodb.model.OracleRelationInfo;
import com.alfresco.support.alfrescodb.model.RelationInfo;

@Mapper
public interface DbSizeMapper {

    // Postgres Queries
    @Select("SELECT table_schema as tableSchema, table_name as tableName, (total_bytes) AS total, " +
            "row_estimate as rowEstimate , (index_bytes) AS INDEX, (table_bytes) AS TABLE " +
            "FROM (SELECT *, total_bytes-index_bytes-COALESCE(toast_bytes,0) AS table_bytes " +
            "FROM (SELECT c.oid,nspname AS table_schema, relname AS TABLE_NAME, " +
            "c.reltuples AS row_estimate, pg_total_relation_size(c.oid) AS total_bytes, " +
            "pg_indexes_size(c.oid) AS index_bytes, pg_total_relation_size(reltoastrelid) AS toast_bytes " +
            "FROM pg_class c LEFT JOIN pg_namespace n ON n.oid = c.relnamespace " +
            "WHERE relkind = 'r') a) a order by table_schema, total_bytes desc")
    List<RelationInfo> findTablesInfoPostgres();

    @Select("SELECT pg_catalog.pg_database_size(current_database());")
    String findDbSizePostgres();


    // MySQL Queries/
    @Select("SELECT TABLE_SCHEMA 'tableSchema', TABLE_NAME 'tableName', (data_length + index_length) 'total', " +
            "table_rows 'rowEstimate', index_length 'index',  " +
            "data_length 'table' " +
            "FROM information_schema.TABLES " +
            "WHERE table_schema like '%' and TABLE_TYPE='BASE TABLE' ORDER BY TABLE_SCHEMA, data_length DESC")
    List<RelationInfo> findTablesInfoMysql();

    @Select("SELECT Sum(data_length + index_length) 'total' FROM information_schema.tables")
    String findDbSizeMysql();


    // Oracle Queries
    @Select("select sum(bytes) Size, segment_name tableName " +
            "from user_extents " +
            "where segment_name in (select table_name from all_tables) " +
            "group by segment_name")
    List<OracleRelationInfo> findTablesInfoOracle();

    @Select("select sum(u.bytes) Size, u.segment_name indexName, i.table_name tableName " +
            "from user_extents u " +
            "join all_ind_columns i " +
            " on u.segment_name = i.index_name " +
            " and i.column_position = 1 " +
            "group by u.segment_name, i.table_name")
    List<OracleRelationInfo> findIndexesInfoOracle();

    // MS SQL Queries
    @Select("SELECT \n" +
            " t.NAME AS TableName, p.rows AS RowCounts,\n" +
            " (SUM(a.total_pages) * 8 * 1024) AS TotalSpace, \n" +
            " (SUM(a.used_pages) * 8 * 1024) AS UsedSpace, \n" +
            " ((SUM(a.total_pages) - SUM(a.used_pages)) * 8 *1024 ) AS UnusedSpace \n" +
            "FROM sys.tables t \n" +
            "INNER JOIN sys.schemas s ON s.schema_id = t.schema_id \n" +
            "INNER JOIN sys.indexes i ON t.OBJECT_ID = i.object_id \n" +
            "INNER JOIN sys.partitions p ON i.object_id = p.OBJECT_ID AND i.index_id = p.index_id \n" +
            "INNER JOIN sys.allocation_units a ON p.partition_id = a.container_id \n" +
            "WHERE \n" +
            " t.NAME NOT LIKE 'dt%'    -- filter out system tables for diagramming \n" +
            " AND t.is_ms_shipped = 0 \n" +
            " AND i.OBJECT_ID > 255 \n" +
            "GROUP BY \n" +
            " t.Name, s.Name, p.Rows")
    List<MSSqlRelationInfo> findTablesInfoMSSql();

    @Select("SELECT\n" +
            " OBJECT_NAME(i.OBJECT_ID) AS TableName,\n" +
            " i.name AS IndexName,\n" +
            " i.index_id AS IndexID,\n" +
            " (8 * SUM(a.used_pages) * 1024) AS 'IndexSize'\n" +
            "FROM sys.indexes AS i\n" +
            "JOIN sys.partitions AS p ON p.OBJECT_ID = i.OBJECT_ID AND p.index_id = i.index_id \n" +
            "JOIN sys.allocation_units AS a ON a.container_id = p.partition_id \n" +
            "GROUP BY i.OBJECT_ID,i.index_id,i.name")
    List<MSSqlRelationInfo> findIndexesInfoMSSql();

}
