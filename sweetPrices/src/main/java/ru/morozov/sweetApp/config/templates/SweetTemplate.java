package ru.morozov.sweetApp.config.templates;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;
import ru.morozov.sweetApp.config.PropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetPropertySet;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.config.prices.PriceItem;

import java.util.List;

public class SweetTemplate implements InitializingBean{
	
	private boolean isValidConfig = false;
	private String templateFileName;
	private Workbook workbook;
	private SweetPropertySet properties;
	private List<PriceItem> amounts;
	private CellCoord amountCoord;

	public SweetPropertySet getProperties() {return properties;}
	public void setProperties(SweetPropertySet properties) {this.properties = properties;}
	
	public String getTemplateFileName() {return templateFileName;}
	public void setTemplateFileName(String templateFileName) {this.templateFileName = templateFileName;}
	
	public List<PriceItem> getAmounts() {return amounts;}
	public void setAmounts(List<PriceItem> amounts) {this.amounts = amounts;}
	
	public boolean isValidConfig() {return isValidConfig;}
	public Workbook getWorkbook() {return workbook;}
	
	public CellCoord getAmount() {return amountCoord;}
	public void setAmount(CellCoord amount) {this.amountCoord = amount;}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (templateFileName == null || templateFileName.isEmpty())
			return;
		
		workbook = new HSSFWorkbook(getClass().getClassLoader().getResourceAsStream("xls/templates/" + templateFileName));
		
		isValidConfig = true;
	}
	
	public Workbook applyParams(PropertyValueSet values, Double amount) {
		Workbook newWorkbook = workbook;
		
		amountCoord.getCell(newWorkbook).setCellValue(amount);
		
		for(PropertyValue value : values.getValueSet())
			value.applyParam(workbook);
		
		if (newWorkbook instanceof HSSFWorkbook)
			HSSFFormulaEvaluator.evaluateAllFormulaCells(newWorkbook);
		else
			XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook) newWorkbook);
		
		return newWorkbook;
	}
}