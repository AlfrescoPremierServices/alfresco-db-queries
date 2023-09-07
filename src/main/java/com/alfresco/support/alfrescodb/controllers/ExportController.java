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

    @Value("${reportSplit}")
    private boolean multiReportFile;

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

    private List<RelationInfo> listRelationInfos;
    private List<LargeFolder> listLargeFolders;
    private List<LargeTransaction> listLargeTransactions;
    private List<AccessControlList> listAccessControlListEntries;
    private List<AccessControlList> aclTypeRepartition;
    private List<AccessControlList> aclNodeRepartition;
    private List<AccessControlList> aclsHeight;
    private List<AccessControlList> aceAuthorities;
    private List<ContentModelProperties> listContentModelProperties;
    private List<ActivitiesFeed> listActivitiesFeed;
    private List<ArchivedNodes> listArchivedNodes;
    private List<NodesList> listNodesByMimeType;
    private List<NodesList> listNodesByType;
    private List<NodesList> listNodesByStore;
    private List<LockedResources> listLockedResources;
    private List<Authority> listUsers;
    private List<Authority> listAuthorizedUsers;
    private List<Authority> listGroups;
    private List<Workflow> listWorkflows;
    private List<JmxProperties> listJmxProperties;
    private List<AppliedPatches> listAppliedPatches;

    public void exportReport(Model model) {
        try {
            BufferedWriter out;
            if (multiReportFile) {
                reportFile = reportFile.substring(0, reportFile.length()-4); //removing .csv
                out = this.prepareOutputFile(reportFile + "_DBSize.csv");
            } else {
                out = this.prepareOutputFile(reportFile);
            }
            // DB Size
            this.writeDBTableSize(out);
            // Large folders
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_LargeFolder.csv");
            }
            this.writeLargeFolder(out);
            // Large Transactions
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_LargeTransaction.csv");
            }
            this.writeLargeTransaction(out);
            // Access Control List
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_AccessControlList.csv");
            }
            this.writeLargeACL(out);
            // Content Model Properties List
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_ContentModelProperties.csv");
            }
            this.writeContentModelProps(out);
            // Activities
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_Activities.csv");
            }
            this.writeActivities(out);
            /* Workflows */
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_Workflows.csv");
            }
            this.writeWorkflows(out);
            // Archived Nodes
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_ArchivedNodes.csv");
            }
            this.writeArchivedNodes(out);
            // List Nodes by Mimetype
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_NodesByMimetype.csv");
            }
            this.writeNodesByMimetype(out);
            // Nodes disk space -- commented out as it's not actually printing on file
            //if (multiReportFile) {
            //    out.close();
            //    out = this.prepareOutputFile(reportFile + "_NodesDiskSpace.csv");
            //}
            // this.writeNodesDiskSpace(out);
            // List Nodes by Content Type
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_NodesByContentType.csv");
            }
            this.writeNodesByContentType(out);
            // List Nodes by Content Type per month
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_NodesByContentTypeAndMonth.csv");
            }
            this.writeNodesByContentTypeAndMonth(out);
            // List Nodes by Store
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_NodesByStore.csv");
            }
            this.writeNodesByStore(out);
            // Resource Locking
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_ResourceLocking.csv");
            }
            this.writeResouceLocking(out);
            // Authorities
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_Authorities.csv");
            }
            this.writeAuthorities(out);
            // Solr memory
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_SolrMemory.csv");
            }
            this.writeSolrMemory(out);
            // JMX Properties
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_JMXProperties.csv");
            }
            this.writeJmxProps(out);
            // Applied Patches
            if (multiReportFile) {
                out.close();
                out = this.prepareOutputFile(reportFile + "_AppliedPatches.csv");
            }
            this.writeAppliedPatches(out);

            model.addAttribute("reportFile", reportFile);
            out.close();
        } catch (IOException e) {
            System.out.println("Exception ");
            e.printStackTrace();
        }
    }

    private BufferedWriter prepareOutputFile(String name) throws IOException {
        logger.debug("Creating new output file: "+name);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(name));
            return out;
        } catch (IOException ioex) {
            throw new IOException("Exception creating output file: " + name, ioex);
        }
    }

    /** This is the only method that actually write to the file provided */
    private void writeLine(BufferedWriter out, String str) {
        try {
            out.write(str);
        } catch (IOException ioex) {
            System.out.println("Exception while writing to file");
            ioex.printStackTrace();
        }
    }

    private void writeDBTableSize(BufferedWriter out) {
        // Database Size
        listRelationInfos = sqlMapper.findTablesInfo();

        if (dbType.equalsIgnoreCase("mysql") || dbType.equalsIgnoreCase("postgres")) {
            this.writeLine(out, "\nTable Name, Total Size, Row Estimate, Table Size, Index Size");

            for (int i = 0; i < listRelationInfos.size(); i++) {
                this.writeLine(out, listRelationInfos.get(i).printDbInfo());
            }

            String dbSize = sqlMapper.findDbSize();
            this.writeLine(out, "\n\nDatabase Size");
            this.writeLine(out, "\nSize");
            this.writeLine(out, dbSize);

        } else if (dbType.equalsIgnoreCase("oracle")) {
            List<OracleRelationInfo> OracleListRelationInfos = sqlMapper.findTablesInfo();

            this.writeLine(out, "\nTables Size");
            this.writeLine(out, "\nTable Name, Size MB");
            for (int i = 0; i < OracleListRelationInfos.size(); i++) {
                this.writeLine(out, OracleListRelationInfos.get(i).printTableInfo());
            }

            List<OracleRelationInfo> OracleListIndexesInfos = dbSizeMapper.findIndexesInfoOracle();

            this.writeLine(out, "\n\nIndexes Size");
            this.writeLine(out, "\nTable Name, Index Name, Index Size MB");
            for (int i = 0; i < OracleListIndexesInfos.size(); i++) {
                this.writeLine(out, OracleListIndexesInfos.get(i).printIndexInfo());
            }
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            List<MSSqlRelationInfo> MSSqlListRelationInfos = sqlMapper.findTablesInfo();

            this.writeLine(out, "\nTables Size");
            this.writeLine(out, "\nTable Name, Rows Count, Total Space KB, Used Space KB, Unused Space KB");
            for (int i = 0; i < listRelationInfos.size(); i++) {
                this.writeLine(out, MSSqlListRelationInfos.get(i).printTableInfo());
            }

            List<MSSqlRelationInfo> MSSqlListIndexesInfos = dbSizeMapper.findIndexesInfoMSSql();

            this.writeLine(out, "\n\nIndexes Size");
            this.writeLine(out, "\nTable Name, Index Name, Index Size KB");
            for (int i = 0; i < MSSqlListIndexesInfos.size(); i++) {
                this.writeLine(out, MSSqlListIndexesInfos.get(i).printIndexInfo());
            }
        }
    }

    private void writeLargeFolder(BufferedWriter out) {
        listLargeFolders = sqlMapper.findLargeFolders();
        this.writeLine(out, "\n\nLarge Folders");
        this.writeLine(out, "\nFolder Name, Node Reference, Type, No. of Child Nodes");
        if (listLargeFolders != null) {
            for (int i = 0; i < listLargeFolders.size(); i++) {
                this.writeLine(out, listLargeFolders.get(i).printLargeFolders());
            }
        }
    }

    private void writeLargeTransaction(BufferedWriter out) {
        listLargeTransactions = largeTransactionMapper.findBySize(largeTransactionSize);
        this.writeLine(out, "\n\nLarge Transactions");
        this.writeLine(out, "\nTransaction Id, Nodes Count");
        if (listLargeTransactions != null) {
            for (int i = 0; i < listLargeTransactions.size(); i++) {
                this.writeLine(out, listLargeTransactions.get(i).printLargeTransactions());
            }
        }
    }

    private void writeLargeACL(BufferedWriter out) {
        String aclSize = sqlMapper.findAccessControlList();
        this.writeLine(out, "\n\nAccess Control List Size");
        this.writeLine(out, "\n" + aclSize);

        listAccessControlListEntries = sqlMapper.findAccessControlListEntries();
        Integer aceSize = 0;
        for (int i = 0; i < listAccessControlListEntries.size(); i++) {
            Integer count = Integer.valueOf(listAccessControlListEntries.get(i).getPermissionCount());
            aceSize = aceSize + count;
        }

        String orphanedAcls = sqlMapper.findOrphanedAcls();
        this.writeLine(out, "\n\nOrphaned ACLs");
        this.writeLine(out, "\n" + orphanedAcls);

        aclNodeRepartition = sqlMapper.findACLNodeRepartition();
        this.writeLine(out, "\n\nNodes Repartition");
        this.writeLine(out, "\nACL ID, Nodes");
        if (aclNodeRepartition != null) {
            for (int i = 0; i < aclNodeRepartition.size(); i++) {
                this.writeLine(out, aclNodeRepartition.get(i).printAclNode());
            }
        }

        aclTypeRepartition = sqlMapper.findAclTypeRepartition();
        this.writeLine(out, "\n\nType repartition");
        this.writeLine(out, "\nACL type, Count");
        if (aclTypeRepartition != null) {
            for (int i = 0; i < aclTypeRepartition.size(); i++) {
                this.writeLine(out, aclTypeRepartition.get(i).printAclType());
            }
        }

        aclsHeight = sqlMapper.findAclsHeight();
        this.writeLine(out, "\n\nNumber of ACEs in ACLs");
        this.writeLine(out, "\nACL ID, ACE count");
        for (int i = 0; i < aclsHeight.size(); i++) {
            this.writeLine(out, aclsHeight.get(i).printAclHeight());
        }

        this.writeLine(out, "\n\nAccess Control List Entries");
        this.writeLine(out, "\nSize");
        this.writeLine(out, "\n" + String.valueOf(aceSize));

        this.writeLine(out, "\n\nACE Permission, Occurrences");
        if (listAccessControlListEntries != null) {
            for (int i = 0; i < listAccessControlListEntries.size(); i++) {
                this.writeLine(out, listAccessControlListEntries.get(i).printAccessControlListEntries());
            }
        }

        aceAuthorities = sqlMapper.findACEAuthorities();
        this.writeLine(out, "\n\nAuthorities & ACEs");
        this.writeLine(out, "\nAuthority hash, ACEs");
        if (aceAuthorities != null) {
            for (int i = 0; i < aceAuthorities.size(); i++) {
                this.writeLine(out, aceAuthorities.get(i).printAuthorityAce());
            }
        }
    }

    private void writeContentModelProps(BufferedWriter out) {
        listContentModelProperties = sqlMapper.findContentModelProperties();
        this.writeLine(out, "\n\nContent Model Properties");
        this.writeLine(out, "\nContent Model URI, Property");
        if (listContentModelProperties != null) {
            for (int i = 0; i < listContentModelProperties.size(); i++) {
                this.writeLine(out, listContentModelProperties.get(i).printContentModelProperties());
            }
        }
    }

    private void writeActivities(BufferedWriter out) {
        listActivitiesFeed = sqlMapper.findActivitiesByActivityType();
        this.writeLine(out, "\n\nActivities by Activity Type");
        this.writeLine(out, "\nDate, Site Network, Activity Type, Activities Count");
        if (listActivitiesFeed != null) {
            for (int i = 0; i < listActivitiesFeed.size(); i++) {
                this.writeLine(out, listActivitiesFeed.get(i).printActivitiesByActivityType());
            }
        }

        listActivitiesFeed = sqlMapper.findActivitiesByUser();
        this.writeLine(out, "\n\nActivities by User");
        this.writeLine(out, "\nDate, Site Network, User Id, Activities Count");
        if (listActivitiesFeed != null) {
            for (int i = 0; i < listActivitiesFeed.size(); i++) {
                this.writeLine(out, listActivitiesFeed.get(i).printActivitiesByUser());
            }
        }

        listActivitiesFeed = sqlMapper.findActivitiesByApplicationInterface();
        this.writeLine(out, "\n\nActivities by Application Interface");
        this.writeLine(out, "\nDate, Site Network, Application Interface, Activities Count");
        if (listActivitiesFeed != null) {
            for (int i = 0; i < listActivitiesFeed.size(); i++) {
                this.writeLine(out, listActivitiesFeed.get(i).printActivitiesByInterface());
            }
        }
    }

    private void writeWorkflows(BufferedWriter out) {
        listWorkflows = workflowMapper.findAll();
        this.writeLine(out, "\n\nAll Workflows Grouped by Process Definition and Task Name");
        this.writeLine(out, "\nProcess Definition, Task Name, No Occurrences");
        if (listWorkflows != null) {
            for (int i = 0; i < listWorkflows.size(); i++) {
                this.writeLine(out, listWorkflows.get(i).printTasks());
            }
        }

        List<Workflow> listOpenWorkflows = workflowMapper.openWorkflows();
        this.writeLine(out, "\n\nOpen Workflows");
        this.writeLine(out, "\nProcess Definition, No Occurrences");
        if (listOpenWorkflows != null) {
            for (int i = 0; i < listOpenWorkflows.size(); i++) {
                this.writeLine(out, listOpenWorkflows.get(i).printProcesses());
            }
        }

        List<Workflow> listClosedWorkflows = workflowMapper.closedWorkflows();
        this.writeLine(out, "\n\nClosed Workflows");
        this.writeLine(out, "\nProcess Definition, No Occurrences");
        if (listClosedWorkflows != null) {
            for (int i = 0; i < listClosedWorkflows.size(); i++) {
                this.writeLine(out, listClosedWorkflows.get(i).printProcesses());
            }
        }

        List<Workflow> listOpenTasks = workflowMapper.openTasks();
        this.writeLine(out, "\n\nOpen Tasks");
        this.writeLine(out, "\nProcess Definition, Task Name, No Occurrences");
        if (listOpenTasks != null) {
            for (int i = 0; i < listOpenTasks.size(); i++) {
                this.writeLine(out, listOpenTasks.get(i).printTasks());
            }
        }

        List<Workflow> listClosedTasks = workflowMapper.closedTasks();
        this.writeLine(out, "\n\nClosed Tasks");
        this.writeLine(out, "\nProcess Definition, Task Name, No Occurrences");
        if (listClosedTasks != null) {
            for (int i = 0; i < listClosedTasks.size(); i++) {
                this.writeLine(out, listClosedTasks.get(i).printTasks());
            }
        }
    }

    private void writeArchivedNodes(BufferedWriter out) {
        listArchivedNodes = archivedNodesMapper.findArchivedNodes();
        this.writeLine(out, "\n\nAll Archived Nodes");
        if (listArchivedNodes != null) {
            for (int i = 0; i < listArchivedNodes.size(); i++) {
                this.writeLine(out, listArchivedNodes.get(i).printArchivedNodes());
            }
        }

        listArchivedNodes = archivedNodesMapper.findArchivedNodesByUser();
        this.writeLine(out, "\n\nArchived Nodes by User");
        this.writeLine(out, "\nArchived Nodes, User");
        if (listArchivedNodes != null) {
            for (int i = 0; i < listArchivedNodes.size(); i++) {
                this.writeLine(out, listArchivedNodes.get(i).printArchivedNodesByUser());
            }
        }
    }

    private void writeNodesByMimetype(BufferedWriter out) {
        listNodesByMimeType = sqlMapper.findNodesSizeByMimeType();
        this.writeLine(out, "\n\nNodes Disk Space by Mimetype");
        this.writeLine(out, "\nMime Types, Nodes Count, Disk Space MB");
        if (listNodesByMimeType != null) {
            for (int i = 0; i < listNodesByMimeType.size(); i++) {
                this.writeLine(out, listNodesByMimeType.get(i).printNodesByMimeType());
            }
        }
    }

    private void writeNodesDiskSpace(BufferedWriter out) {
        // List<NodesList> diskSpace = sqlMapper.findNodesSize();
        // model.addAttribute("totalDiskSpace", diskSpace);
    }

    private void writeNodesByContentType(BufferedWriter out) {
        listNodesByType = sqlMapper.findNodesByContentType();
        this.writeLine(out, "\nNode Type, Nodes Count");
        if (listNodesByType != null) {
            for (int i = 0; i < listNodesByType.size(); i++) {
                this.writeLine(out, listNodesByType.get(i).printNodesByType());
            }
        }
    }

    private void writeNodesByContentTypeAndMonth(BufferedWriter out) {
        listNodesByType = sqlMapper.findNodesByContentTypeAndMonth();
        this.writeLine(out, "\n\nNodes by Content Type Grouped by Month");
        this.writeLine(out, "\nDate, Node Type, Nodes Count");
        if (listNodesByType != null) {
            for (int i = 0; i < listNodesByType.size(); i++) {
                this.writeLine(out, listNodesByType.get(i).printNodesByTypeAndMonth());
            }
        }
    }

    private void writeNodesByStore(BufferedWriter out) {
        listNodesByStore = sqlMapper.findNodesByStore();
        this.writeLine(out, "\n\nNodes by Store");
        this.writeLine(out, "\nStore, Nodes Count");
        if (listNodesByStore != null) {
            for (int i = 0; i < listNodesByStore.size(); i++) {
                this.writeLine(out, listNodesByStore.get(i).printNodesByStore());
            }
        }
    }

    private void writeResouceLocking(BufferedWriter out) {
        listLockedResources = lockedResourcesMapper.findAll();
        this.writeLine(out, "\n\nResource Locking");
        this.writeLine(out, "\nIde, Lock Token, Start Time, Expiry Time, Shared Resource, Exclusive Resource, URI");
        for (int i = 0; i < listLockedResources.size(); i++) {
            this.writeLine(out, listLockedResources.get(i).findAll());
        }
    }

    private void writeAuthorities(BufferedWriter out) {
        this.writeLine(out, "\n\nAuthorities");
        listUsers = authorityMapper.findUsers();
        this.writeLine(out, "\nUsers Count");
        if (listUsers != null) {
            for (int i = 0; i < listUsers.size(); i++) {
                this.writeLine(out, listUsers.get(i).printUsers());
            }
        }

        if (alfAuthStatus == true) {
            listAuthorizedUsers = sqlMapper.findAuthorizedUsers();
            this.writeLine(out, "\n\nAuthorized Users Count");
            if (listUsers != null) {
                for (int i = 0; i < listAuthorizedUsers.size(); i++) {
                    this.writeLine(out, listAuthorizedUsers.get(i).printUsers());
                }
            }
            // model.addAttribute("listAuthorizedUsers", listAuthorizedUsers);
        }

        listGroups = authorityMapper.findGroups();
        this.writeLine(out, "\n\nGroups Count");
        if (listGroups != null) {
            for (int i = 0; i < listGroups.size(); i++) {
                this.writeLine(out, listGroups.get(i).printGroups());
            }
        }
    }

    private void writeSolrMemory(BufferedWriter out) {
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

            this.writeLine(out, "\n\nSolr Memory");
            this.writeLine(out, "\nAlfresco Nodes, Archive Nodes, Transactions, ACLs, ACL Transactions");
            this.writeLine(out, "\n" + String.valueOf(alfrescoNodes) + ", " + String.valueOf(archiveNodes) + ", "
                    + String.valueOf(transactions) + ", " + String.valueOf(acls) + ", "
                    + String.valueOf(aclTransactions));

            this.writeLine(out,
                    "\n\nAlfresco Core Query Result Cache Size, Alfresco Core Authority Cache Size, Alfresco Core Path Cache Size, Alfresco Core Filter Cache Size");
            this.writeLine(out, "\n" + String.valueOf(archiveSolrQueryResultCacheSize) + ", "
                    + String.valueOf(alfrescoSolrAuthorityCacheSize) + ", "
                    + String.valueOf(alfrescoSolrPathCacheSize) + ", "
                    + String.valueOf(alfrescoSolrFilterCacheSize));

            this.writeLine(out,
                    "\n\nArchive Core Query Result Cache Size, Archive Core Authority Cache Size, Archive Core Path Cache Size, Archive Core Filter Cache Size");
            this.writeLine(out, "\n" + String.valueOf(archiveSolrQueryResultCacheSize) + ", "
                    + String.valueOf(archiveSolrAuthorityCacheSize) + ", "
                    + String.valueOf(archiveSolrPathCacheSize) + ", " + String.valueOf(archiveSolrFilterCacheSize));

            this.writeLine(out,
                    "\n\nAlfresco Core Data Structures Memory GB, Archive Core Data Structures Memory GB, Total Solr Data Structures Memory GB");
            this.writeLine(out,
                    "\n" + String.valueOf(alfrescoCoreMemory) + ", " + String.valueOf(archiveCoreMemory) + ", "
                            + String.valueOf(totalDataStructuresMemory));
            this.writeLine(out,
                    "\n\nAlfresco Core Cache Memory GB, Archive Core Cache Memory GB, Total Solr Cache Memory GB");
            this.writeLine(out, "\n" + String.valueOf(alfrescoSolrCachesMemory) + ", "
                    + String.valueOf(archiveSolrCachesMemory) + ", " + String.valueOf(totalSolrCachesMemory));
            this.writeLine(out, "\n\nSolr Required Memory GB (for 2 searches per core)");
            this.writeLine(out, "\n" + String.valueOf(2 * totalSolrMemory));
        }
    }

    private void writeJmxProps(BufferedWriter out) {
        listJmxProperties = jmxPropertiesMapper.findJmxProperties();
        this.writeLine(out, "\n\nJMX Properties set in DB");
        this.writeLine(out, "\nProperty Name, Property Value");
        if (listJmxProperties != null) {
            for (int i = 0; i < listJmxProperties.size(); i++) {
                this.writeLine(out, listJmxProperties.get(i).printJmxProperties());
            }
        }
    }

    private void writeAppliedPatches(BufferedWriter out) {
        listAppliedPatches = sqlMapper.findAppliedPatches();
        this.writeLine(out, "\n\nApplied Patches");
        this.writeLine(out,
                "\nId, Applied to Schema, Applied on Date, Applied to Server, Was Executed, Succeeded, Report");
        if (listAppliedPatches != null) {
            for (int i = 0; i < listAppliedPatches.size(); i++) {
                this.writeLine(out, listAppliedPatches.get(i).printAppliedPatches());
            }
        }
    }

}
