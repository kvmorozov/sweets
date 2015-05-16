package ru.morozov.sweetApp.UI.Tabs;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.Utils.Constants.l12n;
import ru.morozov.sweetApp.config.SweetProduct;

public class CalcsTab extends Tab {
	
	public CalcsTab() {
		super();
		
		setClosable(false);
		setText(l12n.bundle.getString(l12n.CALCS_KEY));
		
		ComboBox<SweetProduct> productsBox = new ComboBox<SweetProduct>(FXCollections.observableArrayList(SweetContext.getProducts()));
		
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        grid.add(new Label(l12n.bundle.getString(l12n.PRODUCTS_KEY)), 0, 0);
        grid.add(productsBox, 1, 0);
        
        final TableView table = new TableView();
        
		setContent(grid);
	}
}
