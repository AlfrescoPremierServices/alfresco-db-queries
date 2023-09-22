package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class JmxProperties {
	private String propertyName;
	private String propertyValue;

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

    public String getPropertyValue() {
        return this.propertyValue;
    }
    
    public String printJmxProperties() {
		return this.printJmxProperties(ExportController.EXPORT_TXT);
	}
	
	public String printJmxProperties(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", propertyName, propertyValue);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"propertyName\":\"%s\", \"propertyValue\":\"%s\"}", propertyName, propertyValue);
		} else { // Default TXT
			res = String.format("\n%s, %s", propertyName, propertyValue);
		}
		return res;
	}
}