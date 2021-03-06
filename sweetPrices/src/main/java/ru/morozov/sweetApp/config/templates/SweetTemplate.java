package ru.morozov.sweetApp.config.templates;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetPropertySet;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.values.AbstractPropertyValue;
import ru.morozov.sweetApp.generate.BaseSweetGenerator;
import ru.morozov.sweetApp.generate.IGenerator;
import ru.morozov.utils.components.xls.XlsFile;

import java.util.List;

public class SweetTemplate {
	
	private XlsFile templateFile;
	private SweetPropertySet properties;
	private List<PriceItem> amounts;
	private CellCoord amountCoord;

	public SweetPropertySet getProperties() {return properties;}
	public void setProperties(SweetPropertySet properties) {this.properties = properties;}
	
	public XlsFile getTemplateFile() {return templateFile;}
	public void setTemplateFile(XlsFile templateFile) {this.templateFile = templateFile;}
	
	public List<PriceItem> getAmounts() {return amounts;}
	public void setAmounts(List<PriceItem> amounts) {this.amounts = amounts;}
	
	public boolean isValidConfig() {return templateFile.isValidConfig();}
	public Workbook getWorkbook() {return templateFile.getWorkbook();}
	
	public CellCoord getAmount() {return amountCoord;}
	public void setAmount(CellCoord amount) {this.amountCoord = amount;}
	
	public XlsFile applyParams(PropertyValueSet values, Double amount) {
        XlsFile newTemplate = templateFile;

		amountCoord.getCell(newTemplate).setCellValue(amount);

		for(AbstractPropertyValue value : values.getValueSet())
			value.applyParam(templateFile);

		if (templateFile.getWorkbook() instanceof HSSFWorkbook)
			HSSFFormulaEvaluator.evaluateAllFormulaCells(newTemplate.getWorkbook());
		else
			XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook) newTemplate.getWorkbook());

		return newTemplate;
	}

    public IGenerator getGenerator(SystemConfigs systemConfig, ParametersHolder parametersHolder, Double amount) {
        return new BaseSweetGenerator(systemConfig, this, parametersHolder, amount);
    }
}