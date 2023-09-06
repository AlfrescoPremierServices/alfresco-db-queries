package com.alfresco.support.alfrescodb.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import com.alfresco.support.alfrescodb.dao.ArchivedNodesMapper;
import com.alfresco.support.alfrescodb.dao.AuthorityMapper;
import com.alfresco.support.alfrescodb.dao.DbSizeMapper;
import com.alfresco.support.alfrescodb.dao.JmxPropertiesMapper;
import com.alfresco.support.alfrescodb.dao.LargeTransactionMapper;
import com.alfresco.support.alfrescodb.dao.LockedResourcesMapper;
import com.alfresco.support.alfrescodb.dao.WorkflowMapper;
import com.alfresco.support.alfrescodb.model.AccessControlList;
import com.alfresco.support.alfrescodb.model.ActivitiesFeed;
import com.alfresco.support.alfrescodb.model.AppliedPatches;
import com.alfresco.support.alfrescodb.model.ArchivedNodes;
import com.alfresco.support.alfrescodb.model.Authority;
import com.alfresco.support.alfrescodb.model.ContentModelProperties;
import com.alfresco.support.alfrescodb.model.JmxProperties;
import com.alfresco.support.alfrescodb.model.LargeFolder;
import com.alfresco.support.alfrescodb.model.LargeTransaction;
import com.alfresco.support.alfrescodb.model.LockedResources;
import com.alfresco.support.alfrescodb.model.MSSqlRelationInfo;
import com.alfresco.support.alfrescodb.model.NodesList;
import com.alfresco.support.alfrescodb.model.OracleRelationInfo;
import com.alfresco.support.alfrescodb.model.RelationInfo;
import com.alfresco.support.alfrescodb.model.SolrMemory;
import com.alfresco.support.alfrescodb.model.Workflow;

