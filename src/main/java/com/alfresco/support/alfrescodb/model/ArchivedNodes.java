package com.alfresco.support.alfrescodb.model;

public class ArchivedNodes {
    private String auditModifier;
    private int nodes;
    private int entries;

    @Override
    public String toString() {
        return String.format(
                "ArchivedNodes[User='%s', archivedNodes=%d]", auditModifier, nodes);
    }

	public void setAuditModifier(String auditModifier) {
		this.auditModifier = auditModifier;
	}
	
	public String getAuditModifier(){
		return this.auditModifier;
	}
	
	public void setNodes(int nodes){
		this.nodes = nodes;
	}
	
	public int getNodes(){
		return this.nodes;
	}
	
	public void setEntries(int entries){
		this.entries = entries;
	}
	
	public int getEntries(){
		return this.entries;
	}

	public String printArchivedNodes() {
		return String.format("\n'%s'", nodes);
	}

	public String printArchivedNodesByUser() {
		return String.format("\n'%s', '%s'", nodes, auditModifier);
	}
}
