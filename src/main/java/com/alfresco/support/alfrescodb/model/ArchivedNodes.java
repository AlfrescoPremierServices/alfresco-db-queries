package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class ArchivedNodes {
	private String auditModifier;
	private int nodes;
	private int entries;

	public void setAuditModifier(String auditModifier) {
		this.auditModifier = auditModifier;
	}

	public String getAuditModifier() {
		return this.auditModifier;
	}

	public void setNodes(int nodes) {
		this.nodes = nodes;
	}

	public int getNodes() {
		return this.nodes;
	}

	public void setEntries(int entries) {
		this.entries = entries;
	}

	public int getEntries() {
		return this.entries;
	}

	public String printArchivedNodes() {
		return this.printArchivedNodes(ExportController.EXPORT_TXT);
	}

	public String printArchivedNodes(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s", nodes);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"nodes\":\"%s\"}", nodes);
		} else { // Default TXT
			res = String.format("\n%s", nodes);
		}
		return res;
	}

	public String printArchivedNodesByUser() {
		return this.printArchivedNodesByUser(ExportController.EXPORT_TXT);
	}

	public String printArchivedNodesByUser(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", nodes, auditModifier);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"nodes\":\"%s\", \"auditModifier\":\"%s\"}", nodes, auditModifier);
		} else { // Default TXT
			res = String.format("\n%s, %s", nodes, auditModifier);
		}
		return res;
	}
}
