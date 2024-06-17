package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class ArchivedNodesBean implements Serializable {
    private String auditModifier;
    private BigInteger count;
    
    public String getAuditModifier() {
        return auditModifier;
    }
    public void setAuditModifier(String auditModifier) {
        this.auditModifier = auditModifier;
    }
    public BigInteger getCount() {
        return count;
    }
    public void setCount(BigInteger count) {
        this.count = count;
    }
}
