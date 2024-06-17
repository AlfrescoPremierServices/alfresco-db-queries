package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class NodeStoreBean implements Serializable {
    private String protocol;
    private String identifier;
    private BigInteger count;
    private BigInteger totalContentSizeBytes;

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
    public BigInteger getCount() {
        return count;
    }
    public void setCount(BigInteger count) {
        this.count = count;
    }
    public BigInteger getTotalContentSizeBytes() {
        return totalContentSizeBytes;
    }
    public void setTotalContentSizeBytes(BigInteger totalContentSizeBytes) {
        this.totalContentSizeBytes = totalContentSizeBytes;
    }
}
