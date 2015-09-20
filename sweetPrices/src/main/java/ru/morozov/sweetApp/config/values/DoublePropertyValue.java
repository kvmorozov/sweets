package ru.morozov.sweetApp.config.values;

import javafx.beans.property.SimpleDoubleProperty;
import ru.morozov.sweetApp.config.properties.SweetProperty;

public class DoublePropertyValue extends AbstractPropertyValue {

	protected SimpleDoubleProperty value;

    public DoublePropertyValue(SweetProperty property) {
        this(0d, property);
    }

	public DoublePropertyValue(Double value, SweetProperty property) {
		this.value = new SimpleDoubleProperty(value);
		this.property = property;
		
		strValueProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty())
                setValue(Double.valueOf(newValue));
        });
	}

    @Override
	public Double getValue() {return value.get();}

    @Override
    public boolean validate() {return value != null && value.get() > 0;}

    @Override
    public String asFormattedString() {
        Double _value = value.getValue();

        return _value == _value.longValue() ? String.format("%d", _value.longValue()) : String.format("%s", _value);
    }

    private void setValue(Double value) {this.value.set(value);}
}
