package com.alfresco.support.alfrescodb.model;

public class MSSqlRelationInfo {
    private String TableName;
	private String RowCounts;
	private String TotalSpace;
    private String UsedSpace;
    private String UnUsedSpace;
    private String IndexName;
    private String IndexSize;
    private String IndexID;

	public void setTableName(String TableName) {
		this.TableName = TableName;
	}

	public String getTableName(){
		return this.TableName;
	}
	
	public void setRowCounts(String RowCounts) {
		this.RowCounts = RowCounts;
	}

	public String getRowCounts(){
		return this.RowCounts;
	}	
	
	public void setTotalSpace(String TotalSpace) {
		this.TotalSpace = TotalSpace;
	}

	public String getTotalSpace(){
		return this.TotalSpace;
	}

	public void setUsedSpace(String UsedSpace) {
		this.UsedSpace = UsedSpace;
	}

	public String getUsedSpace(){
		return this.UsedSpace;
	}

    public void setUnUsedSpace(String UnUsedSpace) {
        this.UnUsedSpace = UnUsedSpace;
    }

    public String getUnusedSpace(){
        return this.UnUsedSpace;
    }

    public void setIndexName(String IndexName) {
        this.IndexName = IndexName;
    }

    public String getIndexName(){
        return this.IndexName;
    }

    public void setIndexSize(String indexSize) {
        this.IndexSize = indexSize;
    }

    public String getIndexSize(){
        return this.IndexSize;
    }

    public void setIndexID(String indexID) {
        this.IndexSize = indexID;
    }

    public String getIndexID(){
        return this.IndexID;
    }

    public String printTableInfo() {
		return String.format("\n%s, %s, %s, %s, %s ", TableName, RowCounts, TotalSpace, UsedSpace, UnUsedSpace);
	}

    public String printIndexInfo() {
        return String.format("\n%s, %s, %s ", TableName, IndexName, IndexSize);
    }
}
