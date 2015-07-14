package ru.morozov.sweetApp.config.prices;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import ru.morozov.sweetApp.Utils.Constants.l12n;
import ru.morozov.sweetApp.config.base.CellCoord;

public class PriceItem {
	
	protected SweetItem item;
	protected Double price, price1 = 0d, price2 = 0d;
	protected CellCoord coord, addInfoCoord;
	protected String desc, name;
	protected Map<String, Object> itemProperties;
	protected PriceList itemProvider;
	
	public PriceItem() {}
	
	public PriceItem(int row) {
		coord = new CellCoord(row);
	}
	
	public CellCoord getCoord() {return coord;}
	public CellCoord getCoord(int column) {return new CellCoord(coord.getSheet(), column, coord.getRow());}
	
	public void setCoord(CellCoord coord) {this.coord = coord;}
	
	public SweetItem getItem() {return item;}
	public void setItem(SweetItem item) {this.item = item;}
	
	public Double getPrice() {return price == null || price <= 0 ? Math.max(price1, price2) : price;}
	public void setPrice(Double price) {this.price = price;}
	
	public Double getPrice1() {return price1;}
	public void setPrice1(Double price1) {this.price1 = price1;}
	
	public Double getPrice2() {return price2;}
	public void setPrice2(Double price2) {this.price2 = price2;}
	
	public String getDesc() {return desc;}
	public void setDesc(String desc) {this.desc = desc;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public Map<String, Object> getItemProperties() {return itemProperties;}
	public void setItemProperties(Map<String, Object> itemProperties) {this.itemProperties = itemProperties;}
	
	public PriceList getItemProvider() {return itemProvider;}
	public void setItemProvider(PriceList itemProvider) {this.itemProvider = itemProvider;}
	
	public CellCoord getAddInfoCoord() {return addInfoCoord;}
	public void setAddInfoCoord(CellCoord addInfoCoord) {this.addInfoCoord = addInfoCoord;}

	@Override
	public String toString() {return desc;}
	
	public static PriceItem getEmptyPrice() {
		PriceItem emptyPrice = new PriceItem();
		
		emptyPrice.setDesc(l12n.bundle.getString(l12n.NO_VALUE));
		emptyPrice.setPrice(-1d);
		
		return emptyPrice;
	}
	
	public Double getAmount(Workbook workbook) {
		Cell cell = getCoord().getCell(workbook);
		
		return cell == null ? 0d : cell.getNumericCellValue();
	}
	
	public Double getTotal(PriceItem amountItem, Workbook workbook) {
		Double amount = amountItem.getAmount(workbook);
		
		return (getPrice() == null ? 0d : getPrice()) * (amount == null ? 0d : amount);
	}
}