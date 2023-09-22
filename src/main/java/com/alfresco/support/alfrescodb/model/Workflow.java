package com.alfresco.support.alfrescodb.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class Workflow {
	private int occurrences;
	private int open;
	private int closed;
	private String taskName;
	private String procDefId;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcDefId() {
		return this.procDefId;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences() {
		return this.occurrences;
	}

	public String printProcesses() {
		return this.printProcesses(ExportController.EXPORT_TXT);
	}

	public String printProcesses(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", procDefId, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"procDefId\":\"%s\", \"occurrences\":\"%s\"}", procDefId, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s", procDefId, occurrences);
		}
		return res;
	}

	public String printTasks() {
		return this.printTasks(ExportController.EXPORT_TXT);
	}

	public String printTasks(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s", procDefId, taskName, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"procDefId\":\"%s\", \"taskName\":\"%s\", \"occurrences\":\"%s\"}", procDefId, taskName, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s", procDefId, taskName, occurrences);
		}
		return res;
	}
}
