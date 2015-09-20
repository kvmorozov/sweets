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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.prices.PricesSet;
import ru.morozov.sweetApp.config.templates.SweetTemplate;
import ru.morozov.utils.components.xls.XlsFile;

public class BaseSweetGenerator {
	
	private final static SimpleDateFormat subDirDateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	
	private SystemConfigs systemConfig;
	private SweetTemplate template;
	private ParametersHolder parametersHolder;
    private Double amount;
	
	private static final Double DEFAULT_AMOUNT = 10000d;
	
	public SystemConfigs getSystemConfig() {return systemConfig;}

	public BaseSweetGenerator(SystemConfigs systemConfig, SweetTemplate template, ParametersHolder parametersHolder) {
		this.systemConfig = systemConfig;
		this.template = template;
		this.parametersHolder = parametersHolder;
		this.amount = DEFAULT_AMOUNT;
	}
	
	public BaseSweetGenerator(SystemConfigs systemConfig, SweetTemplate template, ParametersHolder parametersHolder, Double amount) {
		this.systemConfig = systemConfig;
		this.template = template;
		this.parametersHolder = parametersHolder;
		this.amount = amount;
	}
	
	public boolean generate() {
		String outputSubDirName = subDirDateFormat.format(new Date());
		Path outputPath = systemConfig.createSubdirectory(outputSubDirName);
		
		Workbook resultBook = parametersHolder.getWorkbook();

        List<Double> totals = new ArrayList<>();
		PricesSet appPrices = SweetContext.getPricesSet();
		
		for(PropertyValueSet params : parametersHolder.getParameters()) {
			XlsFile generatedTemplate = template.applyParams(params, amount);
			
			Double total = 0d;

            if (template.getAmounts() != null)
                for(PriceItem amount : template.getAmounts()) {
                    PriceItem price = amount.getPrice(appPrices);

                    try {
                        if (price != null)
                            total += price.getTotal(amount, generatedTemplate);
                    }
                    catch(Exception ex) {
                        System.out.println("Ошибка вычисления!");
                    }
                }
			
			if (outputPath != null) {
				Path newPath = Paths.get(outputPath.toString(), params.getShortDesc() + ".xls");
				
				try {
					Path newFilePath = Files.createFile(newPath);
					FileOutputStream out = new FileOutputStream(newFilePath.toString());
					generatedTemplate.getWorkbook().write(out);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					
					return false;
				}
			}
			
			if (resultBook != null)
				try {
					Cell totalCell = resultBook.getSheetAt(0).getRow(params.getRow()).getCell(params.getValueSet().size());
					if (totalCell == null)
						totalCell = resultBook.getSheetAt(0).getRow(params.getRow()).createCell(params.getValueSet().size(), Cell.CELL_TYPE_NUMERIC);
					totalCell.setCellValue(total);
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			
			params.setTotal(total, amount);
			totals.add(total);
		}
		
		parametersHolder.invalidate();
		
		try {
			if (outputPath != null && resultBook != null && SweetContext.getSystemConfigs().isGenerateFiles())
				resultBook.write(new FileOutputStream(outputPath.toString() +
						"/Результаты.xls" + (resultBook instanceof XSSFWorkbook ? "x" : "")));
			
			if (outputPath != null && SweetContext.getSystemConfigs().isPopupWindow())
				Desktop.getDesktop().open(new File(outputPath.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
