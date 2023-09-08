package com.alfresco.support.alfrescodb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.alfresco.support.alfrescodb.model.AccessControlList;

@Mapper
public interface AccessControlListMapper {
    @Select("select count(*) occurences from alf_access_control_list")
    String findAccessControlList();

    @Select({"select ap.name permission, count(*) permissionCount " +
            "from alf_access_control_entry aace, alf_permission ap " +
            "where ap.id = aace.permission_id " +
            "group by ap.name"})
    List<AccessControlList> findAccessControlListEntries();

    @Select({"SELECT acl_id aclid, count(*) numNodes " +
            "FROM alf_node " +
            "GROUP BY acl_id ORDER BY numNodes DESC LIMIT 10"})
    List<AccessControlList> findACLNodeRepartition();

    @Select({"SELECT * FROM (SELECT acl_id aclid, count(*) numNodes " +
            "FROM alf_node " +
            "GROUP BY acl_id ORDER BY numNodes DESC) " +
            "WHERE ROWNUM <= 10"})
    List<AccessControlList> findACLNodeRepartitionOracle();

    @Select({"SELECT TOP 10 acl_id aclid, count(*) numNodes " +
            "FROM alf_node " +
            "GROUP BY acl_id ORDER BY numNodes DESC"})
    List<AccessControlList> findACLNodeRepartitionMSSql();

    @Select({"SELECT md5(aa.authority) AS authorityHash, count(*) AS numAces " +
            "FROM alf_access_control_entry ace " +
            "JOIN alf_authority aa ON aa.id=ace.authority_id " +
            "GROUP BY authorityHash HAVING count(*) > 0"})
    List<AccessControlList> findACEAuthorities();

    @Select({"SELECT 'xxx' AS authorityHash, count(*) AS numAces " +
            "FROM alf_access_control_entry ace " +
            "JOIN alf_authority aa ON aa.id=ace.authority_id " +
            "GROUP BY aa.authority HAVING count(*) > 0"})
    List<AccessControlList> findACEAuthoritiesOracle();

    @Select({"SELECT CONVERT(VARCHAR(32), HashBytes('MD5', aa.authority), 2) AS authorityHash, count(*) AS numAces " +
            "FROM alf_access_control_entry ace " +
            "JOIN alf_authority aa ON aa.id=ace.authority_id " +
            "GROUP BY aa.authority HAVING count(*) > 0"})
    List<AccessControlList> findACEAuthoritiesMSSql();

    @Select({"SELECT count(*) occurrences from alf_access_control_list aacl "  +
            "LEFT OUTER JOIN alf_node an ON an.acl_id=aacl.id " +
            "WHERE aacl.id IS NULL"})
    String findOrphanedAcls();

   @Select({"SELECT CASE type WHEN 0 THEN 'OLD' " +
       "WHEN 1 THEN 'DEFINING' " +
       "WHEN 2 THEN 'SHARED' " +
       "WHEN 3 THEN  'FIXED' " +
       "WHEN 4 THEN 'GLOBAL' " +
       "WHEN 5 THEN 'LAYERED' " +
        "ELSE 'UNKNOWN' " +
        "END as aclType, inherits, count(*) as occurrences " +
    "FROM alf_access_control_list " +
    "GROUP BY type, inherits"
    })
    List<AccessControlList> findAclTypesRepartition();

    @Select({"SELECT acm.acl_id as aclid, count(*) as numAces FROM \n" +
             "alf_acl_member acm INNER JOIN alf_access_control_list \n" +
             "aacl ON aacl.id=acm.acl_id AND aacl.type=1 \n" +
             "GROUP BY acm.acl_id"})
    List<AccessControlList> findAclsHeight();

}
