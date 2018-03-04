package com.alfresco.support.alfrescodb.model;

public class AccessControlList {
    private int occurrences;
	private String permission;
	private int permissionCount;

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences(){
		return this.occurrences;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission(){
		return this.permission;
	}

	public void setPermissionCount(int permissionCount) {
		this.permissionCount = permissionCount;
	}

	public int getPermissionCount(){
		return this.permissionCount;
	}

	public String printAccessControlList() {
		return String.format("\n%s", occurrences);
	}

	public String printAccessControlListEntries() {
		return String.format("\n%s, %s", permission, permissionCount);
	}
}