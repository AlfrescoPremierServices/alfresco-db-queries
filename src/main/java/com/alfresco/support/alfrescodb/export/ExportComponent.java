package com.alfresco.support.alfrescodb.export;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.alfresco.support.alfrescodb.DAOMapper;
import com.alfresco.support.alfrescodb.beans.AccessControlBean;
import com.alfresco.support.alfrescodb.beans.ActivitiesFeedByApplication;
import com.alfresco.support.alfrescodb.beans.ActivitiesFeedByTypeBean;
import com.alfresco.support.alfrescodb.beans.ActivitiesFeedByUserBean;
import com.alfresco.support.alfrescodb.beans.AppliedPatchesBean;
import com.alfresco.support.alfrescodb.beans.ArchivedNodesBean;
import com.alfresco.support.alfrescodb.beans.ContentModelBean;
import com.alfresco.support.alfrescodb.beans.DbMySQLBean;
import com.alfresco.support.alfrescodb.beans.DbOracleIndexBean;
import com.alfresco.support.alfrescodb.beans.DbOracleTableBean;
import com.alfresco.support.alfrescodb.beans.DbPostgresBean;
import com.alfresco.support.alfrescodb.beans.LargeFolderBean;
import com.alfresco.support.alfrescodb.beans.LargeTransactionBean;
import com.alfresco.support.alfrescodb.beans.LockedResourcesBean;
import com.alfresco.support.alfrescodb.beans.NodeContentTypeBean;
import com.alfresco.support.alfrescodb.beans.NodeContentTypeMonthBean;
import com.alfresco.support.alfrescodb.beans.NodeMimeTypeBean;
import com.alfresco.support.alfrescodb.beans.NodeStoreBean;
import com.alfresco.support.alfrescodb.beans.WorkflowBean;
import com.alfresco.support.alfrescodb.config.DbQueriesProperties;

