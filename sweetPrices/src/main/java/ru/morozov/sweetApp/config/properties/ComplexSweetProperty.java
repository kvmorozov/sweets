package ru.morozov.sweetApp.config.properties;

import ru.morozov.sweetApp.config.values.CalculatedPropertyValue;
import ru.morozov.sweetApp.config.values.DoublePropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;

public class ComplexSweetProperty extends SweetProperty {

    @Override
    public DoublePropertyValue createPropertyValue(PropertyValueSet valueSet) {
        return new CalculatedPropertyValue(0d, this, valueSet);
    }

}
