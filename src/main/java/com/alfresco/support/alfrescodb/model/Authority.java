package com.alfresco.support.alfrescodb.model;

public class Authority {
    private int authoritiesCount;

	public void setAuthoritiesCount(int authoritiesCount) {
		this.authoritiesCount = authoritiesCount;
	}
	
	public int getAuthoritiesCount(){
		return this.authoritiesCount;
	}

	public String printUsers() {
		return String.format("\n%s", authoritiesCount);
	}

	public String printGroups() {
		return String.format("\n%s", authoritiesCount);
	}
}
