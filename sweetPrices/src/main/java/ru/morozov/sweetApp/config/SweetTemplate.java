package ru.morozov.sweetApp.config;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.InitializingBean;

import ru.morozov.utils.ParserUtils;

public class SweetTemplate implements InitializingBean{
	
	private boolean isValidConfig = false;
	private String templateFileName;
	private HSSFWorkbook workbook;
	private SweetPropertySet properties;

	public SweetPropertySet getProperties() {return properties;}
	public void setProperties(SweetPropertySet properties) {this.properties = properties;}
	
	public String getTemplateFileName() {return templateFileName;}
	public void setTemplateFileName(String templateFileName) {this.templateFileName = templateFileName;}
	
	public boolean isValidConfig() {return isValidConfig;}
	public HSSFWorkbook getWorkbook() {return workbook;}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (templateFileName == null || templateFileName.isEmpty())
			return;
		
		workbook = new HSSFWorkbook(getClass().getClassLoader().getResourceAsStream("xls/templates/" + templateFileName));
		
		isValidConfig = true;
	}
	
	public Workbook applyParams(List<PropertyValue> values) {
		HSSFWorkbook newWorkbook = workbook;
		
		for(PropertyValue value : values) {
			Cell cell = workbook.getSheetAt(0).getRow(value.getProperty().getRow()).getCell(value.getProperty().getCol());
			
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
		
		return newWorkbook;
	}
}
