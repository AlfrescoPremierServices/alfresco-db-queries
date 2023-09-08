package com.alfresco.support.alfrescodb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.alfresco.support.alfrescodb.model.ContentModelProperties;

@Mapper
public interface ContentModelPropertiesMapper {
    @Select({"select an.uri model, local_name property " +
            "from alf_qname aq, alf_namespace an " +
            "where an.id = aq.ns_id " +
            "order by an.uri, aq.local_name"})
    List<ContentModelProperties> findContentModelProperties();
}
