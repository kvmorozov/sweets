package ru.morozov.sweetApp.config.properties;

import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.values.CalculatedPropertyValue;
import ru.morozov.sweetApp.config.values.DoublePropertyValue;

public class ComplexSweetProperty extends SweetProperty {

    @Override
    public DoublePropertyValue createPropertyValue(PropertyValueSet valueSet) {
        return new CalculatedPropertyValue(0d, this, valueSet);
    }

}
