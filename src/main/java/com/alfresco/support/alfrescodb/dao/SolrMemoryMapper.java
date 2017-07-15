package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.ArchivedNodes;
import com.alfresco.support.alfrescodb.model.SolrMemory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SolrMemoryMapper {
    @Select("select * from\n" +
            "(select count( * ) alfrescoNodes from alf_node where store_id = (select id from alf_store where protocol = 'workspace' and identifier = 'SpacesStore')) as N1 ,\n" +
            "(select count( * ) archiveNodes from alf_node where store_id = (select id from alf_store where protocol = 'archive' and identifier = 'SpacesStore')) as N2 ,\n" +
            "(select count( * ) transactions from alf_transaction ) as T,\n" +
            "(select count( * ) acls from alf_access_control_list ) as A,\n" +
            "(select count( * ) aclTransactions from alf_acl_change_set) as X")
    List<SolrMemory> solrMemory();
}