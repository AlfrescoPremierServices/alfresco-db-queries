package com.alfresco.support.alfrescodb.export.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class NodeMimeTypeBean implements Serializable {
    private String mimetype;
    private BigInteger occurrences;
    private BigInteger total_content_size_bytes;
    
    public String getMimetype() {
        return mimetype;
    }
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
    public BigInteger getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(BigInteger occurrences) {
        this.occurrences = occurrences;
    }
    public BigInteger getTotal_content_size_bytes() {
        return total_content_size_bytes;
    }
    public void setTotal_content_size_bytes(BigInteger total_content_size_bytes) {
        this.total_content_size_bytes = total_content_size_bytes;
    }
}
