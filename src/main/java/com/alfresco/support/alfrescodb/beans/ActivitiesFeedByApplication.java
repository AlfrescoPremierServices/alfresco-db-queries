package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

public class ActivitiesFeedByApplication implements Serializable {
    private BigInteger occurrences;
    private Date post_date;
    private String sitenetwork;
    private String apptool;
    
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
    public String getApptool() {
        return apptool;
    }
    public void setApptool(String apptool) {
        this.apptool = apptool;
    }
    
}
