package com.alfresco.support.alfrescodb.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.alfresco.support.alfrescodb.dao.*;
import com.alfresco.support.alfrescodb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    @Value("${largeFolderSize}")
    private Integer largeFolderSize;

    @Value("${largeTransactionSize}")
    private Integer largeTransactionSize;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    ResultSet resultSet;

    @RequestMapping("/")
    public String index(String name, Model model) {
        addAdditionalParamsToModel(model);
        return "index";
    }


    @Autowired
    private WorkflowMapper workflowMapper;

    @RequestMapping("/workflows")
    public String workflows(Model model) {

    	// Count workflows by process def and task name
        List <Workflow> listWorkflows = workflowMapper.findAll();
        model.addAttribute("listWorkflows", listWorkflows);

    	// Count open processes
        List < Workflow > listOpenWorkflows = workflowMapper.openWorkflows();
        model.addAttribute("listOpenWorkflows", listOpenWorkflows);
        
    	// Count closed processes
        List < Workflow > listClosedWorkflows = workflowMapper.closedWorkflows();
        model.addAttribute("listClosedWorkflows", listClosedWorkflows);
        
    	// Count open taks
        List < Workflow > listOpenTasks = workflowMapper.openTasks();
        model.addAttribute("listOpenTasks", listOpenTasks);
        
    	// Count closed tasks
        List < Workflow > listClosedTasks = workflowMapper.closedTasks();
        model.addAttribute("listClosedTasks", listClosedTasks);
        
        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private DbSizeMapper dbSizeMapper;

    @RequestMapping("/dbSize")
    public String dbSize(Model model) {
        List <RelationInfo> listRelationInfos = dbSizeMapper.findTablesInfo();
        String dbSize = dbSizeMapper.findDbSize();

        model.addAttribute("listRelationInfos", listRelationInfos);
        model.addAttribute("dbSize", dbSize);

        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private LargeFolderMapper largeFolderMapper;

    @RequestMapping("/largeFolders")
    public String largeFolders(@RequestParam(value = "size", required = true) String size, Model model) {
        List <LargeFolder> listLargeFolders = largeFolderMapper.findBySize(largeFolderSize);

        model.addAttribute("largeFolderSize", largeFolderSize);
        model.addAttribute("listLargeFolders", listLargeFolders);
        model.addAttribute("size", size);
        
        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private LargeTransactionMapper largeTransactionMapper;

    @RequestMapping("/largeTransactions")
    public String largeTransactions(@RequestParam(value = "size", required = true) String size, Model model) {
        List <LargeTransaction> listLargeTransactions = largeTransactionMapper.findBySize(largeTransactionSize);

        model.addAttribute("largeTransactionSize", largeTransactionSize);
        model.addAttribute("listLargeTransactions", listLargeTransactions);
        model.addAttribute("size", size);

        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private ActivitiesFeedMapper activitiesFeedMapper;

    @RequestMapping("/activitiesFeed")
    public String activitiesFeed(Model model) {

        // Activities by activity type
        List <ActivitiesFeed> listActivitiesFeed = activitiesFeedMapper.findActivitiesByActivityType();

        model.addAttribute("listActivitiesFeedByActivityType", listActivitiesFeed);

        // Activities by user
        listActivitiesFeed = activitiesFeedMapper.findActivitiesByUser();

        model.addAttribute("listActivitiesFeedByUser", listActivitiesFeed);

        // Activities by application interface
        listActivitiesFeed = activitiesFeedMapper.findActivitiesByApplicationInterface();

        model.addAttribute("listActivitiesFeedByAppTool", listActivitiesFeed);
          
        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private ArchivedNodesMapper archivedNodesMapper;

    @RequestMapping("/archivedNodes")
    public String archivedNodes(Model model) {

        // Archived nodes
        List <ArchivedNodes> listArchivedNodes = archivedNodesMapper.findArchivedNodes();
        
        model.addAttribute("listArchivedNodes", listArchivedNodes);

        // Archived nodes by user
        List <ArchivedNodes> listArchivedNodesByUser = archivedNodesMapper.findArchivedNodesByUser();

        model.addAttribute("listArchivedNodesByUser", listArchivedNodes);
        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private NodeListMapper nodeListMapper;

    @RequestMapping("/listNodesByMimeType")
    public void nodesByMimeType(Model model) {

        // Nodes disk space
        List < NodesList > diskSpace = nodeListMapper.findNodesSize();
        
        model.addAttribute("totalDiskSpace", diskSpace);
        
        // Nodes by mime type
        List < NodesList > listNodesByMimeType = nodeListMapper.findNodesSizeByMimeType();

        model.addAttribute("listNodesByMimeType", listNodesByMimeType);

        addAdditionalParamsToModel(model);
    }
    
    @RequestMapping("/listNodesByType")
    public String nodesByType(Model model) {
   
        // Nodes by type
        List < NodesList > listNodesByType = nodeListMapper.findNodesSizeByContentType();

        model.addAttribute("listNodesByType", listNodesByType);

        addAdditionalParamsToModel(model);

        return null;
    }
    
    @RequestMapping("/listNodesByStore")
    public String nodesByStore(Model model) {
    	
        // Nodes by store
        List < NodesList > listNodesByStore = nodeListMapper.findNodesByStore();
        
        model.addAttribute("listNodesByStore", listNodesByStore);

        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private LockedResourcesMapper lockedResourcesMapper;

    @RequestMapping("/lockedResources")
    public String lockedResources(Model model) {
        			 
        List < LockedResources > listLockedResources = lockedResourcesMapper.findAll();
        
        model.addAttribute("listLockedResources", listLockedResources);
        
        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private AuthorityMapper authorityMapper;

    @RequestMapping("/authorities")
    public String authorities(Model model) {

        //Count users
       List <Authority> listUsers = authorityMapper.findUsers();

        model.addAttribute("listUsers", listUsers);

        //Count groups
        List < Authority > listGroups = authorityMapper.findGroups();

        model.addAttribute("listGroups", listGroups);

        addAdditionalParamsToModel(model);

        return null;
    }

    private void addAdditionalParamsToModel(Model model) {
        // Need this entry for large folders url
        model.addAttribute("largeFolderSize", largeFolderSize);
        // Need this entry for large transactions url
        model.addAttribute("largeTransactionSize", largeTransactionSize);   		
	}
}
