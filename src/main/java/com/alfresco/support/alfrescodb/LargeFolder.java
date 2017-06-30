package com.alfresco.support.alfrescodb;

public class LargeFolder {
    private int occurrencies;
    private String nodeRef;
    private String name;
    private String type;
    private int entries = 0;

    @Override
    public String toString() {
        return String.format(
                "largeFolder[name='%s', nodeRef='%s', occurrencies=%d, type='%s']", name, nodeRef, occurrencies, type);
    }

	public void setOccurrencies(int occurrencies) {
		this.occurrencies = occurrencies;
	}

	public int getOccurrencies(){
		return this.occurrencies;
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
