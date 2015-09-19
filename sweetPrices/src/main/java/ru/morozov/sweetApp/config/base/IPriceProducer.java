package ru.morozov.sweetApp.config.base;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import ru.morozov.sweetApp.config.prices.PriceItem;

/**
 * Created by km on 18.09.2015.
 */
public interface IPriceProducer {

    public ObservableList<PriceItem> getItemList();
    public SimpleObjectProperty<PriceItem> getCurrentItem();
    public void refresh();
}
