package ru.morozov.sweetApp.config.values;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.properties.SweetProperty;

/**
 * Created by km on 19.09.2015.
 */
public class DropDownPropertyValue extends AbstractPropertyValue {

    private SimpleObjectProperty<PriceItem> pItem;

    public DropDownPropertyValue (SimpleObjectProperty<PriceItem> pItem, SweetProperty property) {
        this.pItem = pItem;
        this.property = property;
    }

    @Override
    public Object getValue() {return pItem.get().toString();}

    @Override
    public boolean validate() {
        return pItem != null &&  pItem.get() != null && pItem.get().getName() != null && pItem.get().getName().length() > 0;
    }

    @Override
    public String asFormattedString() {return pItem.get().getName();}
}
