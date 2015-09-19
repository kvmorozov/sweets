package ru.morozov.sweetApp.config.values;

import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.properties.SweetProperty;

public class CalculatedPropertyValue extends DoublePropertyValue {
	
	protected PropertyValueSet valueSet;

	protected CalculatedPropertyValue(Double value, SweetProperty property) {super(value, property);}
	
	public CalculatedPropertyValue(Double value, SweetProperty property, PropertyValueSet valueSet) {
		super(value, property);
		this.valueSet = valueSet;
	}

	public DoublePropertyValue createPropertyValue(SweetProperty property, PropertyValueSet valueSet) {
		return new DoublePropertyValue(0d, property);
	}
}
