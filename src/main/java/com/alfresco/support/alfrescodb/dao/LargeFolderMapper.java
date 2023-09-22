package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.LargeFolder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LargeFolderMapper {
    @Select("select children.occurrences, (stores.protocol || '://' || stores.identifier || '/' || nodes.uuid) as nodeRef, props.string_value as nodeName, qname.local_name as localName  " +
            "from alf_node as nodes " +
            "join alf_store as stores on stores.id = nodes.store_id " +
            "join alf_node_properties as props on props.node_id = nodes.id " +
            "join alf_qname as qname on qname.id = nodes.type_qname_id " +
            "join (select parent_node_id, count(*) as occurrences from alf_child_assoc group by parent_node_id HAVING count(*) > #{size} ) children on nodes.id = children.parent_node_id ")
    List<LargeFolder> findBySize(@Param("size") int size);
}