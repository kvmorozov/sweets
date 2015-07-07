package ru.morozov.sweetApp.config.prices;

import java.util.List;

public class PricesSet {
	
	private List<PriceList> priceLists;

	public List<PriceList> getPriceLists() {return priceLists;}
	public void setPriceLists(List<PriceList> priceLists) {this.priceLists = priceLists;}
	
	public Double getPrice(String itemName) {
		Double result = 0d;
		
		for(PriceList priceList : priceLists) {
			Double _result = priceList.getPrice(itemName);
			if (_result > 0)
				return _result;
		}
		
		return result;
	}
}
