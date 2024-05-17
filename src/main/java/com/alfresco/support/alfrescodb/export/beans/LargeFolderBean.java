package com.alfresco.support.alfrescodb.export.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class LargeFolderBean implements Serializable {
    private BigInteger occurrences;
    private String nodeRef;
    private String nodeName;
    private String localName;

    public BigInteger getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(BigInteger occurrences) {
        this.occurrences = occurrences;
    }
    public String getNodeRef() {
        return nodeRef;
    }
    public void setNodeRef(String nodeRef) {
        this.nodeRef = nodeRef;
    }
    public String getNodeName() {
        return nodeName;
    }
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    public String getLocalName() {
        return localName;
    }
    public void setLocalName(String localName) {
        this.localName = localName;
    }
}
