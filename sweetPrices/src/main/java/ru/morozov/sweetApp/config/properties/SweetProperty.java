package ru.morozov.sweetApp.config.properties;

import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetPropertySet;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.config.values.AbstractPropertyValue;
import ru.morozov.sweetApp.config.values.DoublePropertyValue;

public class SweetProperty {
	
	private String propertyName;
	private CellCoord coord;
    private SweetPropertySet holder;
    private Integer minValue, maxValue, defaultValue;
	
	public String getPropertyName() {return propertyName;}
	public void setPropertyName(String propertyName) {this.propertyName = propertyName;}
	
	public CellCoord getCoord() {return coord;}
	public void setCoord(CellCoord coord) {this.coord = coord;}

    public SweetPropertySet getHolder() {return holder;}
    public void setHolder(SweetPropertySet holder) {this.holder = holder;}

    public Integer getMinValue() {return minValue;}
    public void setMinValue(Integer minValue) {this.minValue = minValue;}

    public Integer getMaxValue() {return maxValue;}
    public void setMaxValue(Integer maxValue) {this.maxValue = maxValue;}

    public Integer getDefaultValue() {return defaultValue;}
    public void setDefaultValue(Integer defaultValue) {this.defaultValue = defaultValue;}

    public AbstractPropertyValue createPropertyValue(PropertyValueSet valueSet) {
		return new DoublePropertyValue(0d, this);
	}
}
