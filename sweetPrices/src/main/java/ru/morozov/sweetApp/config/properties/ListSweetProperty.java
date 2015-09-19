package ru.morozov.sweetApp.config.properties;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.base.IPriceProducer;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.prices.SelectList;
import ru.morozov.sweetApp.config.values.AbstractPropertyValue;
import ru.morozov.sweetApp.config.values.ListPropertyValue;

public class ListSweetProperty extends SweetProperty implements IPriceProducer {
	
	private SelectList priceList;
	
	public SelectList getPriceList() {return priceList;}
	public void setPriceList(SelectList priceList) {this.priceList = priceList;}

    @Override
    public ObservableList<PriceItem> getItemList() {return FXCollections.observableArrayList(getPriceList().getPrices());}

    @Override
    public SimpleObjectProperty<PriceItem> getCurrentItem() {return getPriceList().currentItem;}

    @Override
    public void refresh() {}

    @Override
    public AbstractPropertyValue createPropertyValue(PropertyValueSet valueSet) {
        return new ListPropertyValue(this);
    }
}