public class ExportComponent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DbQueriesProperties appProperties;

    @Autowired
    private DAOMapper exportMapper;

    public void exportReport(Model model) {

        logger.debug("Full Export started. Export Type: " + appProperties.getReportExportType()
                + "; Export folder: " + appProperties.getReportExportFolder());

        try {
            checkAndCreateFolder(appProperties.getReportExportFolder());
            List<String> generatedFiles = new ArrayList<String>();

            /* Applied Patches */
            List<AppliedPatchesBean> listAppliedPatches = exportMapper.listAppliedPatches();
            generatedFiles.add(this.exportToFile(listAppliedPatches, "listAppliedPatches"));

            /* DBSize */
            if ("postgres".equalsIgnoreCase(appProperties.getDbType())) {
                List<DbPostgresBean> listDbPostgres = exportMapper.findTablesInfoPostgres();
                generatedFiles.add(this.exportToFile(listDbPostgres, "listDbPostgres"));
            } else if ("mysql".equalsIgnoreCase(appProperties.getDbType())) {
                List<DbMySQLBean> listDbMySQL = exportMapper.findTablesInfoMysql();
                generatedFiles.add(this.exportToFile(listDbMySQL, "listDbMySQL"));
            } else if ("oracle".equalsIgnoreCase(appProperties.getDbType())) {
                List<DbOracleTableBean> listDbOracleTables = exportMapper.findTablesInfoOracle();
                generatedFiles.add(this.exportToFile(listDbOracleTables, "listDbOracleTables"));
                List<DbOracleIndexBean> listDbOracleIndexes = exportMapper.findIndexesInfoOracle();
                generatedFiles.add(this.exportToFile(listDbOracleIndexes, "listDbOracleIndexes"));
            } else if ("microsoft".equalsIgnoreCase(appProperties.getDbType())) {
                // XXX TODO
            }

            /* Large Folders */
            List<LargeFolderBean> largeFolders = exportMapper.findLargeFolders(appProperties.getLargeFolderSizeThreshold());
            generatedFiles.add(this.exportToFile(largeFolders, "largeFolders"));

            /* Large Transactions */
            List<LargeTransactionBean> largeTransaction = exportMapper.findLargeTransactions(appProperties.getLargeTransactionSizeThreshold());
            generatedFiles.add(this.exportToFile(largeTransaction, "largeTransaction"));

            /* ACLs */
            List<AccessControlBean> listACLs = exportMapper.findAccessControlListEntries();
            generatedFiles.add(this.exportToFile(listACLs, "findAccessControlListEntries"));
            listACLs = exportMapper.findAclTypesRepartition();
            generatedFiles.add(this.exportToFile(listACLs, "findAclTypesRepartition"));
            listACLs = exportMapper.findAclsHeight();
            generatedFiles.add(this.exportToFile(listACLs, "findAclsHeight"));
            if ("oracle".equalsIgnoreCase(appProperties.getDbType())) {
                listACLs = exportMapper.findACLNodeRepartitionOracle();
            } else if ("microsoft".equalsIgnoreCase(appProperties.getDbType())) {
                listACLs = exportMapper.findACLNodeRepartitionMSSql();
            } else {
                listACLs = exportMapper.findACLNodeRepartition();
            }
            generatedFiles.add(this.exportToFile(listACLs, "findACLNodeRepartition"));
            if ("oracle".equalsIgnoreCase(appProperties.getDbType())) {
                listACLs = exportMapper.findACEAuthoritiesOracle();
            } else if ("microsoft".equalsIgnoreCase(appProperties.getDbType())) {
                listACLs = exportMapper.findACEAuthoritiesMSSql();
            } else {
                listACLs = exportMapper.findACEAuthorities();
            }
            generatedFiles.add(this.exportToFile(listACLs, "findACEAuthorities"));
            // XXX Those? One per file?
            String totAcls = exportMapper.countTotalAcls();
            generatedFiles.add(this.exportToFile(totAcls, "totAcls"));
            String totAclsOrphaned = exportMapper.countTotalOrphanedAcls();
            generatedFiles.add(this.exportToFile(totAclsOrphaned, "totAclsOrphaned"));

            /* Content Model */
            List<ContentModelBean> contentModelBean = exportMapper.listContentModels();
            generatedFiles.add(this.exportToFile(contentModelBean, "contentModel"));

            /* Activities Feed */
            List<ActivitiesFeedByTypeBean> activitiesFeedTypeBean = exportMapper.listActivitiesByActivityType();
            generatedFiles.add(this.exportToFile(activitiesFeedTypeBean, "activitiesFeedByType"));

            List<ActivitiesFeedByUserBean> activitiesFeedByUserBean = exportMapper.listActivitiesByUser();
            generatedFiles.add(this.exportToFile(activitiesFeedByUserBean, "activitiesFeedByUser"));

            List<ActivitiesFeedByApplication> activitiesFeedByApplication = exportMapper.listActivitiesByApplication();
            generatedFiles.add(this.exportToFile(activitiesFeedByApplication, "activitiesFeedByApplication"));

            /* Archived nodes */
            List<ArchivedNodesBean> archivedNodesByUser = exportMapper.listArchivedNodesByUser();
            generatedFiles.add(this.exportToFile(archivedNodesByUser, "archivedNodesByUser"));

            String totArchivedNodes = exportMapper.countTotalArchivedNodes();
            generatedFiles.add(this.exportToFile(totArchivedNodes, "totArchivedNodes"));

            /* Locked Resources */
            List<LockedResourcesBean> lockedResources = exportMapper.lockedResources();
            generatedFiles.add(this.exportToFile(lockedResources, "lockedResources"));

            /* Users and Groups */
            String countTotalUsers = exportMapper.countTotalUsers();
            generatedFiles.add(this.exportToFile(countTotalUsers, "countTotalUsers"));

            if (appProperties.getIsEnterpriseVersion()) {
                String countAuthorizedUsers = "";
                if ("oracle".equalsIgnoreCase(appProperties.getDbType())) {
                    countAuthorizedUsers = exportMapper.countAuthorizedUsersOracle();
                } else {
                    countAuthorizedUsers = exportMapper.countAuthorizedUsers();
                }                
                generatedFiles.add(this.exportToFile(countAuthorizedUsers, "countAuthorizedUsers"));
            }

            String countGroups = exportMapper.countGroups();
            generatedFiles.add(this.exportToFile(countGroups, "countGroups"));

            /* Workflows */
            List<WorkflowBean> workflowProcessAndTasks = exportMapper.listWorkflowsWithProcessesAndTasks();
            generatedFiles.add(this.exportToFile(workflowProcessAndTasks, "workflowProcessAndTasks"));

            List<WorkflowBean> openWorkflows = exportMapper.listOpenWorkflows();
            generatedFiles.add(this.exportToFile(openWorkflows, "openWorkflows"));

            List<WorkflowBean> closedWorkflows = exportMapper.listClosedWorkflows();
            generatedFiles.add(this.exportToFile(closedWorkflows, "closedWorkflows"));

            List<WorkflowBean> openTasks = exportMapper.listOpenTasks();
            generatedFiles.add(this.exportToFile(openTasks, "openTasks"));

            List<WorkflowBean> closedTasks = exportMapper.listClosedTasks();
            generatedFiles.add(this.exportToFile(closedTasks, "closedTasks"));

            /* Nodes */
            List<NodeStoreBean> nodeStoreBeans = exportMapper.listNodesByStore();
            generatedFiles.add(this.exportToFile(nodeStoreBeans, "nodeStoreBeans"));

            List<NodeMimeTypeBean> activeNodesByMimeType = exportMapper.listActiveNodesByMimetype();
            generatedFiles.add(this.exportToFile(activeNodesByMimeType, "activeNodesByMimeType"));

            List<NodeContentTypeMonthBean> activeNodeContentTypeMonths = exportMapper.listActiveNodesByContentTypeAndMonth();
            generatedFiles.add(this.exportToFile(activeNodeContentTypeMonths, "activeNodeContentTypeMonths"));

            List<NodeContentTypeBean> activeNodeContentTypeBeans = exportMapper.listActiveNodesByContentType();
            generatedFiles.add(this.exportToFile(activeNodeContentTypeBeans, "activeNodeContentTypeBeans"));


            model.addAttribute("generatedFiles", generatedFiles);
        } catch (IOException ioex) {
            logger.error("Exception", ioex);
        } finally {
            logger.debug("Full Export completed!");
        }
    }

    private String exportToFile(Object o, String filename) throws IOException {
        String generatedFile = appProperties.getReportExportFolder() + "/" + filename + "." + appProperties.getReportExportType();
        ObjectSerializer.serialize(o, generatedFile, appProperties.getReportExportType());
        return generatedFile;
    }

    /**
     * Checks if the given path represents an existing folder.
     * If it does not exist, creates the folder.
     *
     * @param folderPath the path to the folder
     * @return true if the folder exists or was created successfully, false
     *         otherwise
     */
    public boolean checkAndCreateFolder(String folderPath) throws IOException {
        File folder = new File(folderPath);

        // Check if the path exists and is a directory
        if (folder.exists()) {
            if (folder.isDirectory()) {
                return true;
            } else {
                throw new IllegalArgumentException("The path exists but is not a directory: " + folderPath);
            }
        }

        // Attempt to create the directory
        boolean isCreated = folder.mkdirs();
        if (isCreated) {
            logger.trace("Folder created successfully: " + folderPath);
        } else {
            throw new IOException("Failed to create folder: " + folderPath);
        }
        return isCreated;
    }
}
