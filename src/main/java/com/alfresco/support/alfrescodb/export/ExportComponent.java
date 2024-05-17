package com.alfresco.support.alfrescodb.export;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import com.alfresco.support.alfrescodb.export.beans.AccessControlBean;
import com.alfresco.support.alfrescodb.export.beans.ActivitiesFeedByApplication;
import com.alfresco.support.alfrescodb.export.beans.ActivitiesFeedByTypeBean;
import com.alfresco.support.alfrescodb.export.beans.ActivitiesFeedByUser;
import com.alfresco.support.alfrescodb.export.beans.AppliedPatchesBean;
import com.alfresco.support.alfrescodb.export.beans.ArchivedNodesBean;
import com.alfresco.support.alfrescodb.export.beans.ContentModelBean;
import com.alfresco.support.alfrescodb.export.beans.DbMySQLBean;
import com.alfresco.support.alfrescodb.export.beans.DbPostgresBean;
import com.alfresco.support.alfrescodb.export.beans.LargeFolderBean;
import com.alfresco.support.alfrescodb.export.beans.LargeTransactionBean;
import com.alfresco.support.alfrescodb.export.beans.LockedResourcesBean;

public class ExportComponent {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${reportFile}")
    private String reportFile;

    @Value("${reportSplit}")
    private boolean multiReportFile;

    @Value("${reportExportFolder}")
    private String reportExportFolder;

    @Value("${reportExportType}")
    private String reportExportType;

    @Value("${spring.datasource.platform}")
    private String dbType;

    @Value("${alf_auth_status}")
    private Boolean isEnterpriseVersion;

    @Value("${largeFolderSize}")
    private Integer largeFolderSize;

    @Value("${largeTransactionSize}")
    private Integer largeTransactionSize;

    @Autowired
    private ExportMapper exportMapper;

    public void exportReport(Model model) {

        logger.debug("Full Export started. Multifile: " + multiReportFile + "; Export Type: " + reportExportType
                + "; Export folder: " + reportExportFolder);

        try {
            checkAndCreateFolder(this.reportExportFolder);
            List<String> generatedFiles = new ArrayList<String>();

            /* Applied Patches */
            List<AppliedPatchesBean> listAppliedPatches = exportMapper.listAppliedPatches();
            generatedFiles.add(this.exportToFile(listAppliedPatches, "listAppliedPatches"));

            /* DBSize */
            if ("postgres".equalsIgnoreCase(dbType)) {
                List<DbPostgresBean> listDbPostgres = exportMapper.findTablesInfoPostgres();
                generatedFiles.add(this.exportToFile(listDbPostgres, "listDbPostgres"));
            } else if ("mysql".equalsIgnoreCase(dbType)) {
                List<DbMySQLBean> listDbMySQL = exportMapper.findTablesInfoMysql();
                generatedFiles.add(this.exportToFile(listDbMySQL, "listDbMySQL"));
            } else if ("oracle".equalsIgnoreCase(dbType)) {
                // XXX TODO
            } else if ("microsoft".equalsIgnoreCase(dbType)) {
                // XXX TODO
            }

            /* Large Folders */
            List<LargeFolderBean> largeFolders = exportMapper.findLargeFolders(largeFolderSize);
            generatedFiles.add(this.exportToFile(largeFolders, "largeFolders"));

            /* Large Transactions */
            List<LargeTransactionBean> largeTransaction = exportMapper.findLargeTransactions(largeTransactionSize);
            generatedFiles.add(this.exportToFile(largeTransaction, "largeTransaction"));

            /* ACLs */
            List<AccessControlBean> listACLs = exportMapper.findAccessControlListEntries();
            generatedFiles.add(this.exportToFile(listACLs, "findAccessControlListEntries"));
            listACLs = exportMapper.findAclTypesRepartition();
            generatedFiles.add(this.exportToFile(listACLs, "findAclTypesRepartition"));
            listACLs = exportMapper.findAclsHeight();
            generatedFiles.add(this.exportToFile(listACLs, "findAclsHeight"));
            if ("oracle".equalsIgnoreCase(dbType)) {
                listACLs = exportMapper.findACLNodeRepartitionOracle();
            } else if ("microsoft".equalsIgnoreCase(dbType)) {
                listACLs = exportMapper.findACLNodeRepartitionMSSql();
            } else {
                listACLs = exportMapper.findACLNodeRepartition();
            }
            generatedFiles.add(this.exportToFile(listACLs, "findACLNodeRepartition"));
            if ("oracle".equalsIgnoreCase(dbType)) {
                listACLs = exportMapper.findACEAuthoritiesOracle();
            } else if ("microsoft".equalsIgnoreCase(dbType)) {
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

            List<ActivitiesFeedByUser> activitiesFeedByUser = exportMapper.listActivitiesByUser();
            generatedFiles.add(this.exportToFile(activitiesFeedByUser, "activitiesFeedByUser"));

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

            if (this.isEnterpriseVersion) {
                String countAuthorizedUsers = exportMapper.countAuthorizedUsers();
                generatedFiles.add(this.exportToFile(countAuthorizedUsers, "countAuthorizedUsers"));
            }

            String countGroups = exportMapper.countGroups();
            generatedFiles.add(this.exportToFile(countGroups, "countGroups"));

            model.addAttribute("generatedFiles", generatedFiles);
        } catch (IOException ioex) {
            logger.error("Exception", ioex);
        } finally {
            logger.debug("Full Export completed!");
        }
    }

    private String exportToFile(Object o, String filename) throws IOException {
        String generatedFile = this.reportExportFolder + "/" + filename + "." + this.reportExportType;
        ObjectSerializer.serialize(o, generatedFile, this.reportExportType);
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
