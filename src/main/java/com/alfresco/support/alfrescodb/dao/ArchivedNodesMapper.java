package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.ActivitiesFeed;
import com.alfresco.support.alfrescodb.model.ArchivedNodes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArchivedNodesMapper {
    @Select("select count(*) as nodes " +
            "from alf_node " +
            "where store_id in (select id from alf_store where protocol = 'archive' and identifier = 'SpacesStore')") //SQL
    List<ArchivedNodes> findArchivedNodes();

    @Select("select audit_modifier as auditModifier, count(*) as nodes " +
            "from alf_node " +
            "where store_id in (select id from alf_store where protocol = 'archive' and identifier = 'SpacesStore') " +
            "group by audit_modifier ") //SQL
    List<ArchivedNodes> findArchivedNodesByUser();
}