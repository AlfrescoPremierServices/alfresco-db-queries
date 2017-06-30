package com.alfresco.support.alfrescodb;

public class AlfNode {
    private long id;
    private String uuid;
    private String audit_modifier;
    private String audit_modified;

    @Override
    public String toString() {
        return String.format(
                "alf_node[id=%d, uuid='%s', audit_modifier='%s', audit_modified='%s']",
                id, uuid, audit_modifier, audit_modified);
    }

	public void setId(long id) {
		this.id = id;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setAuditModifier(String audit_modifier) {
		this.audit_modifier = audit_modifier;
	}

	public void setAuditModified(String audit_modified) {
		this.audit_modified = audit_modified;
	}
	
	public long getId(){
		return this.id;
	}
	
	public String getUuid(){
		return this.uuid;
	}	
	
	public String getAuditModifier(){
		return this.audit_modifier;
	}	
	
	public String getAuditModified(){
		return this.audit_modified;
	}	
	
	
}
