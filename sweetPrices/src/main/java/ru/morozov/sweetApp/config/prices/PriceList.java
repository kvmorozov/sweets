package ru.morozov.sweetApp.config.prices;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;

import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.utils.ParserUtils;

public class PriceList implements InitializingBean {
	
	private String priceListFileName;
	private Workbook workbook;
	private boolean isValidConfig = false;
	private List<PriceItem> prices;
	private Map<String, PriceItem> pricesMap;
	
	public String getPriceListFileName() {return priceListFileName;}
	public void setPriceListFileName(String priceListFileName) {this.priceListFileName = priceListFileName;}
	
	public boolean isValidConfig() {return isValidConfig;}
	public Workbook getWorkbook() {return workbook;}
	
	public List<PriceItem> getPrices() {return prices;}
	public void setPrices(List<PriceItem> prices) {this.prices = prices;}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (priceListFileName == null || priceListFileName.isEmpty())
			return;
		
		try {
			workbook = new HSSFWorkbook(getClass().getClassLoader().getResourceAsStream("xls/prices/" + priceListFileName));
		} 
		catch (OfficeXmlFileException ox) {
			try {
				workbook = new XSSFWorkbook (getClass().getClassLoader().getResourceAsStream("xls/prices/" + priceListFileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pricesMap = new HashMap<String, PriceItem>();
		
		for(PriceItem price : prices) {
			CellCoord coord = price.getCoord();
			Cell cell = workbook.getSheetAt(coord.getSheet()).getRow(coord.getRow()).getCell(coord.getCol());
			
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					price.setPrice(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					price.setPrice(ParserUtils.getDoubleResult(cell.getStringCellValue()));
					break;
				default:
					break;
			}
			
			pricesMap.put(price.getItem().getItemName(), price);
		}
		
		isValidConfig = true;
	}
	
	public Double getPrice(String itemName) {return pricesMap.containsKey(itemName) ? pricesMap.get(itemName).getPrice() : 0;}
}