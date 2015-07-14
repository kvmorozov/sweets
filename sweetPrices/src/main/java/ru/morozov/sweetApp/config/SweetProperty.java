package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.config.base.CellCoord;

public class SweetProperty {
	
	private String propertyName;
	private CellCoord coord;
	
	public String getPropertyName() {return propertyName;}
	public void setPropertyName(String propertyName) {this.propertyName = propertyName;}
	
	public CellCoord getCoord() {return coord;}
	public void setCoord(CellCoord coord) {this.coord = coord;}

}
