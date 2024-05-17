package com.alfresco.support.alfrescodb.export.beans;

import java.math.BigInteger;

public class ArchivedNodesBean {
    private String auditmodifier;
    private BigInteger occurrences;
    public String getAuditmodifier() {
        return auditmodifier;
    }
    public void setAuditmodifier(String auditmodifier) {
        this.auditmodifier = auditmodifier;
    }
    public BigInteger getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(BigInteger occurrences) {
        this.occurrences = occurrences;
    }
}
