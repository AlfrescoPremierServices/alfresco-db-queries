package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;

public class AccessControlBean implements Serializable {
    private int count;
	private String permission;
	private Boolean inherits;
	private String aclId;
	private String authorityHash;
	private String aclType;

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    public Boolean getInherits() {
        return inherits;
    }
    public void setInherits(Boolean inherits) {
        this.inherits = inherits;
    }
    public String getAclId() {
        return aclId;
    }
    public void setAclId(String aclid) {
        this.aclId = aclid;
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
