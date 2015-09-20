package ru.morozov.sweetApp.config.properties;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.config.base.FilterCondition;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.prices.PriceList;
import ru.morozov.sweetApp.config.values.AbstractPropertyValue;
import ru.morozov.sweetApp.config.values.FilterPropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by km on 20.09.2015.
 */
public class FilteredListProperty extends ListSweetProperty {

    private List<FilterCondition> conditions;
    private ObservableList<PriceItem> filteredList = FXCollections.observableArrayList();
    private PriceItem _emptyItem = PriceItem.getEmptyPrice();
    private SimpleObjectProperty<PriceItem> currentItem = new SimpleObjectProperty(_emptyItem);

    public void setConditions(List<FilterCondition> conditions) {this.conditions = conditions;}

    @Override
    public ObservableList<PriceItem> getItemList() {return filteredList;}

    @Override
    public SimpleObjectProperty<PriceItem> getCurrentItem() {return currentItem;}

    @Override
    public void refresh() {
        PriceList _pList = (PriceList) getPriceList();

        ArrayList<PriceItem> _filterArray = new ArrayList<>();
        _filterArray.add(_emptyItem);

        for (PriceItem _pItem : _pList.getPrices()) {
            boolean accepted = true;
            StringBuilder sb = new StringBuilder();

            for (FilterCondition condition : conditions) {
                CellCoord _itemCell = _pItem.getCoord();
                if (_itemCell == null)
                    continue;

                _itemCell.setCol(condition.getColumn());

                String _cellValue = _pList.getPriceListFile().getStringValue(_itemCell);
                if (condition.getFilterProperty().getCurrentItem().get() != null &&
                        !condition.getFilterProperty().getCurrentItem().get().getDesc().equals(_cellValue))
                    accepted = false;
                else {
                    if (sb.length() > 0)
                        sb.append("x");
                    sb.append(_cellValue);
                }
            }

            if (accepted && sb.length() > 0 && _pItem.getPrice() > 0) {
                PriceItem pItem = new PriceItem(sb.append("x").append(_pItem.getDesc()).toString());
                pItem.setPrice(_pItem.getPrice());
                _filterArray.add(pItem);
            }
        }

        filteredList.clear();
        filteredList.addAll(_filterArray);
    }

    @Override
    public AbstractPropertyValue createPropertyValue(PropertyValueSet valueSet) {
        return new FilterPropertyValue(this);
    }
}
