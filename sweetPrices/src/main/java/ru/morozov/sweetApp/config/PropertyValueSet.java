package ru.morozov.sweetApp.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.config.templates.paper.StripesPropertyValue;
import ru.morozov.sweetApp.config.templates.paper.StripesSweetProperty;

public class PropertyValueSet {
	
	private List<PropertyValue> valueSet = new ArrayList<PropertyValue>();
	private Map<String, PropertyValue> internalMap = new HashMap<String, PropertyValue>();
	private Double total;
	private int row;
	private StringProperty totalPropertyStr = new SimpleStringProperty(""),
						   costPropertyStr = new SimpleStringProperty(""),
						   costPropertyStrWithAdd = new SimpleStringProperty("");

	public List<PropertyValue> getValueSet() {return valueSet;}
	public void setValueSet(List<PropertyValue> valueSet) {this.valueSet = valueSet;}
	
	public Double getTotal() {return total;}
	public void setTotal(Double total, Double amount) {
		this.total = total;
		
		Double cost = total / amount;
		Double costWithAdd = cost * (1 + ((SweetContext.getSystemConfigs().getExtraCharge() + 0d) / 100));
		
		totalPropertyStr.setValue(String.format("₽%,.2f", total));
		costPropertyStr.setValue(String.format("₽%,.2f", cost));
		costPropertyStrWithAdd.setValue(String.format("₽%,.2f", costWithAdd));
	}
	
	public int getRow() {return row;}
	public void setRow(int row) {this.row = row;}
	
	public void add(PropertyValue value) {
		valueSet.add(value);
		internalMap.put(value.getProperty().getPropertyName(), value);
	}
	
	public int size() {return valueSet.size();}
	
	public Double getValue(String propertyKey) {return internalMap.containsKey(propertyKey) ? internalMap.get(propertyKey).getValue() : null;}
	public StringProperty getValueStrProperty(String propertyKey) {return internalMap.containsKey(propertyKey) ? internalMap.get(propertyKey).strValueProperty : null;}
	
	public String getShortDesc() {
		StringBuilder sb = new StringBuilder();
		Iterator<PropertyValue> itr = valueSet.iterator();
		
		while(itr.hasNext()) {
			PropertyValue value = itr.next();
			
			Double _value = value.getValue();
			
			if (value.getProperty().getCoord() != null && value.getValue() > 0) {
				if (sb.length() > 0)
					sb.append("x");
				sb.append(_value == _value.longValue() ?  String.format("%d", _value.longValue()) : String.format("%s", _value));
			}
		}
		
		return sb.toString();
	}
	
	public StringProperty getTotalPropertyStr() {return totalPropertyStr;}
	public StringProperty getCostPropertyStr() {return costPropertyStr;}
	public StringProperty getCostPropertyStrWithAdd() {return costPropertyStrWithAdd;}
	
	public static PropertyValueSet createValueSet(SweetPropertySet propertiesSet) {
		PropertyValueSet valueSet = new PropertyValueSet();
		
		for(SweetProperty property : propertiesSet.getProperties())
			if (property instanceof StripesSweetProperty)
				valueSet.add(new StripesPropertyValue(0d, property, valueSet));
			else if (property instanceof ComplexSweetProperty)
				valueSet.add(new CalculatedPropertyValue(0d, property, valueSet));
			else
				valueSet.add(new PropertyValue(0d, property));
		
		return valueSet;
	}
}
