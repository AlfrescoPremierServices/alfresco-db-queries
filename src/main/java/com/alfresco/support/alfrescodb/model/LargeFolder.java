package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class LargeFolder {
	private int occurrences;
	private String nodeRef;
	private String nodeName;
	private String localName;
	private int entries = 0;

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences() {
		return this.occurrences;
	}

	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}

	public String getNodeRef() {
		return this.nodeRef;
	}

	public void setEntries(int entries) {
		this.entries = entries;
	}

	public int getEntries() {
		return this.entries;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setType(String localName) {
		this.localName = localName;
	}

	public String getLocalName() {
		return this.localName;
	}

	public String printLargeFolders() {
		return this.printLargeFolders(ExportController.EXPORT_TXT);
	}

	public String printLargeFolders(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s", nodeName, nodeRef, localName, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"nodeName\":\"%s\", \"nodeRef\":\"%s\", \"localName\":\"%s\", \"occurrences\":\"%s\"}",
					nodeName, nodeRef,
					localName, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s", nodeName, nodeRef, localName, occurrences);
		}
		return res;
	}
}
