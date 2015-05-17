package ru.morozov.sweetApp.config;

import java.util.ArrayList;
import java.util.List;

public class PropertyValueSet {
	
	private List<PropertyValue> valueSet = new ArrayList<PropertyValue>();

	public List<PropertyValue> getValueSet() {
		return valueSet;
	}

	public void setValueSet(List<PropertyValue> valueSet) {
		this.valueSet = valueSet;
	}
	
	public void add(PropertyValue value) {valueSet.add(value);}
	public int size() {return valueSet.size();}
}
