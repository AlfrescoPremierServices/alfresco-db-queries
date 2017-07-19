package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.ArchivedNodes;
import com.alfresco.support.alfrescodb.model.SolrMemory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SolrMemoryMapper {
    @Select("select * from\n" +
            "(select count( * ) alfrescoNodes from alf_node where store_id = (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore')) N1 ,\n" +
            "(select count( * ) archiveNodes from alf_node where store_id = (select id from alf_store where protocol = 'archive' and identifier = 'SpacesStore')) N2 ,\n" +
            "(select count( * ) transactions from alf_transaction ) T,\n" +
            "(select count( * ) acls from alf_access_control_list ) A,\n" +
            "(select count( * ) aclTransactions from alf_acl_change_set) X")
    List<SolrMemory> solrMemory();
}