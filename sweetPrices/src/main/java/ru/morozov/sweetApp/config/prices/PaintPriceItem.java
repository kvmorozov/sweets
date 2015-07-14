package ru.morozov.sweetApp.config.prices;

import org.apache.poi.ss.usermodel.Workbook;

import ru.morozov.utils.ParserUtils;

public class PaintPriceItem extends PriceItem {
	
	private static final String KEY_PAINT_DENSITY = "density";
	private static final String KEY_PAPER_ORIGINAL_WIDTH = "originalWidth";
	private static final String KEY_PAPER_REAL_WIDTH = "realWidth";
	
	public PaintPriceItem() {super();}
	public PaintPriceItem(int row) {super(row);}

	@Override
	public Double getTotal(PriceItem amountItem, Workbook workbook) {
		Double paintDesity = 0d, paperDensity = 0d, paperOriginalWidth = 0d, paperRealWidth = 0d;
		Double paperWeight = 0d, paintPrice = 0d;
		
		if (amountItem.addInfoCoord != null)
			paperDensity = amountItem.addInfoCoord.getDoubleValue(workbook);
		
		if (this.itemProperties != null && this.itemProperties.containsKey(KEY_PAINT_DENSITY))
			paintDesity = ParserUtils.getDoubleResult(this.itemProperties.get(KEY_PAINT_DENSITY));
		
		if (amountItem.itemProperties != null && amountItem.itemProperties.containsKey(KEY_PAPER_ORIGINAL_WIDTH))
			paperOriginalWidth = ParserUtils.getDoubleResult(amountItem.itemProperties.get(KEY_PAPER_ORIGINAL_WIDTH));
		
		if (amountItem.itemProperties != null && amountItem.itemProperties.containsKey(KEY_PAPER_REAL_WIDTH))
			paperRealWidth = ParserUtils.getDoubleResult(amountItem.itemProperties.get(KEY_PAPER_REAL_WIDTH));
		
		paintPrice = getPrice();
		paperWeight = amountItem.getAmount(workbook);
		
		return (paperWeight / paperDensity) * (paperRealWidth / paperOriginalWidth) * paintDesity * paintPrice;
	}
}
