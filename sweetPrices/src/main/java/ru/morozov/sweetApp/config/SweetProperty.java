package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.config.prices.PriceList;

public class SweetProperty {
	
	private String propertyName;
	private CellCoord coord;
	private PriceList priceList;
	
	public String getPropertyName() {return propertyName;}
	public void setPropertyName(String propertyName) {this.propertyName = propertyName;}
	
	public CellCoord getCoord() {return coord;}
	public void setCoord(CellCoord coord) {this.coord = coord;}
	
	public PriceList getPriceList() {return priceList;}
	public void setPriceList(PriceList priceList) {this.priceList = priceList;}
}
