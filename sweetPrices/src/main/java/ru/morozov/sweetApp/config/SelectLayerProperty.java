package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.config.templates.paper.SelectLayerPropertyValue;

/**
 * Created by km on 04.08.2015.
 */
public class SelectLayerProperty extends ListSweetProperty {

    private CellCoord paperCoord;

    public CellCoord getPaperCoord() {return paperCoord;}
    public void setPaperCoord(CellCoord paperCoord) {this.paperCoord = paperCoord;}

    @Override
    public PropertyValue createPropertyValue(SweetProperty property, PropertyValueSet valueSet) {
        return new SelectLayerPropertyValue(0d, property, valueSet);
    }
}
