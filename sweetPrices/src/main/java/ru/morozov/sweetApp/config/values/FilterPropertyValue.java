package ru.morozov.sweetApp.config.values;

import ru.morozov.sweetApp.config.properties.FilteredListProperty;

/**
 * Created by km on 19.09.2015.
 */
public class FilterPropertyValue extends AbstractPropertyValue  {

    public FilterPropertyValue(FilteredListProperty property) {
        this.property = property;
    }

    @Override
    public Object getValue() {return ((FilteredListProperty)property).getCurrentItem().get().getPrice();}

    @Override
    public boolean validate() {return false;}

    @Override
    public String asFormattedString() {return "";}
}
