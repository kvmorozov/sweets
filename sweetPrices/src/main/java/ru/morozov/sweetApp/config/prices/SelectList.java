package ru.morozov.sweetApp.config.prices;

import javafx.beans.property.SimpleObjectProperty;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created by km on 19.09.2015.
 */
public class SelectList {

    protected List<PriceItem> prices;

    public SimpleObjectProperty<PriceItem> currentItem = new SimpleObjectProperty<>();

    public List<PriceItem> getPrices() {return prices;}
    public void setPrices(List<PriceItem> prices) {this.prices = prices;}
}
