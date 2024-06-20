package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActivitiesFeedByUserBean implements Serializable {
    private BigInteger count;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss.SSSZ")
    private Date postDate;
    private String siteNetwork;
    private String feedUserId;
    
    public BigInteger getCount() {
        return count;
    }
    public void setCount(BigInteger occurrences) {
        this.count = occurrences;
    }
    public Date getPostDate() {
        return postDate;
    }
    public void setPostDate(Date post_date) {
        this.postDate = post_date;
    }
    public String getSiteNetwork() {
        return siteNetwork;
    }
    public void setSiteNetwork(String siteNetwork) {
        this.siteNetwork = siteNetwork;
    }
    public String getFeedUserId() {
        return feedUserId;
    }
    public void setFeedUserId(String feedUserId) {
        this.feedUserId = feedUserId;
    }
}
