package com.alfresco.support.alfrescodb.export.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

public class ActivitiesFeedByTypeBean implements Serializable {
    private BigInteger occurrences;
    private Date post_date;
    private String sitenetwork;
    private String activitytype;
    
    public BigInteger getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(BigInteger occurrences) {
        this.occurrences = occurrences;
    }
    public Date getPost_date() {
        return post_date;
    }
    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }
    public String getSitenetwork() {
        return sitenetwork;
    }
    public void setSitenetwork(String sitenetwork) {
        this.sitenetwork = sitenetwork;
    }
    public String getActivitytype() {
        return activitytype;
    }
    public void setActivitytype(String activitytype) {
        this.activitytype = activitytype;
    }
}
