package ru.morozov.sweetApp.config.values;

import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SelectLayerProperty;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.properties.SweetProperty;
import ru.morozov.utils.components.xls.XlsFile;

/**
 * Created by km on 04.08.2015.
 */
public class SelectLayerPropertyValue extends CalculatedPropertyValue {

    public SelectLayerPropertyValue(Double value, SweetProperty property, PropertyValueSet valueSet) {super(value, property, valueSet);}

    @Override
    public void applyParam(XlsFile xlsFile) {
        if (getProperty().getCoord() == null)
            return;

        SelectLayerProperty property = (SelectLayerProperty) getProperty();
        PriceItem selectedPaper = property.getPriceList().currentItem.get();

        applyParam(property.getCoord().getCell(xlsFile), selectedPaper.getDensity());
        applyParam(property.getPaperCoord().getCell(xlsFile), selectedPaper.getName());
    }
}
