package ru.morozov.sweetApp.config.prices;

public interface PriceItemFactory {
	
	PriceItem createItem();
	PriceItem createItem(int row);
	
}
