package ru.morozov.sweetApp.generate;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.prices.PriceList;
import ru.morozov.sweetApp.config.templates.SweetTemplate;

public class BaseSweetGenerator {
	
	private final static SimpleDateFormat subDirDateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	
	private SystemConfigs systemConfig;
	private SweetTemplate template;
	private ParametersHolder parametersHolder;
	private List<Double> totals;
	
	public SystemConfigs getSystemConfig() {return systemConfig;}
	public SweetTemplate getTemplate() {return template;}
	public ParametersHolder getParametersHolder() {return parametersHolder;}
	public List<Double> getTotals() {return totals;}
	
	public BaseSweetGenerator(SystemConfigs systemConfig, SweetTemplate template, ParametersHolder parametersHolder) {
		this.systemConfig = systemConfig;
		this.template = template;
		this.parametersHolder = parametersHolder;
	}
	
	public boolean generate() {
		String outputSubDirName = subDirDateFormat.format(new Date());
		Path outputPath = systemConfig.createSubdirectory(outputSubDirName);
		
		totals = new ArrayList<Double>();
		PriceList priceList = SweetContext.getPriceList();
		
		for(PropertyValueSet params : parametersHolder.getParameters()) {
			Workbook generatedWorkbook = template.applyParams(params);
			
			if (generatedWorkbook instanceof HSSFWorkbook)
				HSSFFormulaEvaluator.evaluateAllFormulaCells(generatedWorkbook);
			else
				XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook) generatedWorkbook);
			
			Double total = 0d;
			
			for(PriceItem amount : template.getAmounts()) {
				Double price = priceList.getPrice(amount.getItem().getItemName());
				Cell cell = generatedWorkbook.getSheetAt(0).getRow(amount.getCoord().getRow()).getCell(amount.getCoord().getCol());
				
				if (cell != null)
					total+=cell.getNumericCellValue() * price;
			}
			
			Path newPath = Paths.get(outputPath.toString(), params.getShortDesc() + ".xls");
			
			try {
				Path newFilePath = Files.createFile(newPath);
				FileOutputStream out = new FileOutputStream(newFilePath.toString());
				generatedWorkbook.write(out);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				
				return false;
			}
			
			params.setTotal(total);
			totals.add(total);
		}
		
		parametersHolder.invalidate();
		
		try {
			Desktop.getDesktop().open(new File(outputPath.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
