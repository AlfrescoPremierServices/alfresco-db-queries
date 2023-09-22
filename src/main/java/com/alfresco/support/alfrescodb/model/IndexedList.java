package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class IndexedList {
    private String qname_id;
    private String local_name;
    private String indexed;
    private int entries;

    public String getQname_id() {
        return qname_id;
    }

    public void setQname_id(String qname_id) {
        this.qname_id = qname_id;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public String isIndexed() {
        return indexed;
    }

    public void setIndexed(String indexed) {
        this.indexed = indexed;
    }

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public String printNodeTypesandIndexing(String format) {
        //Some pretty printing
        if (this.indexed == null) {
            this.indexed = "true";
        } else if ("0".equals(this.indexed) || 'f' == this.indexed.toLowerCase().charAt(0)) {
            this.indexed = "false";
        } else if ("null".equalsIgnoreCase(this.indexed)) {
            this.indexed = "true";
        } else {
            this.indexed = "true";
        }

        String res = null;
        if (ExportController.EXPORT_CSV.equals(format)) {
            res = String.format("\n%s,%s,%s,%s", qname_id, local_name, indexed, entries);
        } else if (ExportController.EXPORT_JSON.equals(format)) {
            res = String.format("\n{\"id\":\"%s\", \"local_name\":\"%s\", \"indexed\":\"%s\", \"entries\":\"%s\"}",
                    qname_id,
                    local_name, indexed, entries);
        } else { // Default TXT
            res = String.format("\n%s, %s, %s, %s", qname_id, local_name, indexed, entries);
        }
        return res;
    }

}
