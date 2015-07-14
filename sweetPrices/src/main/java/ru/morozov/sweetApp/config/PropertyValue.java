package ru.morozov.sweetApp.config;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PropertyValue {

	protected SweetProperty property;
	
	public SimpleDoubleProperty value;
	public SimpleStringProperty strValueProperty = new SimpleStringProperty();
	
	public PropertyValue(Double value, SweetProperty property) {
		this.value = new SimpleDoubleProperty(value);
		this.property = property;
		
		strValueProperty.addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	if (newValue != null && !newValue.isEmpty())
		    		setValue(Double.valueOf(newValue));
		    }
		});
	}

	public Double getValue() {return value.get();}
	private void setValue(Double value) {this.value.set(value);}
	
	public SweetProperty getProperty() {return property;}
}
