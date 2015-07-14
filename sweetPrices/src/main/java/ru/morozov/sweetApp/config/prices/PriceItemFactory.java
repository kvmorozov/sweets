package ru.morozov.sweetApp.config.prices;

public interface PriceItemFactory {
	
	public PriceItem createItem();
	public PriceItem createItem(int row);
	
}
