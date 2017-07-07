package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AuthorityMapper {
    @Select("select count(*) as authoritiesCount from alf_authority " +
            "where authority not like 'GROUP_%'")
    List<Authority> findUsers();

    @Select("select count(*) as authoritiesCount from alf_authority " +
            "where authority like 'GROUP_%'")
    List<Authority> findGroups();
}