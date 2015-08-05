package ru.morozov.sweetApp.config.templates.paper;

import org.apache.poi.ss.usermodel.Workbook;
import ru.morozov.sweetApp.config.CalculatedPropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SelectLayerProperty;
import ru.morozov.sweetApp.config.SweetProperty;
import ru.morozov.sweetApp.config.prices.PriceItem;

/**
 * Created by km on 04.08.2015.
 */
public class SelectLayerPropertyValue extends CalculatedPropertyValue {

    public SelectLayerPropertyValue(Double value, SweetProperty property) {super(value, property);}
    public SelectLayerPropertyValue(Double value, SweetProperty property, PropertyValueSet valueSet) {super(value, property, valueSet);}

    @Override
    public void applyParam(Workbook workbook) {
        if (getProperty().getCoord() == null)
            return;

        SelectLayerProperty property = (SelectLayerProperty) getProperty();
        PriceItem selectedPaper = property.getPriceList().currentItem.get();

        applyParam(property.getCoord().getCell(workbook), selectedPaper.getDensity());
        applyParam(property.getPaperCoord().getCell(workbook), selectedPaper.getName());
    }
}