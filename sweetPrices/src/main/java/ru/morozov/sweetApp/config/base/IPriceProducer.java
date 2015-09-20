package ru.morozov.sweetApp.config.base;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import ru.morozov.sweetApp.config.prices.PriceItem;

/**
 * Created by km on 18.09.2015.
 */
public interface IPriceProducer {

    ObservableList<PriceItem> getItemList();
    SimpleObjectProperty<PriceItem> getCurrentItem();
    void refresh();
}
