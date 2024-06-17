package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class LargeFolderBean implements Serializable {
    private BigInteger count;
    private String protocol;
    private String identifier;
    private String uuid;
    private String nodeName;
    private String localName;

    public BigInteger getCount() {
        return count;
    }
    public void setCount(BigInteger occurrences) {
        this.count = occurrences;
    }
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
