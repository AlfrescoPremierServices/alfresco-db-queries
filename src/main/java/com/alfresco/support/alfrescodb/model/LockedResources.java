package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

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

	public int getId() {
		return this.id;
	}

	public void setSharedResource(String sharedResource) {
		this.sharedResource = sharedResource;
	}

	public String getSharedResource() {
		return this.sharedResource;
	}

	public void setExclusiveResource(String exclusiveResource) {
		this.exclusiveResource = exclusiveResource;
	}

	public String getExclusiveResource() {
		return this.exclusiveResource;
	}

	public void setLockToken(String lockToken) {
		this.lockToken = lockToken;
	}

	public String getLockToken() {
		return this.lockToken;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getExpiryTime() {
		return this.expiryTime;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return this.uri;
	}

	public String printAll() {
		return this.printAll(ExportController.EXPORT_TXT);
	}

	public String printAll(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s,%s,%s,%s", id, lockToken, startTime, expiryTime, sharedResource,
					exclusiveResource, uri);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"id\":\"%s\", \"lockToken\":\"%s\", \"startTime\":\"%s\", \"expiryTime\":\"%s\", \"sharedResource\":\"%s\", \"exclusiveResource\":\"%s\", \"uri\":\"%s\"}",
					id, lockToken,
					startTime, expiryTime, sharedResource, exclusiveResource, uri);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s, %s, %s, %s", id, lockToken, startTime, expiryTime, sharedResource,
					exclusiveResource, uri);
		}
		return res;
	}

}
