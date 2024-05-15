package com.alfresco.support.alfrescodb.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import com.alfresco.support.alfrescodb.export.ExportMapper;
import com.alfresco.support.alfrescodb.export.ObjectSerializer;
import com.alfresco.support.alfrescodb.export.beans.AppliedPatchesBean;

public class ExportController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String EXPORT_CSV = "csv";
    public static final String EXPORT_JSON = "json";

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
    private Boolean alfAuthStatusExists;

    @Autowired
    private ExportMapper exportMapper;

    public void exportReport(Model model) {

        logger.debug("Full Export started. Multifile: " + multiReportFile + "; Export Type: " + reportExportType + "; Export folder: "+ reportExportFolder);

        try {
            checkAndCreateFolder(this.reportExportFolder);

            List <AppliedPatchesBean> listAppliedPatches = exportMapper.findAppliedPatches();
            ObjectSerializer.serialize(listAppliedPatches, this.reportExportFolder + "/listAppliedPatches."+this.reportExportType, this.reportExportType);
            List<String> generatedFiles = new ArrayList<String>();
            generatedFiles.add(this.reportExportType+"/listAppliedPatches."+this.reportExportType);


            model.addAttribute("generatedFiles", generatedFiles);
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            logger.debug("Full Export completed!");
        }
    }

    /**
     * Checks if the given path represents an existing folder.
     * If it does not exist, creates the folder.
     *
     * @param folderPath the path to the folder
     * @return true if the folder exists or was created successfully, false otherwise
     */
    public boolean checkAndCreateFolder(String folderPath) throws IOException{
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
