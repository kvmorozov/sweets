package ru.morozov.sweetApp.generate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValue;
import ru.morozov.sweetApp.config.SweetTemplate;
import ru.morozov.sweetApp.config.SystemConfigs;

public class BaseSweetGenerator {
	
	private final static SimpleDateFormat subDirDateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	
	private SystemConfigs systemConfig;
	private SweetTemplate template;
	private ParametersHolder parametersHolder;
	
	public SystemConfigs getSystemConfig() {return systemConfig;}
	public SweetTemplate getTemplate() {return template;}
	public ParametersHolder getParametersHolder() {return parametersHolder;}

	public BaseSweetGenerator(SystemConfigs systemConfig, SweetTemplate template, ParametersHolder parametersHolder) {
		this.systemConfig = systemConfig;
		this.template = template;
		this.parametersHolder = parametersHolder;
	}
	
	public boolean generate() {
		String outputSubDirName = subDirDateFormat.format(new Date());
		Path outputPath = systemConfig.createSubdirectory(outputSubDirName);
		
		int index = 0;
		
		for(List<PropertyValue> params : parametersHolder.getParameters()) {
			Workbook generatedWorkbook = template.applyParams(params);
			
			index++;
			
			Path newPath = Paths.get(outputPath.toString(), String.valueOf(index) + ".xls");
			
			try {
				Path newFilePath = Files.createFile(newPath);
				FileOutputStream out = new FileOutputStream(newFilePath.toString());
				generatedWorkbook.write(out);
				out.close();
				
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
}
