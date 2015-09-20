package ru.morozov.sweetApp.config.properties;

import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.values.AbstractPropertyValue;
import ru.morozov.sweetApp.config.values.PercentPropertyValue;

/**
 * Created by km on 20.09.2015.
 */
public class PercentProperty extends SweetProperty {

    public AbstractPropertyValue createPropertyValue(PropertyValueSet valueSet) {
        return new PercentPropertyValue(this);
    }
}
