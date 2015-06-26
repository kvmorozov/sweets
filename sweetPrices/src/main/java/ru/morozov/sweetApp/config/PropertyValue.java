package ru.morozov.sweetApp.config;

public class PropertyValue {
	
	private Double value;
	private SweetProperty property;
	
	public PropertyValue(Double value, SweetProperty property) {
		this.value = value;
		this.property = property;
	}

	public Double getValue() {return value;}
	public void setValue(Double value) {this.value = value;}
	
	public SweetProperty getProperty() {return property;}
}
