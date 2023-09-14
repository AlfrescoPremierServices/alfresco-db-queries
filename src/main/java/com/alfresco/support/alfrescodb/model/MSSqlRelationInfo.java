package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class MSSqlRelationInfo {
    private String SchemaName;
    private String TableName;
    private String RowCounts;
    private String TotalSpace;
    private String UsedSpace;
    private String UnUsedSpace;
    private String IndexName;
    private String IndexSize;
    private String IndexID;

    public void setSchemaName(String SchemaName) {
        this.SchemaName = SchemaName;
    }

    public String getSchemaName() {
        return this.SchemaName;
    }

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public String getTableName() {
        return this.TableName;
    }

    public void setRowCounts(String RowCounts) {
        this.RowCounts = RowCounts;
    }

    public String getRowCounts() {
        return this.RowCounts;
    }

    public void setTotalSpace(String TotalSpace) {
        this.TotalSpace = TotalSpace;
    }

    public String getTotalSpace() {
        return this.TotalSpace;
    }

    public void setUsedSpace(String UsedSpace) {
        this.UsedSpace = UsedSpace;
    }

    public String getUsedSpace() {
        return this.UsedSpace;
    }

    public void setUnUsedSpace(String UnUsedSpace) {
        this.UnUsedSpace = UnUsedSpace;
    }

    public String getUnusedSpace() {
        return this.UnUsedSpace;
    }

    public void setIndexName(String IndexName) {
        this.IndexName = IndexName;
    }

    public String getIndexName() {
        return this.IndexName;
    }

    public void setIndexSize(String indexSize) {
        this.IndexSize = indexSize;
    }

    public String getIndexSize() {
        return this.IndexSize;
    }

    public void setIndexID(String indexID) {
        this.IndexSize = indexID;
    }

    public String getIndexID() {
        return this.IndexID;
    }

    public String printTableInfo() {
        return this.printTableInfo(ExportController.EXPORT_TXT);
    }

    public String printTableInfo(String format) {
        String res = null;
        if (ExportController.EXPORT_CSV.equals(format)) {
            res = String.format("\n%s,%s,%s,%s,%s,%s", SchemaName, TableName, RowCounts, TotalSpace, UsedSpace,
                    UnUsedSpace);
        } else if (ExportController.EXPORT_JSON.equals(format)) {
            res = String.format(
                    "\n{\"SchemaName\":\"%s\", \"TableName\":\"%s\", \"RowCounts\":\"%s\", \"TotalSpace\":\"%s\", \"UsedSpace\":\"%s\", \"UnUsedSpace\":\"%s\"}",
                    SchemaName, TableName, RowCounts, TotalSpace, UsedSpace, UnUsedSpace);
        } else { // Default TXT
            res = String.format("\n%s, %s, %s, %s, %s, %s", SchemaName, TableName, RowCounts, TotalSpace, UsedSpace,
                    UnUsedSpace);
        }
        return res;
    }

    public String printIndexInfo() {
        return this.printIndexInfo(ExportController.EXPORT_TXT);
    }

    public String printIndexInfo(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s,%s,%s", SchemaName, TableName, IndexName, IndexSize);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"SchemaName\":\"%s\", \"TableName\":\"%s\", \"IndexName\":\"%s\", \"IndexSize\":\"%s\"}", SchemaName, TableName, IndexName, IndexSize);
		} else { // Default TXT
			res = String.format("\n%s, %s, %s, %s", SchemaName, TableName, IndexName, IndexSize);
		}
		return res;
    }
}
