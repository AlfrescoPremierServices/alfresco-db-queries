package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class RelationInfo {
	private String tableSchema;
	private String tableName;
	private String table;
	private String total;
	private String index;
	private String rowEstimate;

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableSchema() {
		return this.tableSchema;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotal() {
		return this.total;
	}

	public void setRowEstimate(String rowEstimate) {
		this.rowEstimate = rowEstimate;
	}

	public String getRowEstimate() {
		return this.rowEstimate;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return this.table;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIndex() {
		return this.index;
	}

	public String printDbInfo() {
		return this.printDbInfo(ExportController.EXPORT_TXT);
	}

	public String printDbInfo(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s,%s", tableName, total, rowEstimate, table, index);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"tableName\":\"%s\", \"total\":\"%s\", \"rowEstimate\":\"%s\", \"table\":\"%s\", \"index\":\"%s\"}",  tableName, total, rowEstimate, table, index);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s, %s ", tableName, total, rowEstimate, table, index);
		}
		return res;
	}
}
