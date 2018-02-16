package com.alfresco.support.alfrescodb.model;

public class NodesList {
    private int occurrences;

    private String createDate;
    private String mimeType;
    private String nodeType;
    private String store;
    private long diskSpace;
    private int entries;

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getCreateDate(){
		return this.createDate;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getMimeType(){
		return this.mimeType;
	}
	
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	public String getNodeType(){
		return this.nodeType;
	}

	public void setStore(String store) {
		this.store = store;
	}
	
	public String getStore(){
		return this.store;
	}
	
	public void setDiskSpace(long diskSpace){
		this.diskSpace = diskSpace;
	}

	public long getDiskSpace(){
		return this.diskSpace;
	}
	
	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences(){
		return this.occurrences;
	}

	public String printNodesByMimeType() {
		return String.format("\n%s, %s, %s", mimeType, occurrences, diskSpace);
	}

	public String printNodesByType() {
		return String.format("\n%s, %s", nodeType, occurrences);
	}

	public String printNodesByTypeAndMonth() {
		return String.format("\n%s, %s, %s", createDate, nodeType, occurrences);
	}

	public String printNodesByStore() {
		return String.format("\n%s, %s", store, occurrences);
	}
}
