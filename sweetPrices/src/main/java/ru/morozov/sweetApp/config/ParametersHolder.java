package ru.morozov.sweetApp.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ru.morozov.utils.ParserUtils;

public class ParametersHolder {

	private SweetPropertySet propertiesSet;
	private List<PropertyValueSet> parameters;
	private ObservableList<PropertyValueSet> observableParameters;
	private boolean isParamsValid = false;

	private Workbook workbook;
	
	public ParametersHolder(SweetPropertySet propertiesSet) {
		this.propertiesSet = propertiesSet;
		parameters = new ArrayList<PropertyValueSet>(1);
		parameters.add(PropertyValueSet.createValueSet(propertiesSet));
		
		observableParameters = FXCollections.observableArrayList(parameters);
	}
	
	public ParametersHolder(String resourcePath, SweetPropertySet propertiesSet) {
		this.propertiesSet = propertiesSet;
		
		try {
			workbook = new HSSFWorkbook(getClass().getClassLoader().getResourceAsStream(resourcePath));
		} 
		catch (OfficeXmlFileException ox) {
			try {
				workbook = new XSSFWorkbook (getClass().getClassLoader().getResourceAsStream(resourcePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getParamsFromWorkbook();
		validateParams();
	}
	
	public ParametersHolder(File file, SweetProduct product) {
		this.propertiesSet = product.getTemplate().getProperties();
		
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} 
		catch (OfficeXmlFileException ox) {
			try {
				workbook = new XSSFWorkbook (new FileInputStream(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		getParamsFromWorkbook();
		validateParams();
	}

	private void getParamsFromWorkbook() {
		if (workbook == null)
			return;

		parameters = new ArrayList<PropertyValueSet>();

		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			PropertyValueSet paramsSet = new PropertyValueSet();
			paramsSet.setRow(row.getRowNum());

			Iterator<Cell> cellIterator = row.iterator();
			int index = 0;

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				SweetProperty property = propertiesSet.getProperty(index);
				if (property == null)
					break;

				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						paramsSet.add(new PropertyValue(cell.getNumericCellValue(), property));
						break;
					case Cell.CELL_TYPE_STRING:
						paramsSet.add(new PropertyValue(ParserUtils.getDoubleResult(cell.getStringCellValue()), property));
						break;
					default:
						break;
				}
				
				index++;
			}
			
			parameters.add(paramsSet);
		}
	}
	
	private void validateParams() {
		for(PropertyValueSet paramSet : parameters)
			if (paramSet.getValueSet().size() != propertiesSet.getProperties().size())
				return;
		
		isParamsValid = true;
	}

	public List<PropertyValueSet> getParameters() {return parameters;}
	public ObservableList<PropertyValueSet> getObservableParameters() {
		return observableParameters = FXCollections.observableArrayList(parameters);
	}
	
	public void invalidate() {
		if (observableParameters != null) {
			observableParameters.clear();
			ObservableList<PropertyValueSet> observableParameters_ = FXCollections.observableArrayList(parameters);
			observableParameters.addAll(observableParameters_);
		}
	}

	public boolean isParamsValid() {return isParamsValid;}
	public Workbook getWorkbook() {return workbook;}
}