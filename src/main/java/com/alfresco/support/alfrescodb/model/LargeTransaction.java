package com.alfresco.support.alfrescodb.model;

public class LargeTransaction {
    private int nodes;
    private int trxId;
    private int entries = 0;

    @Override
    public String toString() {
        return String.format(
                "largeTransactions[nodes=%d, trxId=%d]", nodes, trxId);
    }

	public void setNodes(int nodes) {
		this.nodes = nodes;
	}

	public int getNodes(){
		return this.nodes;
	}
	
	public void setTrxId(int trxId) {
		this.trxId = trxId;
	}

	public int getTrxId(){
		return this.trxId;
	}	
	
	public void setEntries(int entries){
		this.entries = entries;
	}
	
	public int getEntries(){
		return this.entries;
	}

	public String printLargeTransactions() {
		return String.format("\n'%s', '%s'", trxId, nodes);
	}

}
