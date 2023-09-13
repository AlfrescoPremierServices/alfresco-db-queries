package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class NodesList {
	private int occurrences;

	private String createDate;
	private String mimeType;
	private String nodeType;
	private String store;
	private long diskSpace;
	private int entries;

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeType() {
		return this.nodeType;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getStore() {
		return this.store;
	}

	public void setDiskSpace(long diskSpace) {
		this.diskSpace = diskSpace;
	}

	public long getDiskSpace() {
		return this.diskSpace;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences() {
		return this.occurrences;
	}

	public String printNodesByMimeType() {
		return this.printNodesByMimeType(ExportController.EXPORT_TXT);
	}

	public String printNodesByMimeType(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s", mimeType, occurrences, diskSpace);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"mimeType\":\"%s\", \"occurrences\":\"%s\", \"diskSpace\":\"%s\"}", mimeType,
					occurrences, diskSpace);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s", mimeType, occurrences, diskSpace);
		}
		return res;
	}

	public String printNodesByType() {
		return this.printNodesByType(ExportController.EXPORT_TXT);
	}

	public String printNodesByType(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", nodeType, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"nodeType\":\"%s\", \"occurrences\":\"%s\"}", nodeType, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s", nodeType, occurrences);
		}
		return res;
	}

	public String printNodesByTypeAndMonth() {
		return this.printNodesByTypeAndMonth(ExportController.EXPORT_TXT);
	}

	public String printNodesByTypeAndMonth(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s", createDate, nodeType, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"createDate\":\"%s\", \"nodeType\":\"%s\", \"occurrences\":\"%s\"}", createDate,
					nodeType, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s", createDate, nodeType, occurrences);
		}
		return res;
	}

	public String printNodesByStore() {
		return this.printNodesByStore(ExportController.EXPORT_TXT);
	}

	public String printNodesByStore(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", store, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"store\":\"%s\", \"occurrences\":\"%s\"}", store, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s", store, occurrences);
		}
		return res;
	}
}
