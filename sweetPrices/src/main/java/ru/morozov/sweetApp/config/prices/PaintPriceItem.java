package ru.morozov.sweetApp.config.prices;

import org.apache.poi.ss.usermodel.Workbook;

public class PaintPriceItem extends PriceItem {
	
	public PaintPriceItem() {super();}
	public PaintPriceItem(int row) {super(row);}

	@Override
	public Double getTotal(PriceItem amountItem, Workbook workbook) {
		return 0d;
	}
}
