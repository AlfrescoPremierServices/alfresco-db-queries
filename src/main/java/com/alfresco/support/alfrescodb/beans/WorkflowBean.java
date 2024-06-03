package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class WorkflowBean implements Serializable {
    private BigInteger count;
    private String procDefId;
    private String taskName;

    public BigInteger getCount() {
        return count;
    }
    public void setCount(BigInteger count) {
        this.count = count;
    }
    public String getProcDefId() {
        return procDefId;
    }
    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
