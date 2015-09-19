package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.config.base.CellCoord;

public class SweetProperty {
	
	private String propertyName;
	private CellCoord coord;
    private SweetPropertySet holder;
	
	public String getPropertyName() {return propertyName;}
	public void setPropertyName(String propertyName) {this.propertyName = propertyName;}
	
	public CellCoord getCoord() {return coord;}
	public void setCoord(CellCoord coord) {this.coord = coord;}

    public SweetPropertySet getHolder() {return holder;}
    public void setHolder(SweetPropertySet holder) {this.holder = holder;}

    public PropertyValue createPropertyValue(SweetProperty property, PropertyValueSet valueSet) {
		return new PropertyValue(0d, property);
	}
}
