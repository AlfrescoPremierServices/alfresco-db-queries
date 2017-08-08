package com.alfresco.support.alfrescodb.model;

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
		return String.format("\n%s, %s", propertyName, propertyValue);
	}
}