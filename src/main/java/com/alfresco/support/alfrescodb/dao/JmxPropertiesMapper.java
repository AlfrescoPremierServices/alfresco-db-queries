package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.JmxProperties;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JmxPropertiesMapper {
    @Select("SELECT APSVk.string_value as propertyName,APSVv.string_value as propertyValue \n" +
            "FROM alf_prop_link APL \n" +
            "JOIN alf_prop_value APVv ON APL.value_prop_id=APVv.id \n" +
            "JOIN alf_prop_value APVk ON APL.key_prop_id=APVk.id \n" +
            "JOIN alf_prop_string_value APSVk ON APVk.long_value=APSVk.id \n" +
            "JOIN alf_prop_string_value APSVv ON APVv.long_value=APSVv.id \n" +
            "WHERE APL.key_prop_id <> APL.value_prop_id \n" +
            "AND APL.root_prop_id IN (SELECT prop1_id FROM alf_prop_unique_ctx)")
    List<JmxProperties> findJmxProperties();
}