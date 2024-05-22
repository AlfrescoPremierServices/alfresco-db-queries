package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;

public class AccessControlBean implements Serializable {
    public int occurrences;
	private String permission;
	private int permissionCount;
	private Boolean inherits;
	private int numNodes;
	private int numAces;
	private String aclid;
	private String authorityHash;
	private String aclType;

    public int getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    public int getPermissionCount() {
        return permissionCount;
    }
    public void setPermissionCount(int permissionCount) {
        this.permissionCount = permissionCount;
    }
    public Boolean getInherits() {
        return inherits;
    }
    public void setInherits(Boolean inherits) {
        this.inherits = inherits;
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
    public String getAclid() {
        return aclid;
    }
    public void setAclid(String aclid) {
        this.aclid = aclid;
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
