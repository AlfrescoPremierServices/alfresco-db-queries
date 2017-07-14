package com.alfresco.support.alfrescodb.model;

public class LockedResources {
    private int id;
    private String sharedResource;
    private String exclusiveResource;
    private String lockToken;
    private String startTime;
    private String expiryTime;
    private String uri;

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setSharedResource(String sharedResource) {
		this.sharedResource = sharedResource;
	}
	
	public String getSharedResource(){
		return this.sharedResource;
	}

	public void setExclusiveResource(String exclusiveResource) {
		this.exclusiveResource = exclusiveResource;
	}
	
	public String getExclusiveResource(){
		return this.exclusiveResource;
	}

	public void setLockToken(String lockToken) {
		this.lockToken = lockToken;
	}
	
	public String getLockToken(){
		return this.lockToken;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getStartTime(){
		return this.startTime;
	}

	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	public String getExpiryTime(){
		return this.expiryTime;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getUri(){
		return this.uri;
	}

	public String findAll() {
		return String.format("\n%s, %s, %s, %s, %s, %s, %s", id, lockToken, startTime, expiryTime, sharedResource, exclusiveResource, uri);
	}
}
