package ru.morozov.sweetApp.config;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.morozov.sweetApp.config.base.IPriceProducer;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.prices.PriceList;

public class ListSweetProperty extends SweetProperty implements IPriceProducer {
	
	private PriceList priceList;
	
	public PriceList getPriceList() {return priceList;}
	public void setPriceList(PriceList priceList) {this.priceList = priceList;}

    @Override
    public ObservableList<PriceItem> getItemList() {return FXCollections.observableArrayList(getPriceList().getPrices());}

    @Override
    public SimpleObjectProperty<PriceItem> getCurrentItem() {return getPriceList().currentItem;}

    @Override
    public void refresh() {}
}
