package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;

public class LockedResourcesBean implements Serializable {
    private int id;
    private String sharedResource;
    private String exclusiveResource;
    private String lockToken;
    private String startTime;
    private String expiryTime;
    private String uri;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSharedResource() {
        return sharedResource;
    }
    public void setSharedResource(String sharedResource) {
        this.sharedResource = sharedResource;
    }
    public String getExclusiveResource() {
        return exclusiveResource;
    }
    public void setExclusiveResource(String exclusiveResource) {
        this.exclusiveResource = exclusiveResource;
    }
    public String getLockToken() {
        return lockToken;
    }
    public void setLockToken(String lockToken) {
        this.lockToken = lockToken;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getExpiryTime() {
        return expiryTime;
    }
    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
}
