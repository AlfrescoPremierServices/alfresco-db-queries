package com.alfresco.support.alfrescodb.model;

public class ActivitiesFeed {
    private int occurrences;
    private String date;
    private String siteNetwork;
    private String activityType;
    private String feedUserId;
    private String appTool;
    private int entries = 0;

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public void setSiteNetwork(String siteNetwork) {
		this.siteNetwork = siteNetwork;
	}
	
	public String getSiteNetwork(){
		return this.siteNetwork;
	}
	
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	public String getActivityType(){
		return this.activityType;
	}
	
	public void setFeedUserId(String feedUserId) {
		this.feedUserId = feedUserId;
	}
	
	public String getFeedUserId(){
		return this.feedUserId;
	}
	
	public void setAppTool(String appTool) {
		this.appTool = appTool;
	}
	
	public String getAppTool(){
		return this.appTool;
	}	
	
	public void setEntries(int entries){
		this.entries = entries;
	}
	
	public int getEntries(){
		return this.entries;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public int getOccurrences(){
		return this.occurrences;
	}

	public String printActivitiesByActivityType() {
		return String.format("\n'%s', '%s', '%s', '%s'", date, siteNetwork, activityType, occurrences);
	}

	public String printActivitiesByUser() {
		return String.format("\n'%s', '%s', '%s', '%s'", date, siteNetwork, feedUserId, occurrences);
	}

	public String printActivitiesByInterface() {
		return String.format("\n'%s', '%s', '%s', '%s'", date, siteNetwork, appTool, occurrences);
	}
}
