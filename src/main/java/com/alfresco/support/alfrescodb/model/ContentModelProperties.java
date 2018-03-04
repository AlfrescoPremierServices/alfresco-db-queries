package com.alfresco.support.alfrescodb.model;

public class ContentModelProperties {
	private String model;
	private String property;

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel(){
		return this.model;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty(){
		return this.property;
	}

	public String printContentModelProperties() {
		return String.format("\n%s, %s", model, property);
	}
}
