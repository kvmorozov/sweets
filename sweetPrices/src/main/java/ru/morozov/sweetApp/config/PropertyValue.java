package ru.morozov.sweetApp.config;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.poi.ss.usermodel.Cell;
import ru.morozov.utils.ParserUtils;
import ru.morozov.utils.components.xls.XlsFile;

public class PropertyValue {

	protected SweetProperty property;
	
	public SimpleDoubleProperty value;
	public SimpleStringProperty strValueProperty = new SimpleStringProperty();
	
	public PropertyValue(Double value, SweetProperty property) {
		this.value = new SimpleDoubleProperty(value);
		this.property = property;
		
		strValueProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty())
                setValue(Double.valueOf(newValue));
        });
	}

	public Double getValue() {return value.get();}
	private void setValue(Double value) {this.value.set(value);}
	
	public SweetProperty getProperty() {return property;}

	protected void applyParam(Cell cell, Object applyValue) {
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				cell.setCellValue(ParserUtils.getDoubleResult(applyValue));
				break;
			case Cell.CELL_TYPE_STRING:
				cell.setCellValue(applyValue.toString());
				break;
			default:
				cell.setCellValue(applyValue.toString());
				break;
		}
	}

	public void applyParam(XlsFile xlsFile) {
		if (getProperty().getCoord() == null)
			return;

		applyParam(getProperty().getCoord().getCell(xlsFile), value.getValue());
	}
}
