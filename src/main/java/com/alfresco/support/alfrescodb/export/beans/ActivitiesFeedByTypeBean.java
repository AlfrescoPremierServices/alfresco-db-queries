package com.alfresco.support.alfrescodb.export.beans;

import java.math.BigInteger;
import java.sql.Timestamp;

public class ActivitiesFeedByTypeBean {
    private BigInteger occurrences;
    private Timestamp post_date;
    private String sitenetwork;
    private String activitytype;
    
    public BigInteger getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(BigInteger occurrences) {
        this.occurrences = occurrences;
    }
    public Timestamp getPost_date() {
        return post_date;
    }
    public void setPost_date(Timestamp post_date) {
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
