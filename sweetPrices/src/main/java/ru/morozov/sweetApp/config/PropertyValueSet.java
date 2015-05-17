package ru.morozov.sweetApp.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyValueSet {
	
	private List<PropertyValue> valueSet = new ArrayList<PropertyValue>();
	private Map<String, PropertyValue> internalMap = new HashMap<String, PropertyValue>();;

	public List<PropertyValue> getValueSet() {return valueSet;}
	public void setValueSet(List<PropertyValue> valueSet) {this.valueSet = valueSet;}
	
	public void add(PropertyValue value) {
		valueSet.add(value);
		internalMap.put(value.getProperty().getPropertyName(), value);
	}
	
	public int size() {return valueSet.size();}
	
	public Double getValue(String propertyKey) {return internalMap.containsKey(propertyKey) ? internalMap.get(propertyKey).getValue() : null;}
}
