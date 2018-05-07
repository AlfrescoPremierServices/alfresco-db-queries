package com.alfresco.support.alfrescodb.model;

public class AppliedPatches {
    private String id;
    private String appliedToSchema;
    private String appliedOnDate;
    private String appliedToServer;
    private String wasExecuted;
    private String succeeded;
    private String report;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppliedToSchema() {
        return appliedToSchema;
    }

    public void setAppliedToSchema(String appliedToSchema) {
        this.appliedToSchema = appliedToSchema;
    }

    public String getAppliedOnDate() {
        return appliedOnDate;
    }

    public void setAppliedOnDate(String appliedOnDate) {
        this.appliedOnDate = appliedOnDate;
    }

    public String getAppliedToServer() {
        return appliedToServer;
    }

    public void setAppliedToServer(String appliedToServer) {
        this.appliedToServer = appliedToServer;
    }

    public String getWasExecuted() {
        return wasExecuted;
    }

    public void setWasExecuted(String wasExecuted) {
        this.wasExecuted = wasExecuted;
    }

    public String getSucceeded() {
        return succeeded;
    }

    public void setSucceeded(String succeeded) {
        this.succeeded = succeeded;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String printAppliedPatches() {
		return String.format("\n%s, %s, %s, %s, %s, %s, %s", id, appliedToSchema, appliedOnDate, appliedToServer, wasExecuted, succeeded, report);
	}
}
