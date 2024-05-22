package com.alfresco.support.alfrescodb.beans;

import java.io.Serializable;

public class ContentModelBean implements Serializable {
	private String model;
	private String property;
    
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getProperty() {
        return property;
    }
    public void setProperty(String property) {
        this.property = property;
    }
}
