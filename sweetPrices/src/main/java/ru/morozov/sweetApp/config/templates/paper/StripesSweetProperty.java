package ru.morozov.sweetApp.config.templates.paper;

import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.properties.ComplexSweetProperty;
import ru.morozov.sweetApp.config.properties.SweetProperty;
import ru.morozov.sweetApp.config.values.DoublePropertyValue;

import java.util.List;

public class StripesSweetProperty extends ComplexSweetProperty {
	
	private List<SweetProperty> dimensions;
	private Double realWidth;

	public List<SweetProperty> getDimensions() {return dimensions;}
	public void setDimensions(List<SweetProperty> dimensions) {this.dimensions = dimensions;}
	
	public Double getRealWidth() {return realWidth;}
	public void setRealWidth(Double realWidth) {this.realWidth = realWidth;}

	@Override
	public DoublePropertyValue createPropertyValue(PropertyValueSet valueSet) {
		return new StripesPropertyValue(0d, this, valueSet);
	}
}
