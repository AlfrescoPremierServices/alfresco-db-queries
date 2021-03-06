package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.LargeFolder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LargeFolderMapper {
    @Select("SELECT count(*) as occurrences, (stores.protocol || '://' || stores.identifier || '/' || nodes.uuid) as nodeRef, " +
            "props.string_value as nodeName, qname.local_name as localName " +
            "FROM alf_node as nodes, alf_store as stores, alf_child_assoc as children, alf_node_properties as props, alf_qname as qname " +
            "WHERE children.parent_node_id=nodes.id and " +
            "stores.id=nodes.store_id and " +
            "props.node_id = nodes.id and " +
            "props.qname_id IN (SELECT id FROM alf_qname WHERE local_name = 'name') and " +
            "qname.id = nodes.type_qname_id " +
            "GROUP BY nodeRef, nodeName, local_name " +
            "HAVING count(*) > #{size} ")
    List<LargeFolder> findBySize(@Param("size") int size);

    // Oracle queries
    @Select("SELECT count(*) as occurrences, (stores.protocol || '://' || stores.identifier || '/' || nodes.uuid) as nodeRef, " +
            "props.string_value as nodeName, qname.local_name as localName " +
            "FROM alf_node nodes, alf_store stores, alf_child_assoc children, alf_node_properties props, alf_qname qname " +
            "WHERE children.parent_node_id=nodes.id and " +
            "stores.id=nodes.store_id and " +
            "props.node_id = nodes.id and " +
            "props.qname_id IN (SELECT id FROM alf_qname WHERE local_name = 'name') and " +
            "qname.id = nodes.type_qname_id " +
            "GROUP BY (stores.protocol || '://' || stores.identifier || '/' || nodes.uuid), props.string_value, qname.local_name " +
            "HAVING count(*) > #{size} ")
        List<LargeFolder> findBySizeOracle(@Param("size") int size);

    // MS SQL queries
    @Select("SELECT count(*) as occurrences, concat (stores.protocol, '://', stores.identifier, '/', nodes.uuid) as nodeRef, " +
            "props.string_value as nodeName, qname.local_name as localName " +
            "FROM alf_node as nodes, alf_store as stores, alf_child_assoc as children, alf_node_properties as props, alf_qname as qname " +
            "WHERE children.parent_node_id=nodes.id and stores.id=nodes.store_id and props.node_id = nodes.id and " +
            "props.qname_id IN (SELECT id FROM alf_qname WHERE local_name = 'name') and qname.id = nodes.type_qname_id " +
            "GROUP BY stores.protocol, stores.identifier, nodes.uuid, string_value, local_name HAVING count(*) > #{size} ")
    List<LargeFolder> findBySizeMSSql(@Param("size") int size);
}