package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class Authority {
    private int authoritiesCount;

	public void setAuthoritiesCount(int authoritiesCount) {
		this.authoritiesCount = authoritiesCount;
	}
	
	public int getAuthoritiesCount(){
		return this.authoritiesCount;
	}

	public String printUsers() {
		return this.printUsers(ExportController.EXPORT_TXT);
	}

	public String printUsers(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s", authoritiesCount);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"authoritiesCount\":%s}", authoritiesCount);
		} else { // Default TXT
			res = String.format("\n%s", authoritiesCount);
		}
		return res;		
	}

	public String printGroups() {
		return this.printGroups(ExportController.EXPORT_TXT);
	}

	public String printGroups(String format) {
		return this.printUsers(format);	
	}
}
