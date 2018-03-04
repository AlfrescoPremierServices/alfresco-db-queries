package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.AccessControlList;
import com.alfresco.support.alfrescodb.model.ContentModelProperties;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContentModelPropertiesMapper {
    @Select({"select an.uri model, local_name property \n" +
            "from alf_qname aq, alf_namespace an \n" +
            "where an.id = aq.ns_id \n" +
            "order by an.uri, aq.local_name"})
    List<ContentModelProperties> findContentModelProperties();
}
