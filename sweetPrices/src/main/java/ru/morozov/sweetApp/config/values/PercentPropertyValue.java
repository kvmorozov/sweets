package ru.morozov.sweetApp.config.values;

import ru.morozov.sweetApp.config.properties.SweetProperty;

/**
 * Created by km on 20.09.2015.
 */
public class PercentPropertyValue extends DoublePropertyValue {

    public PercentPropertyValue(SweetProperty property) {
        super(0d, property);
    }

    @Override
    public Double getValue() {return value.get() / 100;}

    @Override
    public boolean validate() {return false;}
}
