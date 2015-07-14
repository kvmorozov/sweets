package ru.morozov.sweetApp.config.templates.paper;

import ru.morozov.sweetApp.config.CalculatedPropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetProperty;

public class StripesPropertyValue extends CalculatedPropertyValue {

	public StripesPropertyValue(Double value, SweetProperty property) {super(value, property);}
	public StripesPropertyValue(Double value, SweetProperty property, PropertyValueSet valueSet) {super(value, property, valueSet);}
	
	@Override
	public Double getValue() {
		StripesSweetProperty stripesProperty = (StripesSweetProperty) property;
		
		Double minTrunk = Double.MAX_VALUE;
		Double resultStripes = 1d;
		
		for(SweetProperty dimension : stripesProperty.getDimensions()) {
			Double dimensionValue = valueSet.getValue(dimension.getPropertyName());
			Double stripes = Math.floor(stripesProperty.getRealWidth() / dimensionValue);
			Double trunk = stripesProperty.getRealWidth() - stripes * dimensionValue;
			
			if (trunk < minTrunk) {
				minTrunk = trunk;
				resultStripes = stripes;
			}
		}
		
		return resultStripes;
	}

}
