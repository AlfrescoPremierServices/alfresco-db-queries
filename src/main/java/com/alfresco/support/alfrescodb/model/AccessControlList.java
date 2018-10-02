package com.alfresco.support.alfrescodb.model;

public class AccessControlList {
	public int occurrences;
	private String permission;
	private int permissionCount;
	private Boolean inherits;
	private int numNodes;
	private int numAces;
	private String aclid;
	private String authorityHash;
	private String aclType;

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

	public void setInherits(Boolean inherits) { this.inherits = inherits; }

	public Boolean getInherits(){
		return this.inherits;
	}

	public String printAccessControlList() {
		return String.format("\n%s", occurrences);
	}

	public String printAccessControlListEntries() {
		return String.format("\n%s, %s", permission, permissionCount);
	}

	public String printAccessControlListInheritance() { return String.format("\n%s, %s", inherits, occurrences); }

	public String getAclid()
		{
		return this.aclid;
		}

	public void setAclid(String aclid)
		{
		this.aclid = aclid;
		}

	public int getNumNodes()
		{
		return numNodes;
		}

	public String printAclNode()
		{
		return String.format("\n%s, %s", aclid, numNodes);
		}
	public void setNumNodes(int numNodes)
		{
		this.numNodes = numNodes;
		}

	public int getNumAces()
		{
		return numAces;
		}

	public void setNumAces(int numAcls)
		{
		this.numAces = numAces;
		}

	public String getAuthorityHash()
		{
		return authorityHash;
		}

	public void setAuthorityHash(String authorityHash)
		{
		this.authorityHash = authorityHash;
	}
	public String printAuthorityAce()
		{
		return String.format("\n%s, %s", authorityHash, numAces);
		}

	public String getAclType()
		{
		return aclType;
		}

	public void setAclType(String aclType)
		{
		this.aclType = aclType;
	}
	public String printAclType()
		{
		return String.format("\n%s, %s, %s", aclType, inherits, occurrences);
		}

}
