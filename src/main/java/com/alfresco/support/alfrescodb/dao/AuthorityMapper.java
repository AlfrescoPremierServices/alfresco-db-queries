package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AuthorityMapper {
    @Select("select count(*) as authoritiesCount from alf_node_properties \n" +
            "where node_id in (select id from alf_node where type_qname_id in (select id from alf_qname where local_name = 'person')) \n" +
            "and qname_id in (select id from alf_qname where local_name ='userName')" )
    List<Authority> findUsers();

    @Select("select count(*) as authoritiesCount from alf_auth_status where authorized = true")
    List<Authority> findAuthorizedUsers();

    @Select("select count(*) as authoritiesCount from alf_auth_status where authorized = 1")
    List<Authority> findAuthorizedUsersMSSql();

    @Select("select count(*) as authoritiesCount from alf_node_properties where qname_id in (select id from alf_qname where local_name = 'authorityName')")
    List<Authority> findGroups();
}