package com.alfresco.support.alfrescodb;

public class LargeFolder {
    private int occurrences;
    private String nodeRef;
    private String name;
    private String type;
    private int entries = 0;

    @Override
    public String toString() {
        return String.format(
                "largeFolder[name='%s', nodeRef='%s', occurrences=%d, type='%s']", name, nodeRef, occurrences, type);
    }

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences(){
		return this.occurrences;
	}
	
	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}
	
	public String getNodeRef(){
		return this.nodeRef;
	}
	
	public void setEntries(int entries){
		this.entries = entries;
	}
	
	public int getEntries(){
		return this.entries;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
}
