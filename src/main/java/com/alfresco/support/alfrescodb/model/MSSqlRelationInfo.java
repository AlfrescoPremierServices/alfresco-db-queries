package com.alfresco.support.alfrescodb.model;

public class MSSqlRelationInfo {
    private String TableName;
	private String RowCounts;
	private String TotalSpaceKB;
    private String UsedSpaceKB;
    private String UnusedSpaceKB;
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
	
	public void setTotalSpaceKB(String TotalSpaceKB) {
		this.TotalSpaceKB = TotalSpaceKB;
	}

	public String getTotalSpaceKB(){
		return this.TotalSpaceKB;
	}

	public void setUsedSpaceKB(String UsedSpaceKB) {
		this.UsedSpaceKB = UsedSpaceKB;
	}

	public String getUsedSpaceKB(){
		return this.UsedSpaceKB;
	}

    public void setUnusedSpaceKB(String UnusedSpaceKB) {
        this.UnusedSpaceKB = UnusedSpaceKB;
    }

    public String getUnusedSpaceKB(){
        return this.UnusedSpaceKB;
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
		return String.format("\n%s, %s, %s, %s, %s ", TableName, RowCounts, TotalSpaceKB, UsedSpaceKB, UnusedSpaceKB);
	}

    public String printIndexInfo() {
        return String.format("\n%s, %s, %s ", TableName, IndexName, IndexSize);
    }
}
