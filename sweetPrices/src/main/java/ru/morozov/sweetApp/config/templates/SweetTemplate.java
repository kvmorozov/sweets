package ru.morozov.sweetApp.config.templates;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;

import ru.morozov.sweetApp.config.PropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetPropertySet;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.utils.ParserUtils;

public class SweetTemplate implements InitializingBean{
	
	private boolean isValidConfig = false;
	private String templateFileName;
	private HSSFWorkbook workbook;
	private SweetPropertySet properties;
	private List<PriceItem> amounts;

	public SweetPropertySet getProperties() {return properties;}
	public void setProperties(SweetPropertySet properties) {this.properties = properties;}
	
	public String getTemplateFileName() {return templateFileName;}
	public void setTemplateFileName(String templateFileName) {this.templateFileName = templateFileName;}
	
	public List<PriceItem> getAmounts() {return amounts;}
	public void setAmounts(List<PriceItem> amounts) {this.amounts = amounts;}
	
	public boolean isValidConfig() {return isValidConfig;}
	public HSSFWorkbook getWorkbook() {return workbook;}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (templateFileName == null || templateFileName.isEmpty())
			return;
		
		workbook = new HSSFWorkbook(getClass().getClassLoader().getResourceAsStream("xls/templates/" + templateFileName));
		
		isValidConfig = true;
	}
	
	public Workbook applyParams(PropertyValueSet values) {
		Workbook newWorkbook = workbook;
		
		for(PropertyValue value : values.getValueSet()) {
			Cell cell = workbook.getSheetAt(0).getRow(value.getProperty().getCoord().getRow()).getCell(value.getProperty().getCoord().getCol());
			
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					cell.setCellValue(ParserUtils.getDoubleResult(value.getValue()));
					break;
				case Cell.CELL_TYPE_STRING:
					cell.setCellValue(value.getValue().toString());
					break;
				default:
					cell.setCellValue(value.getValue().toString());
					break;
			}
		}
		
		if (newWorkbook instanceof HSSFWorkbook)
			HSSFFormulaEvaluator.evaluateAllFormulaCells(newWorkbook);
		else
			XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook) newWorkbook);
		
		return newWorkbook;
	}
}
