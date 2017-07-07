package com.alfresco.support.alfrescodb.model;

public class RelationInfoMySQL {
    private String tableName;
    private Long table;
    private Long total;
    private Long index;
    private Long rowEstimate;
    
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName(){
		return this.tableName;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getTotal(){
		return this.total;
	}	
	
	public void setRowEstimate(Long rowEstimate) {
		this.rowEstimate = rowEstimate;
	}

	public Long getRowEstimate(){
		return this.rowEstimate;
	}	
	
	public void setTable(Long table) {
		this.table = table;
	}

	public Long getTable(){
		return this.table;
	}
	
	public void setIndex(Long index) {
		this.index = index;
	}

	public Long getIndex(){
		return this.index;
	}
}
