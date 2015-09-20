package ru.morozov.sweetApp.config.values;

import ru.morozov.sweetApp.config.properties.ListSweetProperty;

/**
 * Created by km on 19.09.2015.
 */
public class ListPropertyValue extends AbstractPropertyValue  {

    public ListPropertyValue(ListSweetProperty property) {
        this.property = property;
    }

    @Override
    public Object getValue() {return ((ListSweetProperty)property).getPriceList().currentItem.get().getPrice();}

    @Override
    public boolean validate() {return false;}

    @Override
    public String asFormattedString() {return "";}
}
