package com.alfresco.support.alfrescodb.model;

public class OracleRelationInfo {
    private String tableName;
    private String indexName;
    private String sizeMB;


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

    public void setSizeMB(String sizeMB) {
        this.sizeMB = sizeMB;
    }

    public String getSizeMB(){
        return this.sizeMB;
    }

    public String printTableInfo() {
		return String.format("\n%s, %s ", tableName, sizeMB);
	}

    public String printIndexInfo() {
        return String.format("\n%s, %s, %s ", tableName, indexName, sizeMB);
    }
}
