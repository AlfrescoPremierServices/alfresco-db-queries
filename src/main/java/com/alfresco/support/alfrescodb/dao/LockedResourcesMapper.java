package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.LockedResources;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LockedResourcesMapper {
    @Select("select al.id, al.lock_token as lockToken, start_time as startTime, expiry_time as expiryTime, " +
            "alr1.qname_localname as sharedResource, alr2.qname_localname as exclusiveResource, uri " +
            "from alf_lock al, alf_lock_resource alr1, alf_lock_resource alr2, alf_namespace an " +
            "where alr1.id = al.shared_resource_id " +
            "and alr2.id = al.excl_resource_id " +
            "and an.id = alr1.qname_ns_id ")
    List<LockedResources> findAll();
}