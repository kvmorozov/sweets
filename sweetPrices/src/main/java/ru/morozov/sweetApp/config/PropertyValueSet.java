package ru.morozov.sweetApp.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ru.morozov.sweetApp.SweetContext;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
	public void setValue(String propertyKey, Double value) {
		if (internalMap.containsKey(propertyKey))
			internalMap.get(propertyKey).setValue(value);
	}
	
	public String getShortDesc() {
		StringBuilder sb = new StringBuilder();
		Iterator<PropertyValue> itr = valueSet.iterator();
		
		while(itr.hasNext()) {
			PropertyValue value = itr.next();
			sb.append(String.format("%.1f", value.getValue()).replaceAll(".0", "").replaceAll(",0", ""));
			if (itr.hasNext())
				sb.append("x");
		}
		
		return sb.toString();
	}
	
	public StringProperty getTotalPropertyStr() {return totalPropertyStr;}
	public StringProperty getCostPropertyStr() {return costPropertyStr;}
	public StringProperty getCostPropertyStrWithAdd() {return costPropertyStrWithAdd;}
	
	public static PropertyValueSet createValueSet(SweetPropertySet propertiesSet) {
		PropertyValueSet valueSet = new PropertyValueSet();
		
		for(SweetProperty property : propertiesSet.getProperties())
			valueSet.add(new PropertyValue(0d, property));
		
		return valueSet;
	}
}
