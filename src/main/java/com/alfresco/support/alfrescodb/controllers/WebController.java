package com.alfresco.support.alfrescodb.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.alfresco.support.alfrescodb.dao.*;
import com.alfresco.support.alfrescodb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class WebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SqlMapperController sqlMapper;

    @Value("${largeFolderSize}")
    private Integer largeFolderSize;

    @Value("${largeTransactionSize}")
    private Integer largeTransactionSize;

    @Value("${reportFile}")
    private String reportFile;

    @Value("${spring.datasource.platform}")
    private String dbType;

    // Alfresco Solr caches
    @Value("${alfresco.solr.filterCache.size}")
    private Long alfrescoSolrFilterCacheSize;

    @Value("${alfresco.solr.queryResultCache.size}")
    private Long alfrescoSolrQueryResultCacheSize;

    @Value("${alfresco.solr.authorityCache.size}")
    private Long alfrescoSolrAuthorityCacheSize;

    @Value("${alfresco.solr.pathCache.size}")
    private Long alfrescoSolrPathCacheSize;

    // Archive Solr caches
    @Value("${archive.solr.filterCache.size}")
    private Long archiveSolrFilterCacheSize;

    @Value("${archive.solr.queryResultCache.size}")
    private Long archiveSolrQueryResultCacheSize;

    @Value("${archive.solr.authorityCache.size}")
    private Long archiveSolrAuthorityCacheSize;

    @Value("${archive.solr.pathCache.size}")
    private Long archiveSolrPathCacheSize;

    @Value("${alf_auth_status}")
    private Boolean alfAuthStatus;

    @RequestMapping("/")
    public String index(String name, Model model) {
        addAdditionalParamsToModel(model);
        return "index";
    }

    @Autowired
    private WorkflowMapper workflowMapper;

    @RequestMapping("/report")
    public void report(Model model) {

        List < RelationInfo > listRelationInfos;
        List < LargeFolder > listLargeFolders;
        List < LargeTransaction > listLargeTransactions;
        List < AccessControlList > listAccessControlListEntries;
        List < AccessControlList > listAccessControlListInheritance;
        List < ContentModelProperties > listContentModelProperties;
        List < ActivitiesFeed > listActivitiesFeed;
        List < ArchivedNodes > listArchivedNodes;
        List < NodesList > listNodesByMimeType;
        List < NodesList > listNodesByType;
        List < NodesList > listNodesByTypeAndMonth;
        List < NodesList > listNodesByStore;
        List < LockedResources > listLockedResources;
        List < Authority > listUsers;
        List < Authority > listAuthorizedUsers;
        List < Authority > listGroups;
        List < Workflow > listWorkflows;
        List < JmxProperties > jmxProperties;

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(reportFile));

            addAdditionalParamsToModel(model);

            // Database Size
            listRelationInfos = sqlMapper.findTablesInfo();
            out.write("Database Tables Information");

            if (dbType.equalsIgnoreCase("mysql") || dbType.equalsIgnoreCase("postgres")) {
                out.write("\nTable Name, Total Size, Row Estimate, Table Size, Index Size");

                for (int i = 0; i < listRelationInfos.size(); i++) {
                    out.write(listRelationInfos.get(i).printDbInfo());
                }

                String dbSize = sqlMapper.findDbSize();
                out.write("\n\nDatabase Size");
                out.write("\nSize");
                out.write(dbSize);
                model.addAttribute("dbSize", dbSize);
            } else if (dbType.equalsIgnoreCase("oracle")){
                List<OracleRelationInfo> OracleListRelationInfos = sqlMapper.findTablesInfo();

                out.write("\nTables Size");
                out.write("\nTable Name, Size MB");
                for (int i = 0; i < OracleListRelationInfos.size(); i++) {
                    out.write(OracleListRelationInfos.get(i).printTableInfo());
                }

                List<OracleRelationInfo> OracleListIndexesInfos = dbSizeMapper.findIndexesInfoOracle();

                out.write("\n\nIndexes Size");
                out.write("\nTable Name, Index Name, Index Size MB");
                for (int i = 0; i < OracleListIndexesInfos.size(); i++) {
                    out.write(OracleListIndexesInfos.get(i).printIndexInfo());
                }
            } else if (dbType.equalsIgnoreCase("microsoft")) {
                List<MSSqlRelationInfo> MSSqlListRelationInfos = sqlMapper.findTablesInfo();

                out.write("\nTables Size");
                out.write("\nTable Name, Rows Count, Total Space KB, Used Space KB, Unused Space KB");
                for (int i = 0; i < listRelationInfos.size(); i++) {
                    out.write(MSSqlListRelationInfos.get(i).printTableInfo());
                }

                List<MSSqlRelationInfo> MSSqlListIndexesInfos = dbSizeMapper.findIndexesInfoMSSql();

                out.write("\n\nIndexes Size");
                out.write("\nTable Name, Index Name, Index Size KB");
                for (int i = 0; i < MSSqlListIndexesInfos.size(); i++) {
                    out.write(MSSqlListIndexesInfos.get(i).printIndexInfo());
                }
            }

            // Large Folders
            listLargeFolders = sqlMapper.findLargeFolders();
            out.write("\n\nLarge Folders");
            out.write("\nFolder Name, Node Reference, Type, No. of Child Nodes");
            if (listLargeFolders != null) {
                for (int i = 0; i < listLargeFolders.size(); i++) {
                    out.write(listLargeFolders.get(i).printLargeFolders());
                }
            }

            // Large Transactions
            listLargeTransactions = largeTransactionMapper.findBySize(largeTransactionSize);
            out.write("\n\nLarge Transactions");
            out.write("\nTransaction Id, Nodes Count");
            if (listLargeTransactions != null) {
                for (int i = 0; i < listLargeTransactions.size(); i++) {
                    out.write(listLargeTransactions.get(i).printLargeTransactions());
                }
            }

            // Access Control List
            String aclSize = sqlMapper.findAccessControlList();
            out.write("\n\nAccess Control List Size");
            out.write("\n" + aclSize);
            model.addAttribute("aclSize", aclSize);

            listAccessControlListEntries = sqlMapper.findAccessControlListEntries();
            Integer aceSize = 0;
            for (int i = 0; i < listAccessControlListEntries.size(); i++) {
                Integer count = Integer.valueOf(listAccessControlListEntries.get(i).getPermissionCount());
                aceSize = aceSize + count;
            }

            out.write("\n\nAccess Control List Entries");
            out.write("\nSize");
            out.write("\n" + String.valueOf(aceSize));

            out.write("\n\nACE Permission, Occurrences");
            if (listAccessControlListEntries != null) {
                for (int i = 0; i < listAccessControlListEntries.size(); i++) {
                    out.write(listAccessControlListEntries.get(i).printAccessControlListEntries());
                }
            }

            listAccessControlListInheritance = sqlMapper.findAccessControlListInheritance();
            out.write("\n\nAccess Control List Inheritance (True/False)");
            out.write("\nInheritance, Occurrences");
            if (listAccessControlListInheritance != null) {
                for (int i = 0; i < listAccessControlListInheritance.size(); i++) {
                    out.write(listAccessControlListInheritance.get(i).printAccessControlListInheritance());
                }
            }

            // Content Model Properties List
            listContentModelProperties = sqlMapper.findContentModelProperties();
            out.write("\n\nContent Model Properties");
            out.write("\nContent Model URI, Property");
            if (listContentModelProperties != null) {
                for (int i = 0; i < listContentModelProperties.size(); i++) {
                    out.write(listContentModelProperties.get(i).printContentModelProperties());
                }
            }

            // Activities
            listActivitiesFeed = sqlMapper.findActivitiesByActivityType();
            out.write("\n\nActivities by Activity Type");
            out.write("\nDate, Site Network, Activity Type, Activities Count");
            if (listActivitiesFeed != null) {
                for (int i = 0; i < listActivitiesFeed.size(); i++) {
                    out.write(listActivitiesFeed.get(i).printActivitiesByActivityType());
                }
            }

            listActivitiesFeed = sqlMapper.findActivitiesByUser();
            out.write("\n\nActivities by User");
            out.write("\nDate, Site Network, User Id, Activities Count");
            if (listActivitiesFeed != null) {
                for (int i = 0; i < listActivitiesFeed.size(); i++) {
                    out.write(listActivitiesFeed.get(i).printActivitiesByUser());
                }
            }

            listActivitiesFeed = sqlMapper.findActivitiesByApplicationInterface();
            out.write("\n\nActivities by Application Interface");
            out.write("\nDate, Site Network, Application Interface, Activities Count");
            if (listActivitiesFeed != null) {
                for (int i = 0; i < listActivitiesFeed.size(); i++) {
                    out.write(listActivitiesFeed.get(i).printActivitiesByInterface());
                }
            }

            /* Workflows */
            listWorkflows = workflowMapper.findAll();
            out.write("\n\nAll Workflows Grouped by Process Definition and Task Name");
            out.write("\nProcess Definition, Task Name, No Occurrences");
            if (listWorkflows != null) {
                for (int i = 0; i < listWorkflows.size(); i++) {
                    out.write(listWorkflows.get(i).printTasks());
                }
            }

            List < Workflow > listOpenWorkflows = workflowMapper.openWorkflows();
            out.write("\n\nOpen Workflows");
            out.write("\nProcess Definition, No Occurrences");
            if (listOpenWorkflows != null) {
                for (int i = 0; i < listOpenWorkflows.size(); i++) {
                    out.write(listOpenWorkflows.get(i).printProcesses());
                }
            }

            List < Workflow > listClosedWorkflows = workflowMapper.closedWorkflows();
            out.write("\n\nClosed Workflows");
            out.write("\nProcess Definition, No Occurrences");
            if (listClosedWorkflows != null) {
                for (int i = 0; i < listClosedWorkflows.size(); i++) {
                    out.write(listClosedWorkflows.get(i).printProcesses());
                }
            }

            List < Workflow > listOpenTasks = workflowMapper.openTasks();
            out.write("\n\nOpen Tasks");
            out.write("\nProcess Definition, Task Name, No Occurrences");
            if (listOpenTasks != null) {
                for (int i = 0; i < listOpenTasks.size(); i++) {
                    out.write(listOpenTasks.get(i).printTasks());
                }
            }

            List < Workflow > listClosedTasks = workflowMapper.closedTasks();
            out.write("\n\nClosed Tasks");
            out.write("\nProcess Definition, Task Name, No Occurrences");
            if (listClosedTasks != null) {
                for (int i = 0; i < listClosedTasks.size(); i++) {
                    out.write(listClosedTasks.get(i).printTasks());
                }
            }

            // Archived Nodes
            listArchivedNodes = archivedNodesMapper.findArchivedNodes();
            out.write("\n\nAll Archived Nodes");
            if (listArchivedNodes != null) {
                for (int i = 0; i < listArchivedNodes.size(); i++) {
                    out.write(listArchivedNodes.get(i).printArchivedNodes());
                }
            }

            listArchivedNodes = archivedNodesMapper.findArchivedNodesByUser();
            out.write("\n\nArchived Nodes by User");
            out.write("\nArchived Nodes, User");
            if (listArchivedNodes != null) {
                for (int i = 0; i < listArchivedNodes.size(); i++) {
                    out.write(listArchivedNodes.get(i).printArchivedNodesByUser());
                }
            }

            // List Nodes by Mimetype
            listNodesByMimeType = sqlMapper.findNodesSizeByMimeType();
            out.write("\n\nNodes Disk Space by Mimetype");
            out.write("\nMime Types, Nodes Count, Disk Space MB");
            if (listNodesByMimeType != null) {
                for (int i = 0; i < listNodesByMimeType.size(); i++) {
                    out.write(listNodesByMimeType.get(i).printNodesByMimeType());
                }
            }

            // Nodes disk space
            List < NodesList > diskSpace = sqlMapper.findNodesSize();
            model.addAttribute("totalDiskSpace", diskSpace);

            // List Nodes by Content Type
            listNodesByType = sqlMapper.findNodesByContentType();
            out.write("\n\nNodes by Content Type");
            out.write("\nNode Type, Nodes Count");
            if (listNodesByType != null) {
                for (int i = 0; i < listNodesByType.size(); i++) {
                    out.write(listNodesByType.get(i).printNodesByType());
                }
            }

            // List Nodes by Content Type
            listNodesByType = sqlMapper.findNodesByContentTypeAndMonth();
            out.write("\n\nNodes by Content Type Grouped by Month");
            out.write("\nDate, Node Type, Nodes Count");
            if (listNodesByType != null) {
                for (int i = 0; i < listNodesByType.size(); i++) {
                    out.write(listNodesByType.get(i).printNodesByTypeAndMonth());
                }
            }

            // List Nodes by Store
            listNodesByStore = sqlMapper.findNodesByStore();
            out.write("\n\nNodes by Store");
            out.write("\nStore, Nodes Count");
            if (listNodesByStore != null) {
                for (int i = 0; i < listNodesByStore.size(); i++) {
                    out.write(listNodesByStore.get(i).printNodesByStore());
                }
            }

            // Resource Locking
            listLockedResources = lockedResourcesMapper.findAll();
            out.write("\n\nResource Locking");
            out.write("\nIde, Lock Token, Start Time, Expiry Time, Shared Resource, Exclusive Resource, URI");
            for (int i = 0; i < listLockedResources.size(); i++) {
                out.write(listLockedResources.get(i).findAll());
            }

            // Authorities
            out.write("\n\nAuthorities");
            listUsers = authorityMapper.findUsers();
            out.write("\nUsers Count");
            if (listUsers != null) {
                for (int i = 0; i < listUsers.size(); i++) {
                    out.write(listUsers.get(i).printUsers());
                }
            }

            if (alfAuthStatus == true) {
                listAuthorizedUsers = sqlMapper.findAuthorizedUsers();
                out.write("\n\nAuthorized Users Count");
                if (listUsers != null) {
                    for (int i = 0; i < listAuthorizedUsers.size(); i++) {
                        out.write(listAuthorizedUsers.get(i).printUsers());
                    }
                }
                model.addAttribute("listAuthorizedUsers", listAuthorizedUsers);
            }

            listGroups = authorityMapper.findGroups();
            out.write("\n\nGroups Count");
            if (listUsers != null) {
                for (int i = 0; i < listUsers.size(); i++) {
                    out.write(listUsers.get(i).printGroups());
                }
            }

            // Solr memory
            List < SolrMemory > solrMemoryList = sqlMapper.solrMemory();

            for (int i = 0; i < solrMemoryList.size(); i++) {
                Long alfrescoNodes = Long.valueOf(solrMemoryList.get(i).getAlfrescoNodes());
                Long archiveNodes = Long.valueOf(solrMemoryList.get(i).getArchiveNodes());
                Long transactions = Long.valueOf(solrMemoryList.get(i).getTransactions());
                Long acls = Long.valueOf(solrMemoryList.get(i).getAcls());
                Long aclTransactions = Long.valueOf(solrMemoryList.get(i).getAclTransactions());
                double alfrescoCoreMemory = (double)(120*alfrescoNodes + 32*(transactions + acls + aclTransactions))/1024/1024/1024;
                double archiveCoreMemory = (double)(120*archiveNodes + 32*(transactions + acls + aclTransactions))/1024/1024/1024;
                double totalDataStructuresMemory = (double)alfrescoCoreMemory + archiveCoreMemory;
                double alfrescoSolrCachesMemory = (double)(alfrescoSolrFilterCacheSize + alfrescoSolrQueryResultCacheSize + alfrescoSolrAuthorityCacheSize + alfrescoSolrPathCacheSize) * (double)(2*alfrescoNodes + transactions + acls + aclTransactions)/8/1024/1024/1024;
                double archiveSolrCachesMemory = (double)(alfrescoSolrFilterCacheSize + alfrescoSolrQueryResultCacheSize + alfrescoSolrAuthorityCacheSize + alfrescoSolrPathCacheSize) * (double)(2*archiveNodes + transactions + acls + aclTransactions)/8/1024/1024/1024;
                double totalSolrCachesMemory = (double)alfrescoSolrCachesMemory + archiveSolrCachesMemory;
                double totalSolrMemory = (double)totalDataStructuresMemory + totalSolrCachesMemory;

                out.write("\n\nSolr Memory");
                out.write("\nAlfresco Nodes, Archive Nodes, Transactions, ACLs, ACL Transactions");
                out.write("\n" + String.valueOf(alfrescoNodes) + ", " + String.valueOf(archiveNodes) + ", " + String.valueOf(transactions) + ", " + String.valueOf(acls) + ", " + String.valueOf(aclTransactions));

                out.write("\n\nAlfresco Core Query Result Cache Size, Alfresco Core Authority Cache Size, Alfresco Core Path Cache Size, Alfresco Core Filter Cache Size");
                out.write("\n" + String.valueOf(archiveSolrQueryResultCacheSize) + ", " + String.valueOf(alfrescoSolrAuthorityCacheSize) + ", " + String.valueOf(alfrescoSolrPathCacheSize) + ", " + String.valueOf(alfrescoSolrFilterCacheSize));

                out.write("\n\nArchive Core Query Result Cache Size, Archive Core Authority Cache Size, Archive Core Path Cache Size, Archive Core Filter Cache Size");
                out.write("\n" + String.valueOf(archiveSolrQueryResultCacheSize) + ", " + String.valueOf(archiveSolrAuthorityCacheSize) + ", " + String.valueOf(archiveSolrPathCacheSize) + ", " + String.valueOf(archiveSolrFilterCacheSize));

                out.write("\n\nAlfresco Core Data Structures Memory GB, Archive Core Data Structures Memory GB, Total Solr Data Structures Memory GB");
                out.write("\n" + String.valueOf(alfrescoCoreMemory) + ", " + String.valueOf(archiveCoreMemory) + ", " + String.valueOf(totalDataStructuresMemory));
                out.write("\n\nAlfresco Core Cache Memory GB, Archive Core Cache Memory GB, Total Solr Cache Memory GB");
                out.write("\n" + String.valueOf(alfrescoSolrCachesMemory) + ", " + String.valueOf(archiveSolrCachesMemory) + ", " + String.valueOf(totalSolrCachesMemory));
                out.write("\n\nSolr Required Memory GB (for 2 searches per core)");
                out.write("\n" + String.valueOf(2*totalSolrMemory));
            }

            //JMX Properties
            List < JmxProperties > listJmxProperties = jmxPropertiesMapper.findJmxProperties();
            out.write("\n\nJMX Properties set in DB");
            out.write("\nProperty Name, Property Value");
            if (listJmxProperties != null) {
                for (int i = 0; i < listJmxProperties.size(); i++) {
                    out.write(listJmxProperties.get(i).printJmxProperties());
                }
            }
            model.addAttribute("reportFile", reportFile);

            out.close();
        } catch (IOException e) {
            System.out.println("Exception ");

        }
    }

    @RequestMapping("/workflows")
    public String workflows(Model model) {

        // Count workflows by process def and task name
        List < Workflow > listWorkflows = workflowMapper.findAll();
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
        if (dbType.equalsIgnoreCase("mysql") || dbType.equalsIgnoreCase("postgres")) {
            List < RelationInfo > listRelationInfos = sqlMapper.findTablesInfo();
            model.addAttribute("listRelationInfos", listRelationInfos);

            String dbSize = sqlMapper.findDbSize();
            model.addAttribute("dbSize", dbSize);
        } else if (dbType.equalsIgnoreCase("oracle")){
            List<OracleRelationInfo> OracleListRelationInfos = sqlMapper.findTablesInfo();
            model.addAttribute("OracleListRelationInfos", OracleListRelationInfos);

            List<OracleRelationInfo> OracleListIndexesInfos = dbSizeMapper.findIndexesInfoOracle();
            model.addAttribute("OracleListIndexesInfos", OracleListIndexesInfos);
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            List<MSSqlRelationInfo> MSSqlListRelationInfos = sqlMapper.findTablesInfo();
            model.addAttribute("MSSqlListRelationInfos", MSSqlListRelationInfos);

            List<MSSqlRelationInfo> MSSqlListIndexesInfos = dbSizeMapper.findIndexesInfoMSSql();
            model.addAttribute("MSSqlListIndexesInfos", MSSqlListIndexesInfos);
        }

        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private LargeFolderMapper largeFolderMapper;

    @RequestMapping("/largeFolders")
    public String largeFolders(@RequestParam(value = "size", required = true) String size, Model model) {
        List < LargeFolder > listLargeFolders = sqlMapper.findLargeFolders();

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
        List < LargeTransaction > listLargeTransactions = largeTransactionMapper.findBySize(largeTransactionSize);

        model.addAttribute("largeTransactionSize", largeTransactionSize);
        model.addAttribute("listLargeTransactions", listLargeTransactions);
        model.addAttribute("size", size);

        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/accessControlList")
    public String accessControlList(Model model) {
        String aclSize = sqlMapper.findAccessControlList();
        model.addAttribute("aclSize", aclSize);
        addAdditionalParamsToModel(model);

        List < AccessControlList > listAccessControlListEntries = sqlMapper.findAccessControlListEntries();
        model.addAttribute("listAccessControlListEntries", listAccessControlListEntries);

        Integer aceSize = 0;
        for (int i = 0; i < listAccessControlListEntries.size(); i++) {
            Integer count = Integer.valueOf(listAccessControlListEntries.get(i).getPermissionCount());
            aceSize = aceSize + count;
        }
        model.addAttribute("aceSize", aceSize);

        List < AccessControlList > listAccessControlListInheritance = sqlMapper.findAccessControlListInheritance();
        model.addAttribute("listAccessControlListInheritance", listAccessControlListInheritance);

        return null;
    }

    @RequestMapping("/contentModelProperties")
    public String contentModelProperties(Model model) {
        List < ContentModelProperties > listContentModelProperties = sqlMapper.findContentModelProperties();
        model.addAttribute("listContentModelProperties", listContentModelProperties);

        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/activitiesFeed")
    public String activitiesFeed(Model model) {

        // Activities by activity type
        List < ActivitiesFeed > listActivitiesFeed = sqlMapper.findActivitiesByActivityType();

        model.addAttribute("listActivitiesFeedByActivityType", listActivitiesFeed);

        // Activities by user
        listActivitiesFeed = sqlMapper.findActivitiesByUser();

        model.addAttribute("listActivitiesFeedByUser", listActivitiesFeed);

        // Activities by application interface
        listActivitiesFeed = sqlMapper.findActivitiesByApplicationInterface();

        model.addAttribute("listActivitiesFeedByAppTool", listActivitiesFeed);

        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private ArchivedNodesMapper archivedNodesMapper;

    @RequestMapping("/archivedNodes")
    public String archivedNodes(Model model) {

        // Archived nodes
        List < ArchivedNodes > listArchivedNodes = archivedNodesMapper.findArchivedNodes();

        model.addAttribute("listArchivedNodes", listArchivedNodes);

        // Archived nodes by user
        List < ArchivedNodes > listArchivedNodesByUser = archivedNodesMapper.findArchivedNodesByUser();

        model.addAttribute("listArchivedNodesByUser", listArchivedNodesByUser);
        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private NodeListMapper nodeListMapper;

    @RequestMapping("/listNodesByMimeType")
    public void nodesByMimeType(Model model) {

        // Nodes disk space
        List < NodesList > diskSpace = sqlMapper.findNodesSize();

        model.addAttribute("totalDiskSpace", diskSpace);

        // Nodes by mime type
        List < NodesList > listNodesByMimeType = sqlMapper.findNodesSizeByMimeType();

        model.addAttribute("listNodesByMimeType", listNodesByMimeType);

        addAdditionalParamsToModel(model);
    }

    @RequestMapping("/listNodesByType")
    public String nodesByType(Model model) {
        // Nodes by type
        List < NodesList > listNodesByType = sqlMapper.findNodesByContentType();

        model.addAttribute("listNodesByType", listNodesByType);

        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/listNodesByTypeAndMonth")
    public String listNodesByTypeAndMonth(Model model) {
        // Nodes by type and month
        List < NodesList > listNodesByTypeAndMonth = sqlMapper.findNodesByContentTypeAndMonth();

        model.addAttribute("listNodesByTypeAndMonth", listNodesByTypeAndMonth);

        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/listNodesByStore")
    public String nodesByStore(Model model) {
        // Nodes by store
        List < NodesList > listNodesByStore = sqlMapper.findNodesByStore();

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
        List < Authority > listUsers = authorityMapper.findUsers();

        model.addAttribute("listUsers", listUsers);

        if (alfAuthStatus == true) {
            //Count authorized users
            List<Authority> listAuthorizedUsers = sqlMapper.findAuthorizedUsers();

            model.addAttribute("listAuthorizedUsers", listAuthorizedUsers);
            model.addAttribute("alfAuthStatus", alfAuthStatus);
        }

        //Count groups
        List < Authority > listGroups = authorityMapper.findGroups();

        model.addAttribute("listGroups", listGroups);

        addAdditionalParamsToModel(model);

        return null;
    }

    @Autowired
    private JmxPropertiesMapper jmxPropertiesMapper;

    @RequestMapping("/jmxProperties")
    public String jmxProperties(Model model) {

        //JMX Properties
        List < JmxProperties > listJmxProperties = jmxPropertiesMapper.findJmxProperties();

        model.addAttribute("listJmxProperties", listJmxProperties);
        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/solrMemory")
    public String solrMemory(Model model) {
        List < SolrMemory > solrMemoryList = sqlMapper.solrMemory();
        model.addAttribute("solrMemoryList", solrMemoryList);

        for (int i = 0; i < solrMemoryList.size(); i++) {
            Long alfrescoNodes = Long.valueOf(solrMemoryList.get(i).getAlfrescoNodes());
            Long archiveNodes = Long.valueOf(solrMemoryList.get(i).getArchiveNodes());
            Long transactions = Long.valueOf(solrMemoryList.get(i).getTransactions());
            Long acls = Long.valueOf(solrMemoryList.get(i).getAcls());
            Long aclTransactions = Long.valueOf(solrMemoryList.get(i).getAclTransactions());

            double alfrescoCoreMemory = (double)(120*alfrescoNodes + 32*(transactions + acls + aclTransactions))/1024/1024/1024;
            double archiveCoreMemory = (double)(120*archiveNodes + 32*(transactions + acls + aclTransactions))/1024/1024/1024;
            double totalDataStructuresMemory = (double)alfrescoCoreMemory + archiveCoreMemory;
            double alfrescoSolrCachesMemory = (double)(alfrescoSolrFilterCacheSize + alfrescoSolrQueryResultCacheSize + alfrescoSolrAuthorityCacheSize + alfrescoSolrPathCacheSize) * (double)(2*alfrescoNodes + transactions + acls + aclTransactions)/8/1024/1024/1024;
            double archiveSolrCachesMemory = (double)(alfrescoSolrFilterCacheSize + alfrescoSolrQueryResultCacheSize + alfrescoSolrAuthorityCacheSize + alfrescoSolrPathCacheSize) * (double)(2*archiveNodes + transactions + acls + aclTransactions)/8/1024/1024/1024;
            double totalSolrCachesMemory = (double)alfrescoSolrCachesMemory + archiveSolrCachesMemory;
            double totalSolrMemory = (double)totalDataStructuresMemory + totalSolrCachesMemory;

            model.addAttribute("alfrescoCoreMemory", alfrescoCoreMemory);
            model.addAttribute("archiveCoreMemory", archiveCoreMemory);
            model.addAttribute("alfrescoSolrQueryResultCacheSize", alfrescoSolrQueryResultCacheSize);
            model.addAttribute("alfrescoSolrAuthorityCacheSize", alfrescoSolrAuthorityCacheSize);
            model.addAttribute("alfrescoSolrPathCacheSize", alfrescoSolrPathCacheSize);
            model.addAttribute("alfrescoSolrFilterCacheSize", alfrescoSolrFilterCacheSize);
            model.addAttribute("archiveSolrQueryResultCacheSize", archiveSolrQueryResultCacheSize);
            model.addAttribute("archiveSolrAuthorityCacheSize", archiveSolrAuthorityCacheSize);
            model.addAttribute("archiveSolrPathCacheSize", archiveSolrPathCacheSize);
            model.addAttribute("archiveSolrFilterCacheSize", archiveSolrFilterCacheSize);
            model.addAttribute("solrDataStructuresTotalMemory", totalDataStructuresMemory);
            model.addAttribute("alfrescoSolrCachesMemory", alfrescoSolrCachesMemory);
            model.addAttribute("archiveSolrCachesMemory", archiveSolrCachesMemory);
            model.addAttribute("totalSolrCachesMemory", totalSolrCachesMemory);
            model.addAttribute("totalSolrMemory", 2*totalSolrMemory);
            model.addAttribute("alfrescoSolrFilterCacheSize", alfrescoSolrFilterCacheSize);
        }

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