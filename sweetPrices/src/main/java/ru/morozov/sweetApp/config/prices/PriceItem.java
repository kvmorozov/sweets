package ru.morozov.sweetApp.config.prices;

import org.apache.poi.ss.usermodel.Cell;
import ru.morozov.sweetApp.Utils.Constants.l12n;
import ru.morozov.sweetApp.config.properties.ListSweetProperty;
import ru.morozov.sweetApp.config.properties.SweetProperty;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.utils.components.xls.XlsFile;

import java.util.Map;

public class PriceItem {
	
	protected SweetItem item;
	protected Double price, price1 = -1d, price2 = -1d, density;
	protected CellCoord coord, addInfoCoord;
	protected String desc, name;
	protected Map<String, Object> itemProperties;
	protected PriceList itemProvider;
	protected SweetProperty referenceProperty;
	
	public PriceItem() {}

    public PriceItem(String desc) {this.desc = desc;}
	
	public PriceItem(int row) {
		coord = new CellCoord(row);
	}
	
	public CellCoord getCoord() {return coord;}
	public CellCoord getCoord(int column) {return new CellCoord(coord.getSheet(), column, coord.getRow());}
	
	public void setCoord(CellCoord coord) {this.coord = coord;}
	
	public SweetItem getItem() {return item;}
	public void setItem(SweetItem item) {this.item = item;}
	
	public Double getPrice() {return price == null || price <= 0 ?
            price1 != null && price2 != null ? Double.max(price1, price2) : price1 : price;}
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

	public Double getDensity() {return density;}
	public void setDensity(Double density) {this.density = density;}

	public SweetProperty getReferenceProperty() {return referenceProperty;}
	public void setReferenceProperty(SweetProperty referenceProperty) {this.referenceProperty = referenceProperty;}

	@Override
	public String toString() {
        return (desc == null ? name : desc) +
               (getPrice() != null && getPrice() >= 0 ? " - " + getPrice().toString() : "");
    }
	
	public static PriceItem getEmptyPrice() {
		PriceItem emptyPrice = new PriceItem();
		
		emptyPrice.setDesc(l12n.bundle.getString(l12n.NO_VALUE));
		emptyPrice.setPrice(-1d);
		
		return emptyPrice;
	}
	
	public Double getAmount(XlsFile xlsFile) {
		Cell cell = getCoord().getCell(xlsFile);
		
		return cell == null ? 0d : cell.getNumericCellValue();
	}
	
	public Double getTotal(PriceItem amountItem, XlsFile xlsFile) {
		Double amount = amountItem.getAmount(xlsFile);
		
		return getPrice() * amount;
	}

	public PriceItem getPrice(PricesSet appPrices) {
		if (getItem() != null)
			return appPrices.getPriceItem(getItem().getItemName());
		else if (referenceProperty != null && referenceProperty instanceof ListSweetProperty)
			return ((ListSweetProperty) referenceProperty).getPriceList().currentItem.get();
		else if (getItemProvider() != null)
			return getItemProvider().currentItem.get();
		else
			return null;
	}
}