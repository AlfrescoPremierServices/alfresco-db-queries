package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.AccessControlList;
import com.alfresco.support.alfrescodb.model.ContentModelProperties;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContentModelPropertiesMapper {
    @Select({"select an.uri model, local_name property " +
            "from alf_qname aq, alf_namespace an " +
            "where an.id = aq.ns_id " +
            "order by an.uri, aq.local_name"})
    List<ContentModelProperties> findContentModelProperties();
}
