package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class NodeContentTypeBean implements Serializable {
    private String namespace;
    private String propertyname;
    private BigInteger occurrences;

    public String getNamespace() {
        return namespace;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    public String getPropertyname() {
        return propertyname;
    }
    public void setPropertyname(String propertyname) {
        this.propertyname = propertyname;
    }
    public BigInteger getOccurrences() {
        return occurrences;
    }
    public void setOccurrences(BigInteger occurrences) {
        this.occurrences = occurrences;
    }
}
