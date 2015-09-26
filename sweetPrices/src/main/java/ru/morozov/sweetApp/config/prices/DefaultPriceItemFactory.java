package ru.morozov.sweetApp.config.prices;

public class DefaultPriceItemFactory implements PriceItemFactory {

	@Override
	public PriceItem createItem(int row) {return new PriceItem(row);}

}
