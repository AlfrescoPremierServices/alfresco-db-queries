package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class NodeMimeTypeBean implements Serializable {
    private String mimetype;
    private BigInteger count;
    private BigInteger totalContentSizeBytes;
    
    public String getMimetype() {
        return mimetype;
    }
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
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
