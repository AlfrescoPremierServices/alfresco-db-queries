package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;

public class JmxPropertiesBean implements Serializable {
	private String propertyName;
	private String propertyValue;
    
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    public String getPropertyValue() {
        return propertyValue;
    }
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
