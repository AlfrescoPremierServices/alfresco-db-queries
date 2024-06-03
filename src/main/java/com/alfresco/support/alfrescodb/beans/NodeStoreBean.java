package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class NodeStoreBean implements Serializable {
    private String protocol;
    private String identifier;
    private BigInteger occurrences;
    private BigInteger total_content_size_bytes;

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
