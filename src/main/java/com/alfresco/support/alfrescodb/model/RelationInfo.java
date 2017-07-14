package com.alfresco.support.alfrescodb.model;

public class RelationInfo {
    private String tableName;
    private String table;
    private String total;
    private String index;
    private String rowEstimate;
    
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName(){
		return this.tableName;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotal(){
		return this.total;
	}	
	
	public void setRowEstimate(String rowEstimate) {
		this.rowEstimate = rowEstimate;
	}

	public String getRowEstimate(){
		return this.rowEstimate;
	}	
	
	public void setTable(String table) {
		this.table = table;
	}

	public String getTable(){
		return this.table;
	}
	
	public void setIndex(String index) {
		this.index = index;
	}

	public String getIndex(){
		return this.index;
	}

	public String printDbInfo() {
		return String.format("\n%s, %s, %s, %s, %s ", tableName, total, rowEstimate, table, index);
	}
}
