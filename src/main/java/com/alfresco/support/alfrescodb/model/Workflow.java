package com.alfresco.support.alfrescodb.model;

public class Workflow {
    private int occurrences;
    private int open;
    private int closed;
    private String name;
    private String procDefId;


	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
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
	
	public void setOpen(int open) {
		this.open = open;
	}

	public int getOpen(){
		return this.open;
	}
	
	public void setClosed(int closed) {
		this.closed = closed;
	}

	public int getClosed(){
		return this.closed;
	}
}
