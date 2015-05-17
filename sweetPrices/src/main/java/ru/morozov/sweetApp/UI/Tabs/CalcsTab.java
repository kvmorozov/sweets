package ru.morozov.sweetApp.UI.Tabs;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.Utils.Constants.l12n;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetProduct;
import ru.morozov.sweetApp.config.SweetProperty;
import ru.morozov.sweetApp.config.SweetTemplate;

public class CalcsTab extends Tab {

	public CalcsTab(Stage stage) {
		super();

		setClosable(false);
		setText(l12n.bundle.getString(l12n.CALCS_KEY));

		ComboBox<SweetProduct> productsBox = new ComboBox<SweetProduct>(
				FXCollections.observableArrayList(SweetContext.getProducts()));

		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));

		grid.add(new Label(l12n.bundle.getString(l12n.PRODUCTS_KEY)), 0, 0);
		grid.add(productsBox, 1, 0);

		final TableView<PropertyValueSet> table = new TableView<PropertyValueSet>();

		productsBox.valueProperty().addListener(
				new ChangeListener<SweetProduct>() {
					@Override
					public void changed(ObservableValue ov, SweetProduct oldValue, SweetProduct newValue) {
						table.getColumns().clear();
						
						SweetTemplate template = newValue.getTemplate();
						for(SweetProperty property : template.getProperties().getProperties())
							table.getColumns().add(new TableColumn(property.getPropertyName()));
					}
				});

		grid.add(table, 0, 1, 3, 1);
		
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLS", "*.xls"));
		
		final Button openButton = new Button(l12n.bundle.getString(l12n.SELECT_PARAMS_KEY));
		
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            
                        }
                    }
                });
        
        grid.add(openButton, 3, 0);

		setContent(grid);
	}
}
