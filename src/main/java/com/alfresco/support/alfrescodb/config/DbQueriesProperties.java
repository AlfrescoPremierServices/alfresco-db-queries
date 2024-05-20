package com.alfresco.support.alfrescodb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db.queries")
public class DbQueriesProperties {
    private Boolean isEnterpriseVersion;
    private Integer largeFolderSizeThreshold;
    private Integer largeTransactionSizeThreshold;
    private String reportFile;
    private Boolean reportSplit;
    private String reportExportType;
    private String reportExportFolder;

    @Value("${spring.datasource.platform}")
    private String dbType;
    
    public Boolean getIsEnterpriseVersion() {
        return isEnterpriseVersion;
    }
    public void setIsEnterpriseVersion(Boolean isEnterpriseVersion) {
        this.isEnterpriseVersion = isEnterpriseVersion;
    }
    public Integer getLargeFolderSizeThreshold() {
        return largeFolderSizeThreshold;
    }
    public void setLargeFolderSizeThreshold(Integer largeFolderSizeThreshold) {
        this.largeFolderSizeThreshold = largeFolderSizeThreshold;
    }
    public Integer getLargeTransactionSizeThreshold() {
        return largeTransactionSizeThreshold;
    }
    public void setLargeTransactionSizeThreshold(Integer largeTransactionSizeThreshold) {
        this.largeTransactionSizeThreshold = largeTransactionSizeThreshold;
    }
    public String getReportFile() {
        return reportFile;
    }
    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }
    public String getDbType() {
        return dbType;
    }
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
    public Boolean getReportSplit() {
        return reportSplit;
    }
    public void setReportSplit(Boolean reportSplit) {
        this.reportSplit = reportSplit;
    }
    public String getReportExportType() {
        return reportExportType;
    }
    public void setReportExportType(String reportExportType) {
        this.reportExportType = reportExportType;
    }
    public String getReportExportFolder() {
        return reportExportFolder;
    }
    public void setReportExportFolder(String reportExportFolder) {
        this.reportExportFolder = reportExportFolder;
    }
    
}
