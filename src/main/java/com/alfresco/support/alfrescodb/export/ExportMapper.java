package com.alfresco.support.alfrescodb.export;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.alfresco.support.alfrescodb.export.beans.AccessControlBean;
import com.alfresco.support.alfrescodb.export.beans.AppliedPatchesBean;
import com.alfresco.support.alfrescodb.export.beans.DbMSSQLBean;
import com.alfresco.support.alfrescodb.export.beans.DbMySQLBean;
import com.alfresco.support.alfrescodb.export.beans.DbOracleBean;
import com.alfresco.support.alfrescodb.export.beans.DbPostgresBean;
import com.alfresco.support.alfrescodb.export.beans.LargeFolderBean;
import com.alfresco.support.alfrescodb.export.beans.LargeTransactionBean;

@Mapper
public interface ExportMapper {
    /*
     * Applied Patches
     */
    @Select("SELECT id, applied_to_schema appliedToSchema, applied_on_date appliedOnDate, " +
            "applied_to_server appliedToServer, was_executed wasExecuted, succeeded, report " +
            "FROM alf_applied_patch")
    List<AppliedPatchesBean> listAppliedPatches();

    /*
     * DB Size
     */
    // Postgres Queries
    @Select("SELECT pg_namespace.nspname schemaname, pg_class.relname tablename, pg_class.reltuples rowEstimates, " +
            "pg_relation_size(pg_catalog.pg_class.oid) table_size, pg_size_pretty(pg_relation_size(pg_catalog.pg_class.oid)) pretty_size, "
            +
            "pg_indexes_size(pg_class.oid) AS index_bytes, stats.last_vacuum, stats.last_autovacuum, stats.last_analyze, stats.last_autoanalyze "
            +
            "FROM pg_catalog.pg_class " +
            "JOIN pg_catalog.pg_namespace ON relnamespace = pg_catalog.pg_namespace.oid " +
            "left JOIN pg_catalog.pg_stat_all_tables stats on pg_class.oid = stats.relid " +
            "WHERE pg_namespace.nspname NOT LIKE 'pg_%' ")
    List<DbPostgresBean> findTablesInfoPostgres();

    // MySQL Queries/
    @Select("SELECT table_schema as schemaname, table_name as tablename, engine, " +
            "data_length as tableSize, index_length as indexSize, table_rows as rowEstimates, " +
            "(data_length +index_length) as total_size_bytes " +
            "FROM information_schema.TABLES " +
            "where table_schema not in ('sys','performance_schema','information_schema','mysql') ")
    List<DbMySQLBean> findTablesInfoMysql();

    // Oracle Queries
    @Select("select sum(bytes) Size, segment_name tableName " +
            "from user_extents " +
            "where segment_name in (select table_name from all_tables) " +
            "group by segment_name")
    List<DbOracleBean> findTablesInfoOracle();

    @Select("select sum(u.bytes) Size, u.segment_name indexName, i.table_name tableName " +
            "from user_extents u " +
            "join all_ind_columns i " +
            " on u.segment_name = i.index_name " +
            " and i.column_position = 1 " +
            "group by u.segment_name, i.table_name")
    List<DbOracleBean> findIndexesInfoOracle();

