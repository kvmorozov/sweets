package ru.morozov.sweetApp.config.values;

import javafx.beans.property.SimpleStringProperty;
import org.apache.poi.ss.usermodel.Cell;
import ru.morozov.sweetApp.config.properties.SweetProperty;
import ru.morozov.utils.ParserUtils;
import ru.morozov.utils.components.xls.XlsFile;

/**
 * Created by km on 19.09.2015.
 */
public abstract class AbstractPropertyValue {

    protected SweetProperty property;
    public SimpleStringProperty strValueProperty = new SimpleStringProperty();

    public SweetProperty getProperty() {return property;}

    public abstract Object getValue();
    public abstract boolean validate();
    public abstract String asFormattedString();

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

        applyParam(property.getCoord().getCell(xlsFile), getValue());
    }
}
