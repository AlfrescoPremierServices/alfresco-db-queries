package com.alfresco.support.alfrescodb.model;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class SolrMemory {
	private String alfrescoNodes;
	private String archiveNodes;
	private String transactions;
	private String acls;
	private String aclTransactions;

	private double alfrescoCoreMemory;
	private double archiveCoreMemory;
	private double totalDataStructuresMemory;
	private double alfrescoSolrCachesMemory;
	private double archiveSolrCachesMemory;
	private double totalSolrCachesMemory;
	private double totalSolrMemory;
	
	// Alfresco Solr caches
	@Value("${alfresco.solr.filterCache.size}")
	private Long alfrescoSolrFilterCacheSize;

	@Value("${alfresco.solr.queryResultCache.size}")
	private Long alfrescoSolrQueryResultCacheSize;

	@Value("${alfresco.solr.authorityCache.size}")
	private Long alfrescoSolrAuthorityCacheSize;

	@Value("${alfresco.solr.pathCache.size}")
	private Long alfrescoSolrPathCacheSize;

	// Archive Solr caches
	@Value("${archive.solr.filterCache.size}")
	private Long archiveSolrFilterCacheSize;

	@Value("${archive.solr.queryResultCache.size}")
	private Long archiveSolrQueryResultCacheSize;

	@Value("${archive.solr.authorityCache.size}")
	private Long archiveSolrAuthorityCacheSize;

	@Value("${archive.solr.pathCache.size}")
	private Long archiveSolrPathCacheSize;

	public String printSolrMemory() {
		this.doCalculations();
		return String.format("\n%s, %s, %s, %s, %s", alfrescoNodes, archiveNodes, transactions, acls, aclTransactions);
	}

	public String printSolrTotals(String format) {
		this.doCalculations();
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s,%s", alfrescoNodes, archiveNodes, transactions, acls, aclTransactions);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"alfrescoNodes\":\"%s\", \"archiveNodes\":\"%s\", \"transactions\":\"%s\", \"acls\":\"%s\", \"aclTransactions\":\"%s\"}",
					alfrescoNodes, archiveNodes, transactions, acls, aclTransactions);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s, %s", alfrescoNodes, archiveNodes, transactions, acls, aclTransactions);
		}
		return res;
	}

	public String printAlfrescoCoreDetails(String format) {
		this.doCalculations();
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s", alfrescoSolrQueryResultCacheSize, alfrescoSolrAuthorityCacheSize,
					alfrescoSolrPathCacheSize, alfrescoSolrFilterCacheSize);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"alfrescoSolrQueryResultCacheSize\":\"%s\", \"alfrescoSolrAuthorityCacheSize\":\"%s\", \"alfrescoSolrPathCacheSize\":\"%s\", \"alfrescoSolrFilterCacheSize\":\"%s\"}",
					alfrescoSolrQueryResultCacheSize, alfrescoSolrAuthorityCacheSize, alfrescoSolrPathCacheSize,
					alfrescoSolrFilterCacheSize);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s", alfrescoSolrQueryResultCacheSize, alfrescoSolrAuthorityCacheSize,
					alfrescoSolrPathCacheSize, alfrescoSolrFilterCacheSize);
		}
		return res;
	}

	public String printArchiveCoreDetails(String format) {
		this.doCalculations();
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s", archiveSolrQueryResultCacheSize, archiveSolrAuthorityCacheSize,
					archiveSolrPathCacheSize, archiveSolrFilterCacheSize);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"archiveSolrQueryResultCacheSize\":\"%s\", \"archiveSolrAuthorityCacheSize\":\"%s\", \"archiveSolrPathCacheSize\":\"%s\", \"archiveSolrFilterCacheSize\":\"%s\"}",
					archiveSolrQueryResultCacheSize, archiveSolrAuthorityCacheSize, archiveSolrPathCacheSize,
					archiveSolrFilterCacheSize);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s", archiveSolrQueryResultCacheSize, archiveSolrAuthorityCacheSize,
					archiveSolrPathCacheSize, archiveSolrFilterCacheSize);
		}
		return res;
	}

	public String printMemoryDataStructures(String format) {
		this.doCalculations();
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s", alfrescoCoreMemory, archiveCoreMemory, totalDataStructuresMemory);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"alfrescoCoreMemory\":\"%s\", \"archiveCoreMemory\":\"%s\", \"totalDataStructuresMemory\":\"%s\"}",
					alfrescoCoreMemory, archiveCoreMemory, totalDataStructuresMemory);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s", alfrescoCoreMemory, archiveCoreMemory, totalDataStructuresMemory);
		}
		return res;
	}

	public String printMemoryCacheStructures(String format) {
		this.doCalculations();
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s", alfrescoSolrCachesMemory, archiveSolrCachesMemory, totalSolrCachesMemory);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"alfrescoSolrCachesMemory\":\"%s\", \"archiveSolrCachesMemory\":\"%s\", \"totalSolrCachesMemory\":\"%s\"}",
					alfrescoSolrCachesMemory, archiveSolrCachesMemory, totalSolrCachesMemory);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s", alfrescoSolrCachesMemory, archiveSolrCachesMemory, totalSolrCachesMemory);
		}
		return res;
	}

	private void doCalculations() {}

	@PostConstruct
	private void doCalculationsPost() {
		Long tmpAlfrescoNodes = Long.valueOf(this.alfrescoNodes);
		Long tmpArchiveNodes = Long.valueOf(this.archiveNodes);
		Long tmpTransactions = Long.valueOf(this.transactions);
		Long tmpAcls = Long.valueOf(this.acls);
		Long tmpAclTransactions = Long.valueOf(this.aclTransactions);
		this.alfrescoCoreMemory = (double) (120 * tmpAlfrescoNodes
				+ 32 * (tmpTransactions + tmpAcls + tmpAclTransactions));
		this.archiveCoreMemory = (double) (120 * tmpArchiveNodes
				+ 32 * (tmpTransactions + tmpAcls + tmpAclTransactions));
		this.totalDataStructuresMemory = (double) alfrescoCoreMemory + archiveCoreMemory;
		this.alfrescoSolrCachesMemory = (double) (alfrescoSolrFilterCacheSize
				+ alfrescoSolrQueryResultCacheSize + alfrescoSolrAuthorityCacheSize + alfrescoSolrPathCacheSize)
				* (double) (2 * tmpAlfrescoNodes + tmpTransactions + tmpAcls + tmpAclTransactions) / 8;
		this.archiveSolrCachesMemory = (double) (alfrescoSolrFilterCacheSize
				+ alfrescoSolrQueryResultCacheSize + alfrescoSolrAuthorityCacheSize + alfrescoSolrPathCacheSize)
				* (double) (2 * tmpArchiveNodes + tmpTransactions + tmpAcls + tmpAclTransactions) / 8;
		this.totalSolrCachesMemory = (double) alfrescoSolrCachesMemory + archiveSolrCachesMemory;
		this.totalSolrMemory = (double) totalDataStructuresMemory + totalSolrCachesMemory;
	}

	public void setAlfrescoNodes(String alfrescoNodes) {
		this.alfrescoNodes = alfrescoNodes;
	}

	public String getAlfrescoNodes() {
		return this.alfrescoNodes;
	}

	public void setArchiveNodes(String archiveNodes) {
		this.archiveNodes = archiveNodes;
	}

	public String getArchiveNodes() {
		return this.archiveNodes;
	}

	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}

	public String getTransactions() {
		return this.transactions;
	}

	public void setAcls(String acls) {
		this.acls = acls;
	}

	public String getAcls() {
		return this.acls;
	}

	public void setAclTransactions(String aclTransactions) {
		this.aclTransactions = aclTransactions;
	}

	public String getAclTransactions() {
		return this.aclTransactions;
	}

	public double getAlfrescoCoreMemory() {
		return alfrescoCoreMemory;
	}

	public void setAlfrescoCoreMemory(double alfrescoCoreMemory) {
		this.alfrescoCoreMemory = alfrescoCoreMemory;
	}

	public double getArchiveCoreMemory() {
		return archiveCoreMemory;
	}

	public void setArchiveCoreMemory(double archiveCoreMemory) {
		this.archiveCoreMemory = archiveCoreMemory;
	}

	public double getTotalDataStructuresMemory() {
		return totalDataStructuresMemory;
	}

	public void setTotalDataStructuresMemory(double totalDataStructuresMemory) {
		this.totalDataStructuresMemory = totalDataStructuresMemory;
	}

	public double getAlfrescoSolrCachesMemory() {
		return alfrescoSolrCachesMemory;
	}

	public void setAlfrescoSolrCachesMemory(double alfrescoSolrCachesMemory) {
		this.alfrescoSolrCachesMemory = alfrescoSolrCachesMemory;
	}

	public double getArchiveSolrCachesMemory() {
		return archiveSolrCachesMemory;
	}

	public void setArchiveSolrCachesMemory(double archiveSolrCachesMemory) {
		this.archiveSolrCachesMemory = archiveSolrCachesMemory;
	}

	public double getTotalSolrCachesMemory() {
		return totalSolrCachesMemory;
	}

	public void setTotalSolrCachesMemory(double totalSolrCachesMemory) {
		this.totalSolrCachesMemory = totalSolrCachesMemory;
	}

	public double getTotalSolrMemory() {
		return totalSolrMemory;
	}

	public void setTotalSolrMemory(double totalSolrMemory) {
		this.totalSolrMemory = totalSolrMemory;
	}
}
