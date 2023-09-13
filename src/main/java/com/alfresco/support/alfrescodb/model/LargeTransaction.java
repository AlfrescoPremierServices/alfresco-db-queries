package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

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
		return this.printLargeTransactions(ExportController.EXPORT_TXT);
	}

	public String printLargeTransactions(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s, %s", trxId, nodes);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"trxId\":\"%s\", \"nodes\":\"%s\"}", trxId, nodes);
		} else { // Default TXT
			res = String.format("\n%s, %s", trxId, nodes);
		}
		return res;
	}
}
