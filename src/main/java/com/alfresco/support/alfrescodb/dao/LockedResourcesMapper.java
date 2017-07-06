package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.LockedResources;
import com.alfresco.support.alfrescodb.model.NodesList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LockedResourcesMapper {
    @Select("select al.id, al.lock_token as lockToken, start_time as startTime, expiry_time as expiryTime, " +
            "alr1.qname_localname as sharedResource, alr2.qname_localname as exclusiveResource, uri " +
            "from alf_lock as al, alf_lock_resource as alr1, alf_lock_resource as alr2, alf_namespace an " +
            "where alr1.id = al.shared_resource_id " +
            "and alr2.id = al.excl_resource_id " +
            "and an.id = alr1.qname_ns_id " +
            "order by al.id") //SQL
    List<LockedResources> findAll();
}