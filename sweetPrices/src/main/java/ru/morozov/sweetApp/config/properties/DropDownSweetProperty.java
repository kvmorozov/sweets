package ru.morozov.sweetApp.config.properties;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.base.IPriceProducer;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.values.AbstractPropertyValue;
import ru.morozov.sweetApp.config.values.DropDownPropertyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by km on 17.09.2015.
 */
public class DropDownSweetProperty extends SweetProperty implements IPriceProducer {

    private SimpleObjectProperty<PriceItem> currentItem = new SimpleObjectProperty<>();
    private ObservableList<PriceItem> itemList = FXCollections.observableArrayList(new ArrayList<>());

    @Override
    public ObservableList<PriceItem> getItemList() {return itemList;}

    @Override
    public SimpleObjectProperty<PriceItem> getCurrentItem() {return currentItem;}

    @Override
    public void refresh() {
        ArrayList<PriceItem> priceItems = new ArrayList<>();

        List<String> cellConstraints = getHolder().getPropertiesHolder().getCellConstraints(
                getHolder().getPropertiesHolder().getCell(getCoord()));

        if (cellConstraints != null && cellConstraints.size() > 0)
            priceItems.addAll(cellConstraints.stream().map(PriceItem::new).collect(Collectors.toList()));

        if (priceItems.size() > 0)
            currentItem = new SimpleObjectProperty<>(priceItems.get(0));

        itemList.clear();
        itemList.addAll(priceItems);
    }

    @Override
    public AbstractPropertyValue createPropertyValue(PropertyValueSet valueSet) {
        return new DropDownPropertyValue(currentItem, this);
    }
}
