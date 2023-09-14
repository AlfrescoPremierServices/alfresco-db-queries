package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class OracleRelationInfo {
    private String tableName;
    private String indexName;
    private String size;


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName(){
		return this.tableName;
	}

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexName(){
        return this.indexName;
    }

    public void setsize(String size) {
        this.size = size;
    }

    public String getsize(){
        return this.size;
    }

    public String printTableInfo() {
		return this.printTableInfo(ExportController.EXPORT_TXT);
	}

    public String printTableInfo(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", tableName, size);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"tableName\":\"%s\", \"size\":\"%s\"}", tableName, size);
		} else { // Default TXT
			res = String.format("\n%s, %s", tableName, size);
		}
		return res;
	}

    public String printIndexInfo() {
        return this.printIndexInfo(ExportController.EXPORT_TXT);
    }

    public String printIndexInfo(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s", tableName, indexName, size);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"tableName\":\"%s\", \"indexName\":\"%s\", \"size\":\"%s\"}", tableName, indexName, size);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s", tableName, indexName, size);
		}
		return res;
    }
}
