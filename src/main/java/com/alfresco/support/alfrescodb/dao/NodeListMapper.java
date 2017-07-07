package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.NodesList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NodeListMapper {
    @Select("SELECT sum(content_size) diskSpace " +
            "FROM (SELECT DISTINCT content_url_id, mimetype_str, content_size " +
            "FROM alf_content_data  content, alf_content_url  contentUrl, alf_mimetype  mime " +
            "WHERE content.content_mimetype_id = mime.id and " +
            "contentUrl.id = content.content_url_id) AS dct") //SQL
    List<NodesList> findNodesSize();

    @Select("SELECT mimetype_str as mimeType, count(*) occurrences, sum(content_size) diskSpace " +
            "FROM (SELECT DISTINCT content_url_id,mimetype_str,content_size " +
            "FROM alf_content_data  content, alf_content_url  contentUrl, alf_mimetype  mime " +
            "WHERE content.content_mimetype_id = mime.id and " +
            "contentUrl.id = content.content_url_id) AS dct " +
            "GROUP BY mimetype " +
            "ORDER BY occurrences DESC") //SQL
    List<NodesList> findNodesSizeByMimeType();

    @Select("SELECT ('{' || ns.uri || '}' || names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "GROUP BY nodes.type_qname_id, names.local_name, ns.uri " +
            "ORDER BY occurrences DESC") //SQL
    List<NodesList> findNodesSizeByContentTypePostgres();

    @Select("SELECT (stores.protocol || concat('://' || stores.identifier)) as store, count(*) as occurrences " +
            "FROM alf_node nodes, alf_store stores " +
            "WHERE stores.id=nodes.store_id " +
            "GROUP BY stores.protocol, stores.identifier ") //SQL
    List<NodesList> findNodesByStorePostgres();

    @Select("SELECT concat('{', ns.uri, '}', names.local_name) as nodeType, count(*)  as occurrences " +
            "FROM alf_node nodes " +
            "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
            "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
            "WHERE nodes.type_qname_id=names.id " +
            "GROUP BY nodes.type_qname_id, names.local_name, ns.uri " +
            "ORDER BY occurrences DESC") //SQL
    List<NodesList> findNodesSizeByContentTypeMySQL();

    @Select("SELECT concat(stores.protocol, concat('://', stores.identifier)) as store, count(*) as occurrences " +
            "FROM alf_node nodes, alf_store stores " +
            "WHERE stores.id=nodes.store_id " +
            "GROUP BY stores.protocol, stores.identifier ") //SQL
    List<NodesList> findNodesByStoreMySQL();
}