package com.alfresco.support.alfrescodb.model;

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
		return String.format("\n%s, %s ", tableName, size);
	}

    public String printIndexInfo() {
        return String.format("\n%s, %s, %s ", tableName, indexName, size);
    }
}
