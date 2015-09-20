package ru.morozov.sweetApp.config.base;

/**
 * Created by km on 20.09.2015.
 */
public class FilterCondition {

    private int column;
    private IPriceProducer filterProperty;
    private String oldValue;

    public int getColumn() {return column;}
    public void setColumn(int column) {this.column = column;}

    public IPriceProducer getFilterProperty() {return filterProperty;}
    public void setFilterProperty(IPriceProducer filterProperty) {this.filterProperty = filterProperty;}

    public String getOldValue() {return oldValue;}
    public void setOldValue(String oldValue) {this.oldValue = oldValue;}
}
