package ru.morozov.sweetApp.config;

public class CalculatedPropertyValue extends PropertyValue {
	
	protected PropertyValueSet valueSet;

	protected CalculatedPropertyValue(Double value, SweetProperty property) {super(value, property);}
	
	public CalculatedPropertyValue(Double value, SweetProperty property, PropertyValueSet valueSet) {
		super(value, property);
		this.valueSet = valueSet;
	}

	public PropertyValue createPropertyValue(SweetProperty property, PropertyValueSet valueSet) {
		return new PropertyValue(0d, property);
	}
}
