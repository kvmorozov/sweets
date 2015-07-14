package ru.morozov.sweetApp.config.prices;

public class PaintPriceItemFactory implements PriceItemFactory {

	@Override
	public PriceItem createItem() {return new PaintPriceItem();}
	
	@Override
	public PriceItem createItem(int row) {return new PaintPriceItem(row);}

}
