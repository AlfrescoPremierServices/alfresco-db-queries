package com.alfresco.support.alfrescodb.model;

public class AccessControlList {
    private int occurrences;

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences(){
		return this.occurrences;
	}

	public String printAccessControlList() {
		return String.format("\n%s", occurrences);
	}
}
