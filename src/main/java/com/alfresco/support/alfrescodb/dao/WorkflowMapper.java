package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.Workflow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WorkflowMapper {
    @Select("select count(*) as occurrences, proc_def_id_ as procDefId, name_ as name " +
            "FROM ACT_HI_TASKINST " +
            "GROUP BY proc_def_id_, name_ " +
            "ORDER BY occurrences desc")
    List<Workflow> findAll();

    @Select("select count(proc_def_id_) as occurrences, proc_def_id_  as procDefId " +
            "FROM ACT_HI_PROCINST " +
            "WHERE end_time_ is null " +
            "GROUP BY proc_def_id_ " +
            "ORDER BY occurrences desc")
    List<Workflow> openWorkflows();

    @Select("select count(proc_def_id_) as occurrences, proc_def_id_  as procDefId " +
            "FROM ACT_HI_PROCINST " +
            "WHERE end_time_ is not null " +
            "GROUP BY proc_def_id_ " +
            "ORDER BY occurrences desc")
    List<Workflow> closedWorkflows();

    @Select("select count(proc_def_id_) as occurrences, proc_def_id_  as procDefId, name_ as name " +
            "FROM ACT_HI_TASKINST " +
            "WHERE end_time_ is null " +
            "GROUP BY proc_def_id_, name_ " +
            "ORDER BY occurrences desc")
    List<Workflow> openTasks();

    @Select("select count(proc_def_id_) as occurrences, proc_def_id_  as procDefId, name_ as name " +
            "FROM ACT_HI_TASKINST " +
            "WHERE end_time_ is not null " +
            "GROUP BY proc_def_id_, name_ " +
            "ORDER BY occurrences desc")
    List<Workflow> closedTasks();
}