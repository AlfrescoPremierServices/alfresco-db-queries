package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.AccessControlList;
import com.alfresco.support.alfrescodb.model.ActivitiesFeed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccessControlListMapper {
    @Select("select count(*) occurences from alf_access_control_list")
    String findAccessControlList();

    @Select({"select ap.name permission, count(*) permissionCount \n" +
            "from alf_access_control_entry aace, alf_permission ap \n" +
            "where ap.id = aace.permission_id \n" +
            "group by ap.name"})
    List<AccessControlList> findAccessControlListEntries();

    @Select({"select inherits, count(*) occurrences \n" +
            "from alf_access_control_list \n" +
            "group by inherits"})
    List<AccessControlList> findAccessControlListInheritance();
}
