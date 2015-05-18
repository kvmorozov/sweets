package ru.morozov.sweetApp.config.prices;

import ru.morozov.sweetApp.config.base.CellCoord;

public class PriceItem {
	
	private SweetItem item;
	private Double price;
	private CellCoord coord;
	
	public CellCoord getCoord() {return coord;}
	public void setCoord(CellCoord coord) {this.coord = coord;}
	
	public SweetItem getItem() {return item;}
	public void setItem(SweetItem item) {this.item = item;}
	
	public Double getPrice() {return price;}
	public void setPrice(Double price) {this.price = price;}

}
