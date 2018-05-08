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

    @Select({"select ap.name permission, count(*) permissionCount " +
            "from alf_access_control_entry aace, alf_permission ap " +
            "where ap.id = aace.permission_id " +
            "group by ap.name"})
    List<AccessControlList> findAccessControlListEntries();

    @Select({"select inherits, count(*) occurrences " +
            "from alf_access_control_list " +
            "group by inherits"})
    List<AccessControlList> findAccessControlListInheritance();
}
