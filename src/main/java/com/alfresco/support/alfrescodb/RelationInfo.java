package com.alfresco.support.alfrescodb;

public class RelationInfo {
    private String relSize;
    private String relName;
    private int entries = 0;

    @Override
    public String toString() {
        return String.format(
                "relationInfos[relName=%d, relSize=%d]", relName, relSize);
    }

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelName(){
		return this.relName;
	}
	
	public void setRelSize(String relSize) {
		this.relSize = relSize;
	}

	public String getRelSize(){
		return this.relSize;
	}	
	
	public void setEntries(int entries){
		this.entries = entries;
	}
	
	public int getEntries(){
		return this.entries;
	}
}
