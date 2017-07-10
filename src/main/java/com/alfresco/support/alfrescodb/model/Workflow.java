package com.alfresco.support.alfrescodb.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public String getTaskName(){
		return this.taskName;
	}
	
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	public String getProcDefId(){
		return this.procDefId;
	}
	
	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences(){
		return this.occurrences;
	}


	public String printProcesses() {
		return String.format("\n'%s', '%s'", procDefId, occurrences);
	}

	public String printTasks() {
		return String.format("\n'%s', '%s', '%s'", procDefId, taskName, occurrences);
	}
}
