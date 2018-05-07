package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.AppliedPatches;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AppliedPatchesMapper {
    @Select("SELECT id, applied_to_schema appliedToSchema, applied_on_date appliedOnDate, applied_to_server appliedToServer,\n " +
            "was_executed wasExecuted, succeeded, report\n " +
            "FROM alfresco.alf_applied_patch")
    List<AppliedPatches> findAppliedPatches();
}
