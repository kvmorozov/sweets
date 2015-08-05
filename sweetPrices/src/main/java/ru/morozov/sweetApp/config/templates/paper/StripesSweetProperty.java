package ru.morozov.sweetApp.config.templates.paper;

import java.util.List;

import ru.morozov.sweetApp.config.ComplexSweetProperty;
import ru.morozov.sweetApp.config.PropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetProperty;

public class StripesSweetProperty extends ComplexSweetProperty {
	
	private List<SweetProperty> dimensions;
	private Double realWidth;

	public List<SweetProperty> getDimensions() {return dimensions;}
	public void setDimensions(List<SweetProperty> dimensions) {this.dimensions = dimensions;}
	
	public Double getRealWidth() {return realWidth;}
	public void setRealWidth(Double realWidth) {this.realWidth = realWidth;}

	@Override
	public PropertyValue createPropertyValue(SweetProperty property, PropertyValueSet valueSet) {
		return new StripesPropertyValue(0d, property, valueSet);
	}
}