    // MS SQL Queries
    @Select("SELECT \n" +
            " s.Name as SchemaName, t.NAME AS TableName, p.rows AS RowCounts,\n" +
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
    List<DbMSSQLBean> findTablesInfoMSSql();

    @Select("SELECT\n" +
            " s.Name as SchemaName, OBJECT_NAME(i.OBJECT_ID) AS TableName,\n" +
            " i.name AS IndexName,\n" +
            " i.index_id AS IndexID,\n" +
            " (8 * SUM(a.used_pages) * 1024) AS 'IndexSize'\n" +
            "FROM sys.indexes AS i\n" +
            "JOIN sys.partitions AS p ON p.OBJECT_ID = i.OBJECT_ID AND p.index_id = i.index_id \n" +
            "JOIN sys.allocation_units AS a ON a.container_id = p.partition_id \n" +
            "JOIN sys.tables t ON t.OBJECT_ID = i.object_id \n" +
            "JOIN sys.schemas s ON s.schema_id = t.schema_id \n" +
            "GROUP BY i.OBJECT_ID,i.index_id,i.name")
    List<DbMSSQLBean> findIndexesInfoMSSql();

    /*
     * Large Folders
     */
    @Select("SELECT count(*) as occurrences, concat (stores.protocol, '://', stores.identifier, '/', nodes.uuid) as nodeRef, "
            +
            "props.string_value as nodeName, qname.local_name as localName " +
            "FROM alf_node as nodes, alf_store as stores, alf_child_assoc as children, alf_node_properties as props, alf_qname as qname "
            +
            "WHERE children.parent_node_id=nodes.id and stores.id=nodes.store_id and props.node_id = nodes.id and " +
            "props.qname_id IN (SELECT id FROM alf_qname WHERE local_name = 'name') and qname.id = nodes.type_qname_id "
            +
            "GROUP BY stores.protocol, stores.identifier, nodes.uuid, string_value, local_name HAVING count(*) > #{size} ")
    List<LargeFolderBean> findLargeFolders(@Param("size") int size);

    /*
     * Large Transactions
     */
    @Select("SELECT trx.id as trxId, count(*) as nodes " +
            "FROM alf_transaction trx, alf_node an " +
            "WHERE an.transaction_id = trx.id " +
            "GROUP BY trx.id HAVING count(*) > #{size} ")
    List<LargeTransactionBean> findLargeTransactions(@Param("size") int size);


    /*
     * Access Control List
     */
    @Select("select count(*) occurences from alf_access_control_list")
    String countTotalAcls();

    @Select({ "SELECT count(*) occurrences from alf_access_control_list aacl " +
            "LEFT OUTER JOIN alf_node an ON an.acl_id=aacl.id " +
            "WHERE aacl.id IS NULL" })
    String countTotalOrphanedAcls();

    @Select({ "select ap.name permission, count(*) permissionCount " +
            "from alf_access_control_entry aace, alf_permission ap " +
            "where ap.id = aace.permission_id " +
            "group by ap.name" })
    List<AccessControlBean> findAccessControlListEntries();

    @Select({ "SELECT acl_id aclid, count(*) numNodes " +
            "FROM alf_node " +
            "GROUP BY acl_id ORDER BY numNodes DESC LIMIT 10" })
    List<AccessControlBean> findACLNodeRepartition();

    @Select({ "SELECT * FROM (SELECT acl_id aclid, count(*) numNodes " +
            "FROM alf_node " +
            "GROUP BY acl_id ORDER BY numNodes DESC) " +
            "WHERE ROWNUM <= 10" })
    List<AccessControlBean> findACLNodeRepartitionOracle();

    @Select({ "SELECT TOP 10 acl_id aclid, count(*) numNodes " +
            "FROM alf_node " +
            "GROUP BY acl_id ORDER BY numNodes DESC" })
    List<AccessControlBean> findACLNodeRepartitionMSSql();

    @Select({ "SELECT md5(aa.authority) AS authorityHash, count(*) AS numAces " +
            "FROM alf_access_control_entry ace " +
            "JOIN alf_authority aa ON aa.id=ace.authority_id " +
            "GROUP BY authorityHash HAVING count(*) > 0" })
    List<AccessControlBean> findACEAuthorities();

    @Select({ "SELECT 'xxx' AS authorityHash, count(*) AS numAces " +
            "FROM alf_access_control_entry ace " +
            "JOIN alf_authority aa ON aa.id=ace.authority_id " +
            "GROUP BY aa.authority HAVING count(*) > 0" })
    List<AccessControlBean> findACEAuthoritiesOracle();

    @Select({ "SELECT CONVERT(VARCHAR(32), HashBytes('MD5', aa.authority), 2) AS authorityHash, count(*) AS numAces "
            +
            "FROM alf_access_control_entry ace " +
            "JOIN alf_authority aa ON aa.id=ace.authority_id " +
            "GROUP BY aa.authority HAVING count(*) > 0" })
    List<AccessControlBean> findACEAuthoritiesMSSql();

    @Select({ "SELECT CASE type WHEN 0 THEN 'OLD' " +
            "WHEN 1 THEN 'DEFINING' " +
            "WHEN 2 THEN 'SHARED' " +
            "WHEN 3 THEN  'FIXED' " +
            "WHEN 4 THEN 'GLOBAL' " +
            "WHEN 5 THEN 'LAYERED' " +
            "ELSE 'UNKNOWN' " +
            "END as aclType, inherits, count(*) as occurrences " +
            "FROM alf_access_control_list " +
            "GROUP BY type, inherits"
    })
    List<AccessControlBean> findAclTypesRepartition();

    @Select({ "SELECT acm.acl_id as aclid, count(*) as numAces FROM \n" +
            "alf_acl_member acm INNER JOIN alf_access_control_list \n" +
            "aacl ON aacl.id=acm.acl_id AND aacl.type=1 \n" +
            "GROUP BY acm.acl_id" })
    List<AccessControlBean> findAclsHeight();
}
