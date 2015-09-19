package ru.morozov.sweetApp.config;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.morozov.sweetApp.config.base.IPriceProducer;
import ru.morozov.sweetApp.config.prices.PriceItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        Collection<PriceItem> priceItems = new ArrayList<>();

        List<String> cellConstraints = getHolder().getPropertiesHolder().getCellConstraints(
                getHolder().getPropertiesHolder().getCell(getCoord()));

        if (cellConstraints != null && cellConstraints.size() > 0)
            for (String _stringItem : cellConstraints)
                priceItems.add(new PriceItem(_stringItem));

        itemList.removeAll();
        itemList.addAll(priceItems);
    }
}
