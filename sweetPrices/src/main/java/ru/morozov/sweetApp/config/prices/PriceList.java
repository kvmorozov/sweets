package ru.morozov.sweetApp.config.prices;

import org.springframework.beans.factory.InitializingBean;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.utils.ParserUtils;
import ru.morozov.utils.components.xls.XlsFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PriceList extends SelectList implements InitializingBean {
	
	private XlsFile priceListFile;

	private Map<String, PriceItem> pricesMap;
	private Integer descColumn, nameColumn, price1Column, price2Column, firstRow, lastRow, densityColumn = -1;
	private Map<String, Object> itemProperties;
	private PriceItemFactory itemFactory;

    public XlsFile getPriceListFile() {return priceListFile;}
    public void setPriceListFile(XlsFile priceListFileName) {this.priceListFile = priceListFileName;}
	
	public boolean isValidConfig() {return priceListFile.isValidConfig();}

	public void setDescColumn(int descColumn) {this.descColumn = descColumn;}
	public void setNameColumn(int nameColumn) {this.nameColumn = nameColumn;}
	public void setPrice1Column(int price1Column) {this.price1Column = price1Column;}
	public void setPrice2Column(int price2Column) {this.price2Column = price2Column;}
    public Integer getFirstRow() {return firstRow;}
    public Integer getLastRow() {return lastRow;}
    public void setFirstRow(int firstRow) {this.firstRow = firstRow;}
	public void setLastRow(int lastRow) {this.lastRow = lastRow;}
	public void setItemProperties(Map<String, Object> itemProperties) {this.itemProperties = itemProperties;}
	public void setItemFactory(PriceItemFactory itemFactory) {this.itemFactory = itemFactory;}
	public void setDensityColumn(int densityColumn) {this.densityColumn = densityColumn;}

	@Override
	public void afterPropertiesSet() throws Exception {
		pricesMap = new HashMap<>();

		if (prices != null)
			for(PriceItem price : prices) {
                if (price1Column != null)
				    price.setPrice1(ParserUtils.getDoubleResult(price.getCoord(price1Column).getDoubleValue(priceListFile)));
                if (price2Column != null)
				    price.setPrice2(ParserUtils.getDoubleResult(price.getCoord(price2Column).getDoubleValue(priceListFile)));
                if (descColumn != null)
				    price.setDesc((new ParserUtils(price.getCoord(descColumn).getStringValue(priceListFile))).getStringResult());
                if (nameColumn != null)
				    price.setName((new ParserUtils(price.getCoord(nameColumn).getStringValue(priceListFile))).getStringResult());

				if (densityColumn >= 0)
					price.setDensity(ParserUtils.getDoubleResult(price.getCoord(densityColumn).getDoubleValue(priceListFile)));
				
				pricesMap.put(price.getItem().getItemName(), price);
			}
		
		if (firstRow != null && lastRow != null) {
			prices = new ArrayList<>();
			
			prices.add(PriceItem.getEmptyPrice());
			
			for(int row = firstRow; row < lastRow; row++) {
				PriceItem price = (itemFactory == null ? itemFactory = new DefaultPriceItemFactory() : itemFactory).createItem(row);
				
				Double price1 = price1Column == null ? null : ParserUtils.getDoubleResult(price.getCoord(price1Column).getDoubleValue(priceListFile));
				Double price2 = price2Column == null ? null : ParserUtils.getDoubleResult(price.getCoord(price2Column).getDoubleValue(priceListFile));
				String desc = descColumn == null ? null : price.getCoord(descColumn).getStringValue(priceListFile);
				String name = nameColumn == null ? null : price.getCoord(nameColumn).getStringValue(priceListFile);
				
				price.setItem(new SweetItem(name));
				
				price.setPrice1(price1);
				price.setPrice2(price2);
				price.setDesc(desc);
				price.setName(name);
                price.setCoord(new CellCoord(priceListFile.getDefaultSheet(), price1Column, row));
				
				if (itemProperties != null && !itemProperties.isEmpty())
					price.setItemProperties(itemProperties);
				
				prices.add(price);
				pricesMap.put(price.getItem().getItemName(), price);
			}
		}
	}
	
	public Double getPrice(String itemName) {return pricesMap.containsKey(itemName) ? pricesMap.get(itemName).getPrice() : 0;}
	public PriceItem getPriceItem(String itemName) {return pricesMap.containsKey(itemName) ? pricesMap.get(itemName) : null;}
}