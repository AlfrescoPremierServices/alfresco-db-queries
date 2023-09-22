package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

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

	public String printAccessControlList() {
		return this.printAccessControlList(ExportController.EXPORT_TXT);
	}

	public String printAccessControlList(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s", occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"occurences\":%s}", occurrences);
		} else { // Default TXT
			res = String.format("\n%s", occurrences);
		}
		return res;
	}

	public String printAccessControlListEntries() {
		return this.printAccessControlListEntries(ExportController.EXPORT_TXT);
	}

	public String printAccessControlListEntries(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", permission, permissionCount);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"permission\":\"%s\", \"permissionCount\":\"%s\"}", permission, permissionCount);
		} else { // Default TXT
			res = String.format("\n%s, %s", permission, permissionCount);
		}
		return res;
	}

	public String printAccessControlListInheritance() {
		return this.printAccessControlListInheritance(ExportController.EXPORT_TXT);
	}

	public String printAccessControlListInheritance(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", inherits, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"inherits\":\"%s\", \"occurrences\":\"%s\"}", inherits, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s", inherits, occurrences);
		}
		return res;
	}

	public String printAclNode() {
		return this.printAclNode(ExportController.EXPORT_TXT);
	}

	public String printAclNode(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", aclid, numNodes);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"aclid\":\"%s\", \"numNodes\":\"%s\"}", aclid, numNodes);
		} else { // Default TXT
			res = String.format("\n%s, %s", aclid, numNodes);
		}
		return res;
	}

	public String printAclType() {
		return this.printAclType(ExportController.EXPORT_TXT);
	}

	public String printAclType(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s", aclType, inherits, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"aclType\":\"%s\", \"inherits\":\"%s\", \"occurrences\":\"%s\"}", aclType, inherits,
					occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s", aclType, inherits, occurrences);
		}
		return res;
	}

	public String printAclHeight() {
		return this.printAclHeight(ExportController.EXPORT_TXT);
	}

	public String printAclHeight(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", aclid, numAces);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"aclid\":\"%s\", \"numAces\":\"%s\"}", aclid, numAces);
		} else { // Default TXT
			res = String.format("\n%s, %s", aclid, numAces);
		}
		return res;
	}

	public String printAuthorityAce() {
		return this.printAuthorityAce(ExportController.EXPORT_TXT);
	}

	public String printAuthorityAce(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", authorityHash, numAces);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"authorityHash\":\"%s\", \"numAces\":\"%s\"}", authorityHash, numAces);
		} else { // Default TXT
			res = String.format("\n%s, %s", authorityHash, numAces);
		}
		return res;
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

	public String getAclid() {
		return this.aclid;
	}

	public void setAclid(String aclid) {
		this.aclid = aclid;
	}

	public int getNumNodes() {
		return numNodes;
	}

	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}

	public int getNumAces() {
		return numAces;
	}

	public void setNumAces(int numAces) {
		this.numAces = numAces;
	}

	public String getAuthorityHash() {
		return authorityHash;
	}

	public void setAuthorityHash(String authorityHash) {
		this.authorityHash = authorityHash;
	}

	public String getAclType() {
		return aclType;
	}

	public void setAclType(String aclType) {
		this.aclType = aclType;
	}
}
