package ru.morozov.sweetApp.config;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.morozov.sweetApp.config.properties.SweetProperty;
import ru.morozov.sweetApp.config.values.AbstractPropertyValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyValueSet {
	
	private List<AbstractPropertyValue> valueSet = new ArrayList<>();
	private Map<String, AbstractPropertyValue> internalMap = new HashMap<>();
	private Double total;
	private int row;
	private StringProperty totalPropertyStr = new SimpleStringProperty(""),
						   costPropertyStr = new SimpleStringProperty(""),
						   costPropertyStrWithAdd = new SimpleStringProperty("");

	public List<AbstractPropertyValue> getValueSet() {return valueSet;}
	public void setValueSet(List<AbstractPropertyValue> valueSet) {this.valueSet = valueSet;}
	
	public Double getTotal() {return total;}
	public void setTotal(Double total, Double amount) {
		this.total = total;
		
		Double cost = total / amount;
		Double costWithAdd = cost * (1 + ((SystemConfigs.extraChargeProperty.get() + 0d) / 100));
		
		totalPropertyStr.setValue(String.format("₽%,.2f", total));
		costPropertyStr.setValue(String.format("₽%,.2f", cost));
		costPropertyStrWithAdd.setValue(String.format("₽%,.2f", costWithAdd));
	}
	
	public int getRow() {return row;}
	public void setRow(int row) {this.row = row;}
	
	public void add(AbstractPropertyValue value) {
        if (value != null && value.getProperty() != null && value.getProperty().getPropertyName() != null) {
            valueSet.add(value);
            internalMap.put(value.getProperty().getPropertyName(), value);
        }
	}
	
	public int size() {return valueSet.size();}
	
	public Object getValue(String propertyKey) {return internalMap.containsKey(propertyKey) ? internalMap.get(propertyKey).getValue() : null;}
	public StringProperty getValueStrProperty(String propertyKey) {return internalMap.containsKey(propertyKey) ? internalMap.get(propertyKey).strValueProperty : null;}
	
	public String getShortDesc() {
		StringBuilder sb = new StringBuilder();

		for (AbstractPropertyValue value : valueSet) {
			if (value.getProperty().getCoord() != null && value.validate()) {
				if (sb.length() > 0)
					sb.append("x");
				sb.append(value.asFormattedString());
			}
		}
		
		return sb.toString().length() <= 20 ? sb.toString() : sb.toString().substring(0, 20);
	}
	
	public StringProperty getTotalPropertyStr() {return totalPropertyStr;}
	public StringProperty getCostPropertyStr() {return costPropertyStr;}
	public StringProperty getCostPropertyStrWithAdd() {return costPropertyStrWithAdd;}
	
	public static PropertyValueSet createValueSet(SweetPropertySet propertiesSet) {
		PropertyValueSet valueSet = new PropertyValueSet();
		
		for(SweetProperty property : propertiesSet.getProperties())
			valueSet.add(property.createPropertyValue(valueSet));

		return valueSet;
	}
}
