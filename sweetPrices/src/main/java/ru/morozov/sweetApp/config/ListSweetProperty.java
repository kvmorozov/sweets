package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.config.prices.PriceList;

public class ListSweetProperty extends SweetProperty {
	
	private PriceList priceList;
	
	public PriceList getPriceList() {return priceList;}
	public void setPriceList(PriceList priceList) {this.priceList = priceList;}

}
