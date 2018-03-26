package com.alfresco.support.alfrescodb.model;

public class AccessControlList {
	private int occurrences;
	private String permission;
	private int permissionCount;
	private Boolean inherits;
	private int aclId;
	private int numNodes;
	private String aclType;
	private int typeCount;
	private int numAce;
    private String authorities;
    private int numAcls;

public String getAuthorities() {
        return authorities;
    }

public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

public int getNumAcls() {
        return numAcls;
    }

public void setNumAcls(int numAcls) {
        this.numAcls = numAcls;
    }

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences() {
		return this.occurrences;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermissionCount(int permissionCount) {
		this.permissionCount = permissionCount;
	}

	public int getPermissionCount() {
		return this.permissionCount;
	}

	public void setInherits(Boolean inherits) {
		this.inherits = inherits;
	}

	public Boolean getInherits() {
		return this.inherits;
	}

	public int getAclId() {
		return this.aclId;
	}

	public void setAclId(int aclId) {
		this.aclId = aclId;
	}

	public String getAclType() {
		return this.aclType;
	}

	public void setAclType(String aclType) {
		this.aclType = aclType;
	}

	public int getTypeCount() {
		return this.typeCount;
	}

	public void setTypeCount(int typeCount) {
		this.typeCount = typeCount;
	}

	public int getNumAce() {
		return this.numAce;
	}

	public void setNumAce(int numAce) {
		this.numAce = numAce;
	}

	public int getNumNodes() {
		return this.numNodes;
	}

	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}

	public String printAccessControlList() {
		return String.format("\n%s", occurrences);
	}

	public String printAccessControlListEntries() {
		return String.format("\n%s, %s", permission, permissionCount);
	}

	public String printAccessControlListInheritance() {
		return String.format("\n%s, %s", inherits, occurrences);
	}

	public String printAclsPerNodes() {
		return String.format("\n%s, %s", aclId, numNodes);
	}

	public String printAclsTypesRepartition() {
		return String.format("\n%s, %s", aclType, typeCount);
	}

	public String printAcesPerAcls() {
		return String.format("\n%s, %s", aclId, numAce);
	}

    public String printAuthoritiesAcls() {
        return String.format("\n%s, %s", authorities, numAcls);
    }
}
