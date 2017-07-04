package com.alfresco.support.alfrescodb;

public class Workflow {
    private int occurrencies;
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
	
	public void setOccurrencies(int occurrencies) {
		this.occurrencies = occurrencies;
	}

	public int getOccurrencies(){
		return this.occurrencies;
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
