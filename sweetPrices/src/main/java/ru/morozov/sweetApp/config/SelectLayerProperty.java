package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.config.properties.ListSweetProperty;
import ru.morozov.sweetApp.config.values.DoublePropertyValue;
import ru.morozov.sweetApp.config.values.SelectLayerPropertyValue;

/**
 * Created by km on 04.08.2015.
 */
public class SelectLayerProperty extends ListSweetProperty {

    private CellCoord paperCoord;

    public CellCoord getPaperCoord() {return paperCoord;}
    public void setPaperCoord(CellCoord paperCoord) {this.paperCoord = paperCoord;}

    @Override
    public DoublePropertyValue createPropertyValue(PropertyValueSet valueSet) {
        return new SelectLayerPropertyValue(0d, this, valueSet);
    }
}
