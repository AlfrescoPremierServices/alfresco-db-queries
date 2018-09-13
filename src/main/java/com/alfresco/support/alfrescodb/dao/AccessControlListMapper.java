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

    @Select({"SELECT acl_id AS aclId, count(*) AS numNodes \n" +
            "FROM alf_node \n" +
            "GROUP BY acl_id ORDER BY numNodes DESC LIMIT 100"})
    List<AccessControlList> findAclsRepartition();

    @Select({"SELECT * from alf_access_control_list aacl \n" +
            "LEFT OUTER JOIN alf_node an ON an.acl_id=aacl.id \n" +
            "WHERE aacl.id IS NULL"})
    List<AccessControlList> findOrphanedAcls();

    @Select({"SELECT CASE type WHEN 0 THEN 'OLD' \n" +
            "WHEN 1 THEN 'DEFINING' \n" +
            "WHEN 2 THEN 'SHARED' \n" +
            "WHEN 3 THEN  'FIXED' \n" +
            "WHEN 4 THEN 'GLOBAL' \n" +
            "WHEN 5 THEN 'LAYERED' \n" +
        "ELSE 'UNKNOWN' \n" +
        "END as aclType, count(*) as typeCount \n" +
    "FROM alf_access_control_list \n" +
    "GROUP BY type"
    })
    List<AccessControlList> findAclTypesRepartition();

    @Select({"SELECT acm.acl_id as aclId, count(*) as numAce FROM \n" +
             "alf_acl_member acm INNER JOIN alf_access_control_list \n" +
             "aacl ON aacl.id=acm.acl_id AND aacl.type=1 \n" +
             "GROUP BY acm.acl_id"})
    List<AccessControlList> findAclsHeight();

    @Select({"SELECT aa.authority as authorities, count(*) as numAcls\n" +
            "FROM alf_access_control_entry ace \n" +
            "JOIN alf_authority aa ON aa.id=ace.authority_id \n" +
            "GROUP BY ace.authority_id,aa.authority \n" +
            "ORDER BY count(*)"})
    List<AccessControlList> findAuthoritiesAcls();
}