public class ExportController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SqlMapperController sqlMapper;

    @Autowired
    private DbSizeMapper dbSizeMapper;

    @Autowired
    private LargeTransactionMapper largeTransactionMapper;

    @Autowired
    private WorkflowMapper workflowMapper;

    @Autowired
    private ArchivedNodesMapper archivedNodesMapper;

    @Autowired
    private LockedResourcesMapper lockedResourcesMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private JmxPropertiesMapper jmxPropertiesMapper;

    @Value("${reportFile}")
    private String reportFile;

    @Value("${spring.datasource.platform}")
    private String dbType;

    @Value("${largeTransactionSize}")
    private Integer largeTransactionSize;

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

    List<RelationInfo> listRelationInfos;
    List<LargeFolder> listLargeFolders;
    List<LargeTransaction> listLargeTransactions;
    List<AccessControlList> listAccessControlListEntries;
    List<AccessControlList> aclTypeRepartition;
    List<AccessControlList> aclNodeRepartition;
    List<AccessControlList> aclsHeight;
    List<AccessControlList> aceAuthorities;
    List<ContentModelProperties> listContentModelProperties;
    List<ActivitiesFeed> listActivitiesFeed;
    List<ArchivedNodes> listArchivedNodes;
    List<NodesList> listNodesByMimeType;
    List<NodesList> listNodesByType;
    List<NodesList> listNodesByStore;
    List<LockedResources> listLockedResources;
    List<Authority> listUsers;
    List<Authority> listAuthorizedUsers;
    List<Authority> listGroups;
    List<Workflow> listWorkflows;
    List<JmxProperties> listJmxProperties;
    List<AppliedPatches> listAppliedPatches;

    public void exportReport(Model model) {

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(reportFile));

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
            } else if (dbType.equalsIgnoreCase("oracle")) {
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

            String orphanedAcls = sqlMapper.findOrphanedAcls();
            out.write("\n\nOrphaned ACLs");
            out.write("\n" + orphanedAcls);

            aclNodeRepartition = sqlMapper.findACLNodeRepartition();
            out.write("\n\nNodes Repartition");
            out.write("\nACL ID, Nodes");
            if (aclNodeRepartition != null) {
                for (int i = 0; i < aclNodeRepartition.size(); i++) {
                    out.write(aclNodeRepartition.get(i).printAclNode());
                }
            }

            aclTypeRepartition = sqlMapper.findAclTypeRepartition();
            out.write("\n\nType repartition");
            out.write("\nACL type, Count");
            if (aclTypeRepartition != null) {
                for (int i = 0; i < aclTypeRepartition.size(); i++) {
                    out.write(aclTypeRepartition.get(i).printAclType());
                }
            }

            aclsHeight = sqlMapper.findAclsHeight();
            out.write("\n\nNumber of ACEs in ACLs");
            out.write("\nACL ID, ACE count");
            for (int i = 0; i < aclsHeight.size(); i++) {
                out.write(aclsHeight.get(i).printAclHeight());
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

            aceAuthorities = sqlMapper.findACEAuthorities();
            out.write("\n\nAuthorities & ACEs");
            out.write("\nAuthority hash, ACEs");
            if (aceAuthorities != null) {
                for (int i = 0; i < aceAuthorities.size(); i++) {
                    out.write(aceAuthorities.get(i).printAuthorityAce());
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

            List<Workflow> listOpenWorkflows = workflowMapper.openWorkflows();
            out.write("\n\nOpen Workflows");
            out.write("\nProcess Definition, No Occurrences");
            if (listOpenWorkflows != null) {
                for (int i = 0; i < listOpenWorkflows.size(); i++) {
                    out.write(listOpenWorkflows.get(i).printProcesses());
                }
            }

            List<Workflow> listClosedWorkflows = workflowMapper.closedWorkflows();
            out.write("\n\nClosed Workflows");
            out.write("\nProcess Definition, No Occurrences");
            if (listClosedWorkflows != null) {
                for (int i = 0; i < listClosedWorkflows.size(); i++) {
                    out.write(listClosedWorkflows.get(i).printProcesses());
                }
            }

            List<Workflow> listOpenTasks = workflowMapper.openTasks();
            out.write("\n\nOpen Tasks");
            out.write("\nProcess Definition, Task Name, No Occurrences");
            if (listOpenTasks != null) {
                for (int i = 0; i < listOpenTasks.size(); i++) {
                    out.write(listOpenTasks.get(i).printTasks());
                }
            }

            List<Workflow> listClosedTasks = workflowMapper.closedTasks();
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
            List<NodesList> diskSpace = sqlMapper.findNodesSize();
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
            if (listGroups != null) {
                for (int i = 0; i < listGroups.size(); i++) {
                    out.write(listGroups.get(i).printGroups());
                }
            }

            // Solr memory
            List<SolrMemory> solrMemoryList = sqlMapper.solrMemory();

            for (int i = 0; i < solrMemoryList.size(); i++) {
                Long alfrescoNodes = Long.valueOf(solrMemoryList.get(i).getAlfrescoNodes());
                Long archiveNodes = Long.valueOf(solrMemoryList.get(i).getArchiveNodes());
                Long transactions = Long.valueOf(solrMemoryList.get(i).getTransactions());
                Long acls = Long.valueOf(solrMemoryList.get(i).getAcls());
                Long aclTransactions = Long.valueOf(solrMemoryList.get(i).getAclTransactions());
                double alfrescoCoreMemory = (double) (120 * alfrescoNodes
                        + 32 * (transactions + acls + aclTransactions)) / 1024 / 1024 / 1024;
                double archiveCoreMemory = (double) (120 * archiveNodes + 32 * (transactions + acls + aclTransactions))
                        / 1024 / 1024 / 1024;
                double totalDataStructuresMemory = (double) alfrescoCoreMemory + archiveCoreMemory;
                double alfrescoSolrCachesMemory = (double) (alfrescoSolrFilterCacheSize
                        + alfrescoSolrQueryResultCacheSize + alfrescoSolrAuthorityCacheSize + alfrescoSolrPathCacheSize)
                        * (double) (2 * alfrescoNodes + transactions + acls + aclTransactions) / 8 / 1024 / 1024 / 1024;
                double archiveSolrCachesMemory = (double) (alfrescoSolrFilterCacheSize
                        + alfrescoSolrQueryResultCacheSize + alfrescoSolrAuthorityCacheSize + alfrescoSolrPathCacheSize)
                        * (double) (2 * archiveNodes + transactions + acls + aclTransactions) / 8 / 1024 / 1024 / 1024;
                double totalSolrCachesMemory = (double) alfrescoSolrCachesMemory + archiveSolrCachesMemory;
                double totalSolrMemory = (double) totalDataStructuresMemory + totalSolrCachesMemory;

                out.write("\n\nSolr Memory");
                out.write("\nAlfresco Nodes, Archive Nodes, Transactions, ACLs, ACL Transactions");
                out.write("\n" + String.valueOf(alfrescoNodes) + ", " + String.valueOf(archiveNodes) + ", "
                        + String.valueOf(transactions) + ", " + String.valueOf(acls) + ", "
                        + String.valueOf(aclTransactions));

                out.write(
                        "\n\nAlfresco Core Query Result Cache Size, Alfresco Core Authority Cache Size, Alfresco Core Path Cache Size, Alfresco Core Filter Cache Size");
                out.write("\n" + String.valueOf(archiveSolrQueryResultCacheSize) + ", "
                        + String.valueOf(alfrescoSolrAuthorityCacheSize) + ", "
                        + String.valueOf(alfrescoSolrPathCacheSize) + ", "
                        + String.valueOf(alfrescoSolrFilterCacheSize));

                out.write(
                        "\n\nArchive Core Query Result Cache Size, Archive Core Authority Cache Size, Archive Core Path Cache Size, Archive Core Filter Cache Size");
                out.write("\n" + String.valueOf(archiveSolrQueryResultCacheSize) + ", "
                        + String.valueOf(archiveSolrAuthorityCacheSize) + ", "
                        + String.valueOf(archiveSolrPathCacheSize) + ", " + String.valueOf(archiveSolrFilterCacheSize));

                out.write(
                        "\n\nAlfresco Core Data Structures Memory GB, Archive Core Data Structures Memory GB, Total Solr Data Structures Memory GB");
                out.write("\n" + String.valueOf(alfrescoCoreMemory) + ", " + String.valueOf(archiveCoreMemory) + ", "
                        + String.valueOf(totalDataStructuresMemory));
                out.write(
                        "\n\nAlfresco Core Cache Memory GB, Archive Core Cache Memory GB, Total Solr Cache Memory GB");
                out.write("\n" + String.valueOf(alfrescoSolrCachesMemory) + ", "
                        + String.valueOf(archiveSolrCachesMemory) + ", " + String.valueOf(totalSolrCachesMemory));
                out.write("\n\nSolr Required Memory GB (for 2 searches per core)");
                out.write("\n" + String.valueOf(2 * totalSolrMemory));
            }

            // JMX Properties
            listJmxProperties = jmxPropertiesMapper.findJmxProperties();
            out.write("\n\nJMX Properties set in DB");
            out.write("\nProperty Name, Property Value");
            if (listJmxProperties != null) {
                for (int i = 0; i < listJmxProperties.size(); i++) {
                    out.write(listJmxProperties.get(i).printJmxProperties());
                }
            }

            // Applied Patches
            listAppliedPatches = sqlMapper.findAppliedPatches();
            out.write("\n\nApplied Patches");
            out.write("\nId, Applied to Schema, Applied on Date, Applied to Server, Was Executed, Succeeded, Report");
            if (listAppliedPatches != null) {
                for (int i = 0; i < listAppliedPatches.size(); i++) {
                    out.write(listAppliedPatches.get(i).printAppliedPatches());
                }
            }

            model.addAttribute("reportFile", reportFile);
            out.close();
        } catch (IOException e) {
            System.out.println("Exception ");

        }
    }

}
