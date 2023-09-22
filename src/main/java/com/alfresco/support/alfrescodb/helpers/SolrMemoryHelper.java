package com.alfresco.support.alfrescodb.helpers;

import org.springframework.beans.factory.annotation.Value;

public class SolrMemoryHelper {
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

    public Long getAlfrescoSolrFilterCacheSize() {
        return alfrescoSolrFilterCacheSize;
    }

    public void setAlfrescoSolrFilterCacheSize(Long alfrescoSolrFilterCacheSize) {
        this.alfrescoSolrFilterCacheSize = alfrescoSolrFilterCacheSize;
    }

    public Long getAlfrescoSolrQueryResultCacheSize() {
        return alfrescoSolrQueryResultCacheSize;
    }

    public void setAlfrescoSolrQueryResultCacheSize(Long alfrescoSolrQueryResultCacheSize) {
        this.alfrescoSolrQueryResultCacheSize = alfrescoSolrQueryResultCacheSize;
    }

    public Long getAlfrescoSolrAuthorityCacheSize() {
        return alfrescoSolrAuthorityCacheSize;
    }

    public void setAlfrescoSolrAuthorityCacheSize(Long alfrescoSolrAuthorityCacheSize) {
        this.alfrescoSolrAuthorityCacheSize = alfrescoSolrAuthorityCacheSize;
    }

    public Long getAlfrescoSolrPathCacheSize() {
        return alfrescoSolrPathCacheSize;
    }

    public void setAlfrescoSolrPathCacheSize(Long alfrescoSolrPathCacheSize) {
        this.alfrescoSolrPathCacheSize = alfrescoSolrPathCacheSize;
    }

    public Long getArchiveSolrFilterCacheSize() {
        return archiveSolrFilterCacheSize;
    }

    public void setArchiveSolrFilterCacheSize(Long archiveSolrFilterCacheSize) {
        this.archiveSolrFilterCacheSize = archiveSolrFilterCacheSize;
    }

    public Long getArchiveSolrQueryResultCacheSize() {
        return archiveSolrQueryResultCacheSize;
    }

    public void setArchiveSolrQueryResultCacheSize(Long archiveSolrQueryResultCacheSize) {
        this.archiveSolrQueryResultCacheSize = archiveSolrQueryResultCacheSize;
    }

    public Long getArchiveSolrAuthorityCacheSize() {
        return archiveSolrAuthorityCacheSize;
    }

    public void setArchiveSolrAuthorityCacheSize(Long archiveSolrAuthorityCacheSize) {
        this.archiveSolrAuthorityCacheSize = archiveSolrAuthorityCacheSize;
    }

    public Long getArchiveSolrPathCacheSize() {
        return archiveSolrPathCacheSize;
    }

    public void setArchiveSolrPathCacheSize(Long archiveSolrPathCacheSize) {
        this.archiveSolrPathCacheSize = archiveSolrPathCacheSize;
    }
}
