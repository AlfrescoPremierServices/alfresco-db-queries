package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.AccessControlList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccessControlListMapper {
    @Select("select count(*) occurences from alf_access_control_list")
    String findAccessControlList();
}
