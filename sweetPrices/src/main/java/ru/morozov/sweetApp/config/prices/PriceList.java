package ru.morozov.sweetApp.config.prices;

import javafx.beans.property.SimpleObjectProperty;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.InitializingBean;
import ru.morozov.utils.ParserUtils;
import ru.morozov.utils.components.xls.XlsFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceList implements InitializingBean {
	
	private XlsFile priceListFile;
	private List<PriceItem> prices;
	private Map<String, PriceItem> pricesMap;
	private int descColumn, nameColumn, price1Column, price2Column, firstRow, lastRow, densityColumn = -1;
	private Map<String, Object> itemProperties;
	private PriceItemFactory itemFactory;
	
	public SimpleObjectProperty<PriceItem> currentItem = new SimpleObjectProperty<>();
	
	public XlsFile getPriceListFile() {return priceListFile;}
	public void setPriceListFile(XlsFile priceListFileName) {this.priceListFile = priceListFileName;}
	
	public boolean isValidConfig() {return priceListFile.isValidConfig();}
	public Workbook getWorkbook() {return priceListFile.getWorkbook();}
	
	public List<PriceItem> getPrices() {return prices;}
	public void setPrices(List<PriceItem> prices) {this.prices = prices;}
	
	public int getDescColumn() {return descColumn;}
	public void setDescColumn(int descColumn) {this.descColumn = descColumn;}
	
	public int getNameColumn() {return nameColumn;}
	public void setNameColumn(int nameColumn) {this.nameColumn = nameColumn;}
	
	public int getPrice1Column() {return price1Column;}
	public void setPrice1Column(int price1Column) {this.price1Column = price1Column;}
	
	public int getPrice2Column() {return price2Column;}
	public void setPrice2Column(int price2Column) {this.price2Column = price2Column;}
	
	public int getFirstRow() {return firstRow;}
	public void setFirstRow(int firstRow) {this.firstRow = firstRow;}
	
	public int getLastRow() {return lastRow;}
	public void setLastRow(int lastRow) {this.lastRow = lastRow;}
	
	public Map<String, Object> getItemProperties() {return itemProperties;}
	public void setItemProperties(Map<String, Object> itemProperties) {this.itemProperties = itemProperties;}
	
	public PriceItemFactory getItemFactory() {return itemFactory;}
	public void setItemFactory(PriceItemFactory itemFactory) {this.itemFactory = itemFactory;}

	public int getDensityColumn() {return densityColumn;}
	public void setDensityColumn(int densityColumn) {this.densityColumn = densityColumn;}

	@Override
	public void afterPropertiesSet() throws Exception {
		pricesMap = new HashMap<>();
        Workbook workbook = getWorkbook();
		
		if (prices != null)
			for(PriceItem price : prices) {
				price.setPrice1(ParserUtils.getDoubleResult(price.getCoord(price1Column).getDoubleValue(workbook)));
				price.setPrice1(ParserUtils.getDoubleResult(price.getCoord(price2Column).getDoubleValue(workbook)));
				price.setDesc((new ParserUtils(price.getCoord(descColumn).getStringValue(workbook))).getStringResult());
				price.setName((new ParserUtils(price.getCoord(nameColumn).getStringValue(workbook))).getStringResult());

				if (densityColumn >= 0)
					price.setDensity(ParserUtils.getDoubleResult(price.getCoord(densityColumn).getDoubleValue(workbook)));
				
				pricesMap.put(price.getItem().getItemName(), price);
			}
		
		if (firstRow > 0 && lastRow > 0) {
			prices = new ArrayList<>();
			
			prices.add(PriceItem.getEmptyPrice());
			
			for(int row = firstRow; row < lastRow; row++) {
				PriceItem price = (itemFactory == null ? itemFactory = new DefaultPriceItemFactory() : itemFactory).createItem(row);
				
				Double price1 = ParserUtils.getDoubleResult(price.getCoord(price1Column).getDoubleValue(workbook));
				Double price2 = ParserUtils.getDoubleResult(price.getCoord(price2Column).getDoubleValue(workbook));
				String desc = price.getCoord(descColumn).getStringValue(workbook);
				String name = price.getCoord(nameColumn).getStringValue(workbook);
				
				price.setItem(new SweetItem(name));
				
				price.setPrice1(price1);
				price.setPrice1(price2);
				price.setDesc(desc);
				price.setName(name);
				
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