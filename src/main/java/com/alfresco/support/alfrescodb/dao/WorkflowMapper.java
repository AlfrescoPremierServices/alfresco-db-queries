package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.Workflow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WorkflowMapper {
    @Select("select count(*) as occurrences, proc_def_id_ as procDefId, name_ as name " +
            "FROM act_hi_taskinst " +
            "GROUP BY proc_def_id_, name_ " +
            "ORDER BY occurrences desc") //SQL
    List<Workflow> findAll();

    @Select("select count(proc_def_id_) as occurrences, proc_def_id_  as procDefId " +
            "FROM act_hi_procinst " +
            "WHERE end_time_ is null " +
            "GROUP BY proc_def_id_ " +
            "ORDER BY occurrences desc") //SQL
    List<Workflow> openWorkflows();

    @Select("select count(proc_def_id_) as occurrences, proc_def_id_  as procDefId " +
            "FROM act_hi_procinst " +
            "WHERE end_time_ is not null " +
            "GROUP BY proc_def_id_ " +
            "ORDER BY occurrences desc") //SQL
    List<Workflow> closedWorkflows();

    @Select("select count(proc_def_id_) as occurrences, proc_def_id_  as procDefId, name_ as name " +
            "FROM act_hi_taskinst " +
            "WHERE end_time_ is null " +
            "GROUP BY proc_def_id_, name_ " +
            "ORDER BY occurrences desc") //SQL
    List<Workflow> openTasks();

    @Select("select count(proc_def_id_) as occurrences, proc_def_id_  as procDefId, name_ as name " +
            "FROM act_hi_taskinst " +
            "WHERE end_time_ is not null " +
            "GROUP BY proc_def_id_, name_ " +
            "ORDER BY occurrences desc") //SQL
    List<Workflow> closedTasks();
}