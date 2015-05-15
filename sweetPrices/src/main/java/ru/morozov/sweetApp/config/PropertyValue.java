package ru.morozov.sweetApp.config;

public class PropertyValue {
	
	private Object value;
	private SweetProperty property;
	
	public PropertyValue(Object value, SweetProperty property) {
		this.value = value;
		this.property = property;
	}

	public Object getValue() {return value;}
	public SweetProperty getProperty() {return property;}
	
}
