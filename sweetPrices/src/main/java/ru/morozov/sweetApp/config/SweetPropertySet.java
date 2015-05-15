package ru.morozov.sweetApp.config;

import java.util.List;

public class SweetPropertySet {
	
	private List<SweetProperty> properties;
	
	public List<SweetProperty> getProperties() {return properties;}
	public void setProperties(List<SweetProperty> properties) {this.properties = properties;}
	
	public SweetProperty getProperty(int index) {
		return index >= 0 && index < properties.size() ? properties.get(index) : null;
	}

}
