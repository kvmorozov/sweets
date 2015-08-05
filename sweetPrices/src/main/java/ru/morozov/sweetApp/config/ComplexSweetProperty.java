package ru.morozov.sweetApp.config;

public class ComplexSweetProperty extends SweetProperty {

    @Override
    public PropertyValue createPropertyValue(SweetProperty property, PropertyValueSet valueSet) {
        return new CalculatedPropertyValue(0d, property, valueSet);
    }

}
