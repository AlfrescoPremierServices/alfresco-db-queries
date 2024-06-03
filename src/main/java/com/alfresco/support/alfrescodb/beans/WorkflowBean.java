package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class WorkflowBean implements Serializable {
    private BigInteger occurrences;
    private String procdefid;
    private String taskname;

    public BigInteger getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(BigInteger occurrences) {
        this.occurrences = occurrences;
    }
    public String getProcdefid() {
        return procdefid;
    }
    public void setProcdefid(String procdefid) {
        this.procdefid = procdefid;
    }
    public String getTaskname() {
        return taskname;
    }
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }
}
