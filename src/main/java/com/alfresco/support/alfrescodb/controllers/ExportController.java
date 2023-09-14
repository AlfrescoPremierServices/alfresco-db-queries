package com.alfresco.support.alfrescodb.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import com.alfresco.support.alfrescodb.helpers.SolrMemoryHelper;
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

    public static final String EXPORT_CSV = "csv";
    public static final String EXPORT_TXT = "txt";
    public static final String EXPORT_JSON = "json";

    @Autowired
    SqlMapperController sqlMapper;

    @Autowired
    SolrMemory solrMemory;

    @Autowired
    SolrMemoryHelper solrMemoryHelper;

    @Value("${reportFile}")
    private String reportFile;

    @Value("${reportSplit}")
    private boolean multiReportFile;

    @Value("${reportExportType}")
    private String reportExportType;

    @Value("${spring.datasource.platform}")
    private String dbType;

    @Value("${alf_auth_status}")
    private Boolean alfAuthStatus;

    private BufferedWriter out;

    public void exportReport(Model model) {
        String[] headers = { "" };
        String outputFile = this.reportFile;

        checkExportType();

        try {
            logger.debug("Full Export started. Multifile: " + multiReportFile + "; Export Type: " + reportExportType);
            List<String> generatedFiles = new ArrayList<String>();
            if (multiReportFile) {
                outputFile = outputFile.substring(0, outputFile.length() - 4); // removing extension e.g.: ".csv"
            } else {
                out = this.prepareOutputFile(outputFile);
                generatedFiles.add(outputFile);
                if (EXPORT_JSON.equals(this.reportExportType)) {
                    this.writeLine(out, "{ \"" + outputFile + "\": [");
                }
            }
            // DB Size
            if (dbType.equalsIgnoreCase("mysql") || dbType.equalsIgnoreCase("postgres")) {
                headers = new String[] { "Table Schema", "Table Name", "Total Size", "Row Estimate", "Table Size",
                        "Index Size" };
            } else if (dbType.equalsIgnoreCase("oracle")) {
                headers = new String[] { "Table Name", "Size" };
            } else if (dbType.equalsIgnoreCase("microsoft")) {
                headers = new String[] { "Table Schema", "Table Name", "Rows Count", "Total Space", "Used Space",
                        "Unused Space" };
            }

            generatedFiles.add(this.prepareOutputFile(outputFile + "_DBSize", headers, true));
            this.writeDBTableSize(out);

            // DB Size - Indexes
            if (dbType.equalsIgnoreCase("mysql") || dbType.equalsIgnoreCase("postgres")) {
                // NOP
            } else {
                if (dbType.equalsIgnoreCase("oracle")) {
                    headers = new String[] { "Table Name", "Index Name", "Index Size" };
                } else if (dbType.equalsIgnoreCase("microsoft")) {
                    headers = new String[] { "Table Schema", "Table Name", "Index Name", "Index Size" };
                }

                generatedFiles.add(this.prepareOutputFile(outputFile + "_DBSizeIndex", headers));
                this.writeDBTableSize2(out);
            }

            // Large folders
            headers = new String[] { "Folder Name", "Node Reference", "Type", "No. of Child Nodes" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_LargeFolder", headers));
            this.writeLargeFolder(out);

            // Large Transactions
            headers = new String[] { "Large Transaction Id", "Nodes Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_LargeTransaction", headers));
            this.writeLargeTransaction(out);

            // Access Control List 1 - Nodes repartition
            headers = new String[] { "ACL ID", "Nodes" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_AccessControlList1", headers));
            this.writeLargeACL1(out);

            // Access Control List 2 - Type repartition
            headers = new String[] { "ACL type", "Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_AccessControlList2", headers));
            this.writeLargeACL2(out);

            // Access Control List 3 - Number of ACEs in ACLs
            headers = new String[] { "ACL ID", "ACE count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_AccessControlList3", headers));
            this.writeLargeACL3(out);

            // Access Control List 4 -
            headers = new String[] { "ACE Permission", "Occurrences" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_AccessControlList4", headers));
            this.writeLargeACL4(out);

            // Access Control List 5 - Authorities & ACEs
            headers = new String[] { "Authority hash", "ACEs" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_AccessControlList5", headers));
            this.writeLargeACL5(out);

            // NEW ACL/ACE Totals
            headers = new String[] { "Access Control List Size", "Orphaned ACLs", "Access Control List Entries" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_AccessControlListTotals", headers));
            this.writeACLTotals(out);

            // Content Model URIs
            headers = new String[] { "Content Model URI", "Property" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_ContentModelProperties", headers));
            this.writeContentModelProps(out);

            // Activities 1 - Activities by Type
            headers = new String[] { "Date", "Site Network", "Activity Type", "Activities Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Activities_by_type", headers));
            this.writeActivities1(out);

            // Activities 2 - Activities by User
            headers = new String[] { "Date", "Site Network", "User Id", "Activities Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Activities_by_user", headers));
            this.writeActivities2(out);

            // Activities 3 - Activities by Application Interface
            headers = new String[] { "Date", "Site Network", "Application Interface", "Activities Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Activities_by_API", headers));
            this.writeActivities3(out);

            // Workflows 1 - All Workflows Grouped by Process Definition and Task Name
            headers = new String[] { "Process Definition", "Task Name", "No Occurrences" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Workflows_by_proc_and_taskname", headers));
            this.writeWorkflows1(out);

            // Workflows 2 - Open Workflows
            headers = new String[] { "Process Definition", "No Occurrences" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Workflows_open", headers));
            this.writeWorkflows2(out);

            // Workflows 3 - Closed Workflows
            headers = new String[] { "Process Definition", "No Occurrences" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Workflows_closed", headers));
            this.writeWorkflows3(out);

            // Workflows 4 - Open tasks
            headers = new String[] { "Process Definition", "Task Name", "No Occurrences" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Workflows_task_open", headers));
            this.writeWorkflows4(out);

            // Workflows 5 - Closed Tasks
            headers = new String[] { "Process Definition", "Task Name", "No Occurrences" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Workflows_task_closed", headers));
            this.writeWorkflows5(out);

            // Archived Nodes 1 - All archived
            headers = new String[] { "All Archived Nodes" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_ArchivedNodes_all", headers));
            this.writeArchivedNodes1(out);

            // Archived Nodes 2 - Archived by user
            headers = new String[] { "Archived Nodes", "User" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_ArchivedNodes_by_user", headers));
            this.writeArchivedNodes2(out);

            // List Nodes by Mimetype
            headers = new String[] { "Mime Type", "Nodes Count", "Disk Space" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_NodesByMimetype", headers));
            this.writeNodesByMimetype(out);

            // List Nodes by Content Type
            headers = new String[] { "Node Type", "Nodes Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_NodesByContentType", headers));
            this.writeNodesByContentType(out);

            // List Nodes by Content Type per month
            headers = new String[] { "Date", "Node Type", "Nodes Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_NodesByContentTypeAndMonth", headers));
            this.writeNodesByContentTypeAndMonth(out);

            // List Nodes by Store
            headers = new String[] { "Store", "Nodes Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_NodesByStore", headers));
            this.writeNodesByStore(out);

            // Resource Locking
            headers = new String[] { "Ide", " Lock Token", " Start Time", " Expiry Time", " Shared Resource",
                    " Exclusive Resource", " URI" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_ResourceLocking", headers));
            this.writeResouceLocking(out);

            // Authorities 1 - Users Count
            headers = new String[] { "Users Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Authorities_users_count", headers));
            this.writeAuthorities1(out);

            // Authorities 2 - Authorized Users Count
            headers = new String[] { "Authorized Users Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Authorities_users_authorized", headers));
            this.writeAuthorities2(out);

            // Authorities 3 - Groups count
            headers = new String[] { "Groups Count" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_Authorities_groups_count", headers));
            this.writeAuthorities3(out);

            // JMX Properties
            headers = new String[] { "JMX Property Name", "Property Value set on DB" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_JMXProperties", headers));
            this.writeJmxProps(out);

            // Applied Patches
            headers = new String[] { "Id", "Applied to Schema", "Applied on Date", "Applied to Server", "Was Executed",
                    "Succeeded", "Report" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_AppliedPatches", headers));
            this.writeAppliedPatches(out);

            // Solr 1 - Summary
            headers = new String[] { "Alfresco Nodes", "Archive Nodes", "Transactions", "ACLs", "ACL Transactions" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_SOLR_Summary", headers));
            this.writeSolrMemory1(out);

            // Solr 2 - Alfresco Core Details
            headers = new String[] { "Alfresco Core Query Result Cache Size", "Alfresco Core Authority Cache Size", "Alfresco Core Path Cache Size", "Alfresco Core Filter Cache Size" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_SOLR_Core_Alfresco", headers));
            this.writeSolrMemory2(out);

            // Solr 3 - Archive Core Details
            headers = new String[] { "Archive Core Query Result Cache Size", "Archive Core Authority Cache Size", "Archive Core Path Cache Size", "Archive Core Filter Cache Size" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_SOLR_Core_Archive", headers));
            this.writeSolrMemory3(out);

            // Solr 4 - Memory Data Structures
            headers = new String[] { "Alfresco Core Data Structures Memory", "Archive Core Data Structures Memory", "Total Solr Data Structures Memory" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_SOLR_Data_Memory", headers));
            this.writeSolrMemory4(out);

            // Solr 5 -
            headers = new String[] { "Alfresco Core Cache Memory", "Archive Core Cache Memory", "Total Solr Cache Memory" };
            generatedFiles.add(this.prepareOutputFile(outputFile + "_SOLR_Caches_Memory", headers));
            this.writeSolrMemory5(out);

            model.addAttribute("generatedFiles", generatedFiles);

            if (EXPORT_JSON.equals(this.reportExportType)) {
                if (!multiReportFile) {
                    this.writeLine(out, "]}"); // Close the single file
                }
                this.writeLine(out, "]}");
            }

            out.close();
            logger.debug("Full Export completed!");
        } catch (IOException e) {
            System.out.println("Exception ");
            e.printStackTrace();
        }
    }

    /*
     * Utlity to check the validity of reportExportType paramater. If the value is
     * invalid is reset to EXPORT_TXT
     */
    private void checkExportType() {
        this.reportExportType = this.reportExportType.toLowerCase();

        if (EXPORT_CSV.equals(this.reportExportType) || EXPORT_JSON.equals(this.reportExportType)
                || EXPORT_TXT.equals(this.reportExportType)) {
            logger.trace("reportExportType has a valid value");
        } else {
            this.reportExportType = EXPORT_TXT;
            logger.warn("reportExportType has a invalid value, reset to default txt");
        }
    }

    /* Utility to centralize the creation of new output file */
    private BufferedWriter prepareOutputFile(String name) throws IOException {
        logger.debug("Creating new output file: " + name);
        try {
            this.out = new BufferedWriter(new FileWriter(name));
            return out;
        } catch (IOException ioex) {
            logger.error("Exception creating output file: " + name, ioex);
            throw new IOException("Exception creating output file: " + name, ioex);
        }
    }

    private String prepareOutputFile(String name, String[] headers) throws IOException {
        return this.prepareOutputFile(name, headers, false);
    }
    /* Utility to centralize the creation of new output file. */
    private String prepareOutputFile(String name, String[] headers, boolean dontClosePrevious) throws IOException {
        String outputFileName = name + "." + this.reportExportType;

        // Close the previous JSon file if necessary
        if (EXPORT_JSON.equals(this.reportExportType) && out != null && !dontClosePrevious) {
            this.writeLine(out, "]}"); // Close the previous Json file
        }

        // Check if you need to open a new file
        if (multiReportFile) {
            if (out != null) {
                out.close();
            }
            out = this.prepareOutputFile(outputFileName);
        } else {
            if (EXPORT_TXT.equals(this.reportExportType)) {
                this.writeLine(out, "\n\n");
            } else if (EXPORT_CSV.equals(this.reportExportType)) {
                this.writeLine(out, "\n\n");
            } else if (!dontClosePrevious) {
                this.writeLine(out, ",");
            }
            outputFileName = null;
        }

        // Calculate the Header section to write
        String headerString = "";
        if (EXPORT_CSV.equals(this.reportExportType)) {
            headerString = headers[0];
            for (int i = 1; i < headers.length; i++) {
                headerString += "," + headers[i];
            }
        } else if (EXPORT_TXT.equals(this.reportExportType)) {
            headerString = headers[0];
            for (int i = 1; i < headers.length; i++) {
                headerString += ", " + headers[i];
            }
        } else {
            headerString = "{ \"" + name + "\": [";
        }
        // Write the headers
        writeLine(out, headerString);

        // Return the filename generated
        return outputFileName;
    }

    /* This is the only method that actually write to the file provided */
    private void writeLine(BufferedWriter out, String str) {
        try {
            logger.trace("Writing to file: " + str);
            out.write(str);
        } catch (IOException ioex) {
            logger.error("Exception while writing to file", ioex);
            ioex.printStackTrace();
        }
    }

    /* Utility to complete the line written on filw */
    private void writeEndLine(int actualIndex, int size) {
        if ((actualIndex < size - 1) && EXPORT_JSON.equals(this.reportExportType)) {
            try {
                out.write(",");
            } catch (IOException ioex) {
                logger.error("Exception while writing to file", ioex);
                ioex.printStackTrace();
            }
        }
    }

    private void writeDBTableSize(BufferedWriter out) {
        List<RelationInfo> listRelationInfos = sqlMapper.findTablesInfo();

        if (dbType.equalsIgnoreCase("mysql") || dbType.equalsIgnoreCase("postgres")) {
            for (int i = 0; i < listRelationInfos.size(); i++) {
                this.writeLine(out, listRelationInfos.get(i).printDbInfo(this.reportExportType));
                this.writeEndLine(i, listRelationInfos.size());
            }
        } else if (dbType.equalsIgnoreCase("oracle")) {
            List<OracleRelationInfo> oracleListRelationInfos = sqlMapper.findTablesInfo();
            for (int i = 0; i < oracleListRelationInfos.size(); i++) {
                this.writeLine(out, oracleListRelationInfos.get(i).printTableInfo(this.reportExportType));
                this.writeEndLine(i, oracleListRelationInfos.size());
            }
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            List<MSSqlRelationInfo> mSSqlListRelationInfos = sqlMapper.findTablesInfo();
            for (int i = 0; i < listRelationInfos.size(); i++) {
                this.writeLine(out, mSSqlListRelationInfos.get(i).printTableInfo(this.reportExportType));
                this.writeEndLine(i, mSSqlListRelationInfos.size());
            }
        }
    }

    private void writeDBTableSize2(BufferedWriter out) {
        // Database Size
        if (dbType.equalsIgnoreCase("mysql") || dbType.equalsIgnoreCase("postgres")) {

        } else if (dbType.equalsIgnoreCase("oracle")) {
            List<OracleRelationInfo> oracleListIndexesInfos = sqlMapper.findIndexesInfoOracle();

            for (int i = 0; i < oracleListIndexesInfos.size(); i++) {
                this.writeLine(out, oracleListIndexesInfos.get(i).printIndexInfo(this.reportExportType));
                this.writeEndLine(i, oracleListIndexesInfos.size());
            }
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            List<MSSqlRelationInfo> mSSqlListIndexesInfos = sqlMapper.findIndexesInfoMSSql();

            for (int i = 0; i < mSSqlListIndexesInfos.size(); i++) {
                this.writeLine(out, mSSqlListIndexesInfos.get(i).printIndexInfo(this.reportExportType));
                this.writeEndLine(i, mSSqlListIndexesInfos.size());
            }
        }
    }

    private void writeLargeFolder(BufferedWriter out) {
        List<LargeFolder> listLargeFolders = sqlMapper.findLargeFolders();
        if (listLargeFolders != null) {
            for (int i = 0; i < listLargeFolders.size(); i++) {
                this.writeLine(out, listLargeFolders.get(i).printLargeFolders(this.reportExportType));
                this.writeEndLine(i, listLargeFolders.size());
            }
        }
    }

    private void writeLargeTransaction(BufferedWriter out) {
        List<LargeTransaction> listLargeTransactions = sqlMapper.findLargeTransactions();
        if (listLargeTransactions != null) {
            for (int i = 0; i < listLargeTransactions.size(); i++) {
                this.writeLine(out, listLargeTransactions.get(i).printLargeTransactions(this.reportExportType));
                this.writeEndLine(i, listLargeTransactions.size());
            }
        }
    }

    /* This collects some data and print them together */
    private void writeACLTotals(BufferedWriter out) {

        String aclSize = sqlMapper.findAccessControlList();
        String orphanedAcls = sqlMapper.findOrphanedAcls();

        Integer aceSize = 0; // This need some calculation
        List<AccessControlList> listAccessControlListEntries = sqlMapper.findAccessControlListEntries();
        for (int i = 0; i < listAccessControlListEntries.size(); i++) {
            Integer count = Integer.valueOf(listAccessControlListEntries.get(i).getPermissionCount());
            aceSize = aceSize + count;
        }

        String res = null;
        if (EXPORT_CSV.equals(this.reportExportType)) {
            res = String.format("\n%s,%s,%s", aclSize, orphanedAcls, aceSize);
        } else if (EXPORT_JSON.equals(this.reportExportType)) {
            res = String.format("\n{\"aclSize\":\"%s\", \"orphanedAcls\":\"%s\", \"aceSize\":\"%s\"}", aclSize,
                    orphanedAcls, aceSize);
        } else { // Default TXT
            res = String.format("\n%s, %s, %s", aclSize, orphanedAcls, aceSize);
        }

        this.writeLine(out, res);
    }

    private void writeLargeACL1(BufferedWriter out) {
        List<AccessControlList> aclNodeRepartition = sqlMapper.findACLNodeRepartition();
        if (aclNodeRepartition != null) {
            for (int i = 0; i < aclNodeRepartition.size(); i++) {
                this.writeLine(out, aclNodeRepartition.get(i).printAclNode(this.reportExportType));
                this.writeEndLine(i, aclNodeRepartition.size());
            }
        }
    }

    private void writeLargeACL2(BufferedWriter out) {
        List<AccessControlList> aclTypeRepartition = sqlMapper.findAclTypeRepartition();
        if (aclTypeRepartition != null) {
            for (int i = 0; i < aclTypeRepartition.size(); i++) {
                this.writeLine(out, aclTypeRepartition.get(i).printAclType(this.reportExportType));
                this.writeEndLine(i, aclTypeRepartition.size());
            }
        }
    }

    private void writeLargeACL3(BufferedWriter out) {
        List<AccessControlList> aclsHeight = sqlMapper.findAclsHeight();
        for (int i = 0; i < aclsHeight.size(); i++) {
            this.writeLine(out, aclsHeight.get(i).printAclHeight(this.reportExportType));
            this.writeEndLine(i, aclsHeight.size());
        }

    }

    private void writeLargeACL4(BufferedWriter out) {
        List<AccessControlList> listAccessControlListEntries = sqlMapper.findAccessControlListEntries();

        if (listAccessControlListEntries != null) {
            for (int i = 0; i < listAccessControlListEntries.size(); i++) {
                this.writeLine(out,
                        listAccessControlListEntries.get(i).printAccessControlListEntries(this.reportExportType));
                this.writeEndLine(i, listAccessControlListEntries.size());
            }
        }
    }

    private void writeLargeACL5(BufferedWriter out) {
        List<AccessControlList> aceAuthorities = sqlMapper.findACEAuthorities();
        if (aceAuthorities != null) {
            for (int i = 0; i < aceAuthorities.size(); i++) {
                this.writeLine(out, aceAuthorities.get(i).printAuthorityAce(this.reportExportType));
                this.writeEndLine(i, aceAuthorities.size());
            }
        }
    }

    private void writeContentModelProps(BufferedWriter out) {
        List<ContentModelProperties> listContentModelProperties = sqlMapper.findContentModelProperties();
        if (listContentModelProperties != null) {
            for (int i = 0; i < listContentModelProperties.size(); i++) {
                this.writeLine(out,
                        listContentModelProperties.get(i).printContentModelProperties(this.reportExportType));
                this.writeEndLine(i, listContentModelProperties.size());
            }
        }
    }

    private void writeActivities1(BufferedWriter out) {
        List<ActivitiesFeed> listActivitiesFeed = sqlMapper.findActivitiesByActivityType();
        if (listActivitiesFeed != null) {
            for (int i = 0; i < listActivitiesFeed.size(); i++) {
                this.writeLine(out, listActivitiesFeed.get(i).printActivitiesByActivityType(this.reportExportType));
                this.writeEndLine(i, listActivitiesFeed.size());
            }
        }
    }

    private void writeActivities2(BufferedWriter out) {
        List<ActivitiesFeed> listActivitiesFeed = sqlMapper.findActivitiesByUser();
        if (listActivitiesFeed != null) {
            for (int i = 0; i < listActivitiesFeed.size(); i++) {
                this.writeLine(out, listActivitiesFeed.get(i).printActivitiesByUser(this.reportExportType));
                this.writeEndLine(i, listActivitiesFeed.size());
            }
        }

    }

    private void writeActivities3(BufferedWriter out) {
        List<ActivitiesFeed> listActivitiesFeed = sqlMapper.findActivitiesByApplicationInterface();
        if (listActivitiesFeed != null) {
            for (int i = 0; i < listActivitiesFeed.size(); i++) {
                this.writeLine(out, listActivitiesFeed.get(i).printActivitiesByInterface(this.reportExportType));
                this.writeEndLine(i, listActivitiesFeed.size());
            }
        }
    }

    private void writeWorkflows1(BufferedWriter out) {
        List<Workflow> listWorkflows = sqlMapper.findAllWorkflows();
        if (listWorkflows != null) {
            for (int i = 0; i < listWorkflows.size(); i++) {
                this.writeLine(out, listWorkflows.get(i).printTasks(this.reportExportType));
                this.writeEndLine(i, listWorkflows.size());
            }
        }
    }

    private void writeWorkflows2(BufferedWriter out) {
        List<Workflow> listOpenWorkflows = sqlMapper.openWorkflows();
        if (listOpenWorkflows != null) {
            for (int i = 0; i < listOpenWorkflows.size(); i++) {
                this.writeLine(out, listOpenWorkflows.get(i).printProcesses(this.reportExportType));
                this.writeEndLine(i, listOpenWorkflows.size());
            }
        }

    }

    private void writeWorkflows3(BufferedWriter out) {
        List<Workflow> listClosedWorkflows = sqlMapper.closedWorkflows();
        if (listClosedWorkflows != null) {
            for (int i = 0; i < listClosedWorkflows.size(); i++) {
                this.writeLine(out, listClosedWorkflows.get(i).printProcesses(this.reportExportType));
                this.writeEndLine(i, listClosedWorkflows.size());
            }
        }

    }

    private void writeWorkflows4(BufferedWriter out) {
        List<Workflow> listOpenTasks = sqlMapper.openTasks();
        if (listOpenTasks != null) {
            for (int i = 0; i < listOpenTasks.size(); i++) {
                this.writeLine(out, listOpenTasks.get(i).printTasks(this.reportExportType));
                this.writeEndLine(i, listOpenTasks.size());
            }
        }

    }

    private void writeWorkflows5(BufferedWriter out) {
        List<Workflow> listClosedTasks = sqlMapper.closedTasks();
        if (listClosedTasks != null) {
            for (int i = 0; i < listClosedTasks.size(); i++) {
                this.writeLine(out, listClosedTasks.get(i).printTasks(this.reportExportType));
                this.writeEndLine(i, listClosedTasks.size());
            }
        }
    }

    private void writeArchivedNodes1(BufferedWriter out) {
        List<ArchivedNodes> listArchivedNodes = sqlMapper.findArchivedNodes();
        if (listArchivedNodes != null) {
            for (int i = 0; i < listArchivedNodes.size(); i++) {
                this.writeLine(out, listArchivedNodes.get(i).printArchivedNodes(this.reportExportType));
                this.writeEndLine(i, listArchivedNodes.size());
            }
        }
    }

    private void writeArchivedNodes2(BufferedWriter out) {
        List<ArchivedNodes> listArchivedNodes = sqlMapper.findArchivedNodesByUser();
        if (listArchivedNodes != null) {
            for (int i = 0; i < listArchivedNodes.size(); i++) {
                this.writeLine(out, listArchivedNodes.get(i).printArchivedNodesByUser(this.reportExportType));
                this.writeEndLine(i, listArchivedNodes.size());
            }
        }
    }

    private void writeNodesByMimetype(BufferedWriter out) {
        List<NodesList> listNodesByMimeType = sqlMapper.findNodesSizeByMimeType();
        if (listNodesByMimeType != null) {
            for (int i = 0; i < listNodesByMimeType.size(); i++) {
                this.writeLine(out, listNodesByMimeType.get(i).printNodesByMimeType(this.reportExportType));
                this.writeEndLine(i, listNodesByMimeType.size());
            }
        }
    }

    private void writeNodesByContentType(BufferedWriter out) {
        List<NodesList> listNodesByType = sqlMapper.findNodesByContentType();
        if (listNodesByType != null) {
            for (int i = 0; i < listNodesByType.size(); i++) {
                this.writeLine(out, listNodesByType.get(i).printNodesByType(this.reportExportType));
                this.writeEndLine(i, listNodesByType.size());
            }
        }
    }

    private void writeNodesByContentTypeAndMonth(BufferedWriter out) {
        List<NodesList> listNodesByType = sqlMapper.findNodesByContentTypeAndMonth();
        if (listNodesByType != null) {
            for (int i = 0; i < listNodesByType.size(); i++) {
                this.writeLine(out, listNodesByType.get(i).printNodesByTypeAndMonth(this.reportExportType));
                this.writeEndLine(i, listNodesByType.size());
            }
        }
    }

    private void writeNodesByStore(BufferedWriter out) {
        List<NodesList> listNodesByStore = sqlMapper.findNodesByStore();
        if (listNodesByStore != null) {
            for (int i = 0; i < listNodesByStore.size(); i++) {
                this.writeLine(out, listNodesByStore.get(i).printNodesByStore(this.reportExportType));
                this.writeEndLine(i, listNodesByStore.size());
            }
        }
    }

    private void writeResouceLocking(BufferedWriter out) {
        List<LockedResources> listLockedResources = sqlMapper.findAllLockedResources();
        for (int i = 0; i < listLockedResources.size(); i++) {
            this.writeLine(out, listLockedResources.get(i).printAll(this.reportExportType));
            this.writeEndLine(i, listLockedResources.size());
        }
    }

    private void writeAuthorities1(BufferedWriter out) {
        List<Authority> listUsers = sqlMapper.findUsers();
        if (listUsers != null) {
            for (int i = 0; i < listUsers.size(); i++) {
                this.writeLine(out, listUsers.get(i).printUsers(this.reportExportType));
                this.writeEndLine(i, listUsers.size());
            }
        }
    }

    private void writeAuthorities2(BufferedWriter out) {
        if (alfAuthStatus == true) {
            List<Authority> listAuthorizedUsers = sqlMapper.findAuthorizedUsers();
            for (int i = 0; i < listAuthorizedUsers.size(); i++) {
                this.writeLine(out, listAuthorizedUsers.get(i).printUsers(this.reportExportType));
                this.writeEndLine(i, listAuthorizedUsers.size());
            }
        } else {
            logger.warn("ACS Community version: no Alf_auth_status table for exporting *_Authorities_users_authorized");
        }
    }

    private void writeAuthorities3(BufferedWriter out) {
        List<Authority> listGroups = sqlMapper.findGroups();
        if (listGroups != null) {
            for (int i = 0; i < listGroups.size(); i++) {
                this.writeLine(out, listGroups.get(i).printGroups(this.reportExportType));
                this.writeEndLine(i, listGroups.size());
            }
        }
    }

    private void writeJmxProps(BufferedWriter out) {
        List<JmxProperties> listJmxProperties = sqlMapper.findJmxProperties();
        if (listJmxProperties != null) {
            for (int i = 0; i < listJmxProperties.size(); i++) {
                this.writeLine(out, listJmxProperties.get(i).printJmxProperties(this.reportExportType));
                this.writeEndLine(i, listJmxProperties.size());
            }
        }
    }

    private void writeAppliedPatches(BufferedWriter out) {
        List<AppliedPatches> listAppliedPatches = sqlMapper.findAppliedPatches();
        if (listAppliedPatches != null) {
            for (int i = 0; i < listAppliedPatches.size(); i++) {
                this.writeLine(out, listAppliedPatches.get(i).printAppliedPatches(this.reportExportType));
                this.writeEndLine(i, listAppliedPatches.size());
            }
        }
    }

    private void writeSolrMemory1(BufferedWriter out) {
        List<SolrMemory> solrMemoryList = sqlMapper.solrMemory();

        for (int i = 0; i < solrMemoryList.size(); i++) {
            solrMemoryList.get(i).setSolrMemoryHelper(solrMemoryHelper);
            this.writeLine(out, solrMemoryList.get(i).printSolrTotals(this.reportExportType));
            this.writeEndLine(i, solrMemoryList.size());
        }
    }

    private void writeSolrMemory2(BufferedWriter out) {
        List<SolrMemory> solrMemoryList = sqlMapper.solrMemory();

        for (int i = 0; i < solrMemoryList.size(); i++) {
            solrMemoryList.get(i).setSolrMemoryHelper(solrMemoryHelper);
            this.writeLine(out, solrMemoryList.get(i).printAlfrescoCoreDetails(this.reportExportType));
            this.writeEndLine(i, solrMemoryList.size());
        }
    }

    private void writeSolrMemory3(BufferedWriter out) {
        List<SolrMemory> solrMemoryList = sqlMapper.solrMemory();

        for (int i = 0; i < solrMemoryList.size(); i++) {
            solrMemoryList.get(i).setSolrMemoryHelper(solrMemoryHelper);
            this.writeLine(out, solrMemoryList.get(i).printArchiveCoreDetails(this.reportExportType));
            this.writeEndLine(i, solrMemoryList.size());
        }
    }

    private void writeSolrMemory4(BufferedWriter out) {
        List<SolrMemory> solrMemoryList = sqlMapper.solrMemory();

        for (int i = 0; i < solrMemoryList.size(); i++) {
            solrMemoryList.get(i).setSolrMemoryHelper(solrMemoryHelper);
            this.writeLine(out, solrMemoryList.get(i).printMemoryDataStructures(this.reportExportType));
            this.writeEndLine(i, solrMemoryList.size());
        }
    }

    private void writeSolrMemory5(BufferedWriter out) {
        List<SolrMemory> solrMemoryList = sqlMapper.solrMemory();

        for (int i = 0; i < solrMemoryList.size(); i++) {
            solrMemoryList.get(i).setSolrMemoryHelper(solrMemoryHelper);
            this.writeLine(out, solrMemoryList.get(i).printMemoryCacheStructures(this.reportExportType));
            this.writeEndLine(i, solrMemoryList.size());
        }
    }
}
