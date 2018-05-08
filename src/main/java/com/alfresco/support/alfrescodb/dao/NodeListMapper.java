package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.NodesList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NodeListMapper {

    @Select("SELECT (sum(content_size) / 1024 / 1024) diskSpace " +
            "FROM alf_content_data  content, alf_content_url  contentUrl, alf_mimetype  mime, alf_node nodes, alf_node_properties nodes_props " +
            "WHERE content.content_mimetype_id = mime.id " +
            "AND contentUrl.id = content.content_url_id " +
            "AND nodes.id = nodes_props.node_id AND nodes_props.long_value = content.id " +
            "AND nodes_props.qname_id in (select id from alf_qname where local_name = 'content') " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore')")
    List<NodesList> findNodesSize();

    @Select("SELECT mimetype_str mimeType, count(*) occurrences, (sum(content_size) / 1024 / 1024) diskSpace " +
            "FROM alf_content_data  content, alf_content_url  contentUrl, alf_mimetype  mime, alf_node nodes, alf_node_properties nodes_props " +
            "WHERE content.content_mimetype_id = mime.id " +
            "AND contentUrl.id = content.content_url_id " +
            "AND nodes.id = nodes_props.node_id AND nodes_props.long_value = content.id " +
            "AND nodes_props.qname_id in (select id from alf_qname where local_name = 'content') " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY mimetype_str")
    List<NodesList> findNodesSizeByMimeType();

    // Postgres queries
    @Select("SELECT ('{' || ns.uri || '}' || names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY nodes.type_qname_id, names.local_name, ns.uri ")
    List<NodesList> findNodesByContentTypePostgres();

    @Select("SELECT (stores.protocol || concat('://' || stores.identifier)) as store, count(*) as occurrences " +
            "FROM alf_node nodes, alf_store stores " +
            "WHERE stores.id=nodes.store_id " +
            "GROUP BY stores.protocol, stores.identifier ")
    List<NodesList> findNodesByStorePostgres();


    @Select("SELECT substring(nodes.audit_created, 0, 8) as createDate, ('{' || ns.uri || '}' || names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY substring(nodes.audit_created, 0, 8), nodes.type_qname_id, names.local_name, ns.uri")
    List<NodesList> findNodesByContentTypeAndMonthPostgres();

    // MySQL queries
    @Select("SELECT concat('{', ns.uri, '}', names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY nodes.type_qname_id, names.local_name, ns.uri ")
    List<NodesList> findNodesByContentTypeMySQL();

    @Select("SELECT concat(stores.protocol, concat('://', stores.identifier)) as store, count(*) as occurrences " +
            "FROM alf_node nodes, alf_store stores " +
            "WHERE stores.id=nodes.store_id " +
            "GROUP BY stores.protocol, stores.identifier ")
    List<NodesList> findNodesByStoreMySQL();

    @Select("SELECT substring(nodes.audit_created, 1, 7) as createDate, concat('{', ns.uri, '}', names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY substring(nodes.audit_created, 1, 7), nodes.type_qname_id, names.local_name, ns.uri")
    List<NodesList> findNodesByContentTypeAndMonthMySQL();

    // Oracle queries
    @Select("SELECT ('{' || ns.uri || '}' || names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY nodes.type_qname_id, names.local_name, ns.uri ")
    List<NodesList> findNodesByContentTypeOracle();

    @Select("SELECT (stores.protocol || ('://' || stores.identifier)) as store, count(*) as occurrences " +
            "FROM alf_node nodes, alf_store stores " +
            "WHERE stores.id=nodes.store_id " +
            "GROUP BY stores.protocol, stores.identifier")
    List<NodesList> findNodesByStoreOracle();

    @Select("SELECT (sum(content_size) / 1024 / 1024) diskSpace " +
            "FROM alf_content_url")
    List<NodesList> findNodesSizeOracle();

    @Select("SELECT substr(nodes.audit_created, 0, 7) as createDate, ('{' || ns.uri || '}' || names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY substr(nodes.audit_created, 0, 7), nodes.type_qname_id, names.local_name, ns.uri ")
    List<NodesList> findNodesByContentTypeAndMonthOracle();

    // MS SQL queries
    @Select("SELECT concat('{', ns.uri, '}', names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY nodes.type_qname_id, names.local_name, ns.uri ")
    List<NodesList> findNodesByContentTypeMSSql();

    @Select("SELECT concat(stores.protocol,  concat('://',  stores.identifier)) as store, count(*) as occurrences " +
            "FROM alf_node nodes, alf_store stores " +
            "WHERE stores.id=nodes.store_id " +
            "GROUP BY stores.protocol, stores.identifier ")
    List<NodesList> findNodesByStoreMSSql();

    @Select("SELECT (sum(content_size) / 1024 / 1024) diskSpace " +
            "FROM alf_content_url")
    List<NodesList> findNodesSizeMSSql();

    @Select("SELECT substring(nodes.audit_created, 0, 8) as createDate, concat('{', ns.uri, '}', names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "AND nodes.store_id in (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore') " +
            "GROUP BY substring(nodes.audit_created, 0, 8), nodes.type_qname_id, names.local_name, ns.uri ")
    List<NodesList> findNodesByContentTypeAndMonthMSSql();
}
