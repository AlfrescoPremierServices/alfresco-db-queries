package com.alfresco.support.alfrescodb.model;

import com.alfresco.support.alfrescodb.controllers.ExportController;

public class ContentModelProperties {
	private String model;
	private String property;

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return this.model;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty() {
		return this.property;
	}

	public String printContentModelProperties() {
		return this.printContentModelProperties(ExportController.EXPORT_TXT);
	}

	public String printContentModelProperties(String format) {
		String res = null;
		if (ExportController.EXPORT_CSV.equals(format)) {
			res = String.format("\n%s,%s", model, property);
		} else if (ExportController.EXPORT_JSON.equals(format)) {
			res = String.format("\n{\"model\":\"%s\", \"property\":\"%s\"}", model, property);
		} else { // Default TXT
			res = String.format("\n%s, %s", model, property);
		}
		return res;
	}
}
