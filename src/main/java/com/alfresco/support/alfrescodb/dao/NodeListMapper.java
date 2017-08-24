package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.NodesList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NodeListMapper {

    @Select("SELECT (sum(content_size) / 1024 / 1024) diskSpace " +
            "FROM alf_content_url")
    List<NodesList> findNodesSize();

    @Select("SELECT mimetype_str mimeType, count(*) occurrences, (sum(content_size) / 1024 / 1024) diskSpace \n" +
            "FROM alf_content_data  content, alf_content_url  contentUrl, alf_mimetype  mime\n" +
            "WHERE content.content_mimetype_id = mime.id \n" +
            "AND contentUrl.id = content.content_url_id\n" +
            "GROUP BY mimetype_str")
    List<NodesList> findNodesSizeByMimeType();

    // Postgres queries
    @Select("SELECT ('{' || ns.uri || '}' || names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "GROUP BY nodes.type_qname_id, names.local_name, ns.uri ")
    List<NodesList> findNodesByContentTypePostgres();

    @Select("SELECT (stores.protocol || concat('://' || stores.identifier)) as store, count(*) as occurrences " +
            "FROM alf_node nodes, alf_store stores " +
            "WHERE stores.id=nodes.store_id " +
            "GROUP BY stores.protocol, stores.identifier ")
    List<NodesList> findNodesByStorePostgres();

    // MySQL queries
    @Select("SELECT concat('{', ns.uri, '}', names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "GROUP BY nodes.type_qname_id, names.local_name, ns.uri ")
    List<NodesList> findNodesByContentTypeMySQL();

    @Select("SELECT concat(stores.protocol, concat('://', stores.identifier)) as store, count(*) as occurrences " +
            "FROM alf_node nodes, alf_store stores " +
            "WHERE stores.id=nodes.store_id " +
            "GROUP BY stores.protocol, stores.identifier ")
    List<NodesList> findNodesByStoreMySQL();

    // Oracle queries
    @Select("SELECT ('{' || ns.uri || '}' || names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
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

    // MS SQL queries
    @Select("SELECT concat('{', ns.uri, '}', names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
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
}