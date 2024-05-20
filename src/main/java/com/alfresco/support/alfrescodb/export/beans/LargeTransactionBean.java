package com.alfresco.support.alfrescodb.export.beans;

import java.io.Serializable;
import java.math.BigInteger;

public class LargeTransactionBean implements Serializable {
    private Long trxId;
    private BigInteger nodes;
    
    public Long getTrxId() {
        return trxId;
    }
    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }
    public BigInteger getNodes() {
        return nodes;
    }
    public void setNodes(BigInteger nodes) {
        this.nodes = nodes;
    }
}
