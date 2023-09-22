package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class ActivitiesFeed {
	private int occurrences;
	private String postDate;
	private String siteNetwork;
	private String activityType;
	private String feedUserId;
	private String appTool;
	private int entries = 0;

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getPostDate() {
		return this.postDate;
	}

	public void setSiteNetwork(String siteNetwork) {
		this.siteNetwork = siteNetwork;
	}

	public String getSiteNetwork() {
		return this.siteNetwork;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getActivityType() {
		return this.activityType;
	}

	public void setFeedUserId(String feedUserId) {
		this.feedUserId = feedUserId;
	}

	public String getFeedUserId() {
		return this.feedUserId;
	}

	public void setAppTool(String appTool) {
		this.appTool = appTool;
	}

	public String getAppTool() {
		return this.appTool;
	}

	public void setEntries(int entries) {
		this.entries = entries;
	}

	public int getEntries() {
		return this.entries;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences() {
		return this.occurrences;
	}

	public String printActivitiesByActivityType() {
		return this.printActivitiesByActivityType(ExportController.EXPORT_TXT);
	}

	public String printActivitiesByActivityType(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s", postDate, siteNetwork, activityType, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"postDate\":\"%s\", \"siteNetwork\":\"%s\", \"activityType\":\"%s\", \"occurrences\":\"%s\"}",
					postDate, siteNetwork, activityType, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s", postDate, siteNetwork, activityType, occurrences);
		}
		return res;
	}

	public String printActivitiesByUser() {
		return this.printActivitiesByUser(ExportController.EXPORT_TXT);
	}

	public String printActivitiesByUser(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s", postDate, siteNetwork, feedUserId, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"postDate\":\"%s\", \"siteNetwork\":\"%s\", \"feedUserId\":\"%s\", \"occurrences\":\"%s\"}",
					postDate, siteNetwork, feedUserId, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s", postDate, siteNetwork, feedUserId, occurrences);
		}
		return res;
	}

	public String printActivitiesByInterface() {
		return this.printActivitiesByInterface(ExportController.EXPORT_TXT);
	}

	public String printActivitiesByInterface(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s", postDate, siteNetwork, appTool, occurrences);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format(
					"\n{\"postDate\":\"%s\", \"siteNetwork\":\"%s\", \"appTool\":\"%s\", \"occurrences\":\"%s\"}",
					postDate, siteNetwork, appTool, occurrences);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s", postDate, siteNetwork, appTool, occurrences);
		}
		return res;

	}

}
