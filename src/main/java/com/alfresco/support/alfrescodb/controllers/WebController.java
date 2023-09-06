package com.alfresco.support.alfrescodb.controllers;

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
    private ExportController exportController;

    @Autowired
    SqlMapperController sqlMapper;

    @Value("${largeFolderSize}")
    private Integer largeFolderSize;

    @Value("${largeTransactionSize}")
    private Integer largeTransactionSize;

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
        addAdditionalParamsToModel(model);
        exportController.exportReport(model);
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

    @RequestMapping("/appliedPatches")
    public String appliedPatches(Model model) {

        // Count workflows by process def and task name
        List < AppliedPatches > listAppliedPatches = sqlMapper.findAppliedPatches();
        model.addAttribute("listAppliedPatches", listAppliedPatches);

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

        List < AccessControlList > aclNodeRepartition = sqlMapper.findACLNodeRepartition();
        model.addAttribute("aclNodeRepartition", aclNodeRepartition);

        List < AccessControlList > listAccessControlListEntries = sqlMapper.findAccessControlListEntries();
        model.addAttribute("listAccessControlListEntries", listAccessControlListEntries);

        String orphanedAcls = sqlMapper.findOrphanedAcls();
        model.addAttribute("orphanedAcls", orphanedAcls);

        Integer aceSize = 0;
        for (int i = 0; i < listAccessControlListEntries.size(); i++) {
            Integer count = Integer.valueOf(listAccessControlListEntries.get(i).getPermissionCount());
            aceSize = aceSize + count;
        }
        model.addAttribute("aceSize", aceSize);

        List < AccessControlList > listACEAuthorities = sqlMapper.findACEAuthorities();
        model.addAttribute("listACEAuthorities", listACEAuthorities);

        List < AccessControlList > listAclTypeRepartition = sqlMapper.findAclTypeRepartition();
        model.addAttribute("listAclTypeRepartition", listAclTypeRepartition);

        List < AccessControlList > listAclsHeight = sqlMapper.findAclsHeight();
        model.addAttribute("listAclsHeight", listAclsHeight);

        addAdditionalParamsToModel(model);

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
        // Nodes disk space
        List < NodesList > diskSpace = sqlMapper.findNodesSize();

        model.addAttribute("totalDiskSpace", diskSpace);
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