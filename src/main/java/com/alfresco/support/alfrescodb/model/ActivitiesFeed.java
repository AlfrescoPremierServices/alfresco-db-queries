package com.alfresco.support.alfrescodb.model;

public class ActivitiesFeed {
    private int occurrences;
    private String date;
    private String siteNetwork;
    private String activityType;
    private String feedUserId;
    private String appTool;
    private int entries = 0;

    @Override
    public String toString() {
        return String.format(
                "ActivtiesFeed[date='%s', siteNetwork='%s', activityType='%s', feedUserId='%s']", date, siteNetwork, activityType, feedUserId);
    }

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
}
