package com.alfresco.support.alfrescodb.model;

public class SolrMemory {
    private String alfrescoNodes;
    private String archiveNodes;
    private String transactions;
    private String acls;
    private String aclTransactions;

	public void setAlfrescoNodes(String alfrescoNodes) {
		this.alfrescoNodes = alfrescoNodes;
	}

	public String getAlfrescoNodes(){
		return this.alfrescoNodes;
	}

	public void setArchiveNodes(String archiveNodes) {
		this.archiveNodes = archiveNodes;
	}

	public String getArchiveNodes(){
		return this.archiveNodes;
	}

	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}

	public String getTransactions(){
		return this.transactions;
	}

	public void setAcls(String acls) {
		this.acls = acls;
	}

	public String getAcls(){
		return this.acls;
	}

	public void setAclTransactions(String aclTransactions) {
		this.aclTransactions = aclTransactions;
	}

	public String getAclTransactions(){
		return this.aclTransactions;
	}

	public String printSolrMemory() {
		return String.format("\n%s, %s, %s, %s, %s", alfrescoNodes, archiveNodes, transactions, acls, aclTransactions);
	}
}
