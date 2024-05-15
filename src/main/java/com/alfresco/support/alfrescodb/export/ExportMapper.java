package com.alfresco.support.alfrescodb.export;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.alfresco.support.alfrescodb.export.beans.AppliedPatchesBean;


@Mapper
public interface ExportMapper {
    @Select("SELECT id, applied_to_schema appliedToSchema, applied_on_date appliedOnDate, applied_to_server appliedToServer,\n " +
            "was_executed wasExecuted, succeeded, report\n " +
            "FROM alf_applied_patch")
    List<AppliedPatchesBean> findAppliedPatches();
}
