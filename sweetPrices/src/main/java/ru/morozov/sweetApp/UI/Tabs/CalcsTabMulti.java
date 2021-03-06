package ru.morozov.sweetApp.UI.Tabs;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.Utils.Constants.l12n;
import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetProduct;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.properties.SweetProperty;
import ru.morozov.sweetApp.config.templates.SweetTemplate;
import ru.morozov.sweetApp.generate.IGenerator;
import ru.morozov.utils.components.NumberTextField;

import java.io.File;

public class CalcsTabMulti extends Tab implements ICalcsTab {

	public void initCalcsTab(Stage stage) {
		setClosable(false);
		setText(l12n.bundle.getString(l12n.CALCS_KEY));

		ComboBox<SweetProduct> productsBox = new ComboBox<>(FXCollections.observableArrayList(SweetContext.getProducts()));

		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(6, 6, 6, 6));

		grid.add(new Label(l12n.bundle.getString(l12n.PRODUCTS_KEY)), 0, 0, 2, 1);
		grid.add(productsBox, 2, 0);

		final TableView<PropertyValueSet> table = new TableView<>();
		
		final Button openButton = new Button(l12n.bundle.getString(l12n.SELECT_PARAMS_KEY));
		openButton.setDisable(true);

		productsBox.valueProperty().addListener(
				new ChangeListener<SweetProduct>() {
					@Override
					public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, SweetProduct oldValue, SweetProduct newValue) {
						table.getColumns().clear();
						table.getItems().clear();
						
						SweetTemplate template = newValue.getTemplate();
						for(SweetProperty property : template.getProperties().getProperties()) {
							TableColumn<PropertyValueSet, Double> column = new TableColumn<>(property.getPropertyName());
							
							column.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>((Double)p.getValue().getValue(property.getPropertyName())));
							
							column.setCellFactory(col -> new TableCell<PropertyValueSet, Double>() {
								@Override 
						        public void updateItem(Double value, boolean empty) {
						            super.updateItem(value, empty);
						            setText(empty ? null : String.format("%.1f", value));
								}
							});
							
							table.getColumns().add(column);
						}
						
						TableColumn<PropertyValueSet, Double> column = new TableColumn<>(l12n.bundle.getString(l12n.TOTAL_KEY));
						
						column.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getTotal()));
						
						column.setCellFactory(col -> new TableCell<PropertyValueSet, Double>() {
							@Override 
							public void updateItem(Double total, boolean empty) {
								super.updateItem(total, empty);
								setText(empty ? null : String.format("₽%,.2f", total));
							}
						});
						
						table.getColumns().add(column);
						column.setVisible(false);
						
						openButton.setDisable(false);
					}
				});

		grid.add(table, 0, 1, 6, 1);
		
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel files", "*.xls*"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLS", "*.xls"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
		
		String initDirName = SweetContext.getSystemConfigs().getSystemProperty(SystemConfigs.PROPERTY_LAST_PARAMS_FILE);
		File initDir = initDirName == null ? null : new File(initDirName);
		if (initDir != null)
			fileChooser.setInitialDirectory(initDir);
		
        final Button runButton = new Button(l12n.bundle.getString(l12n.RUN_KEY));
        runButton.setDisable(true);
		
        openButton.setOnAction((e) -> {
        	File file = fileChooser.showOpenDialog(stage);
        	if (file != null) {
        		SweetContext.getSystemConfigs().setSystemProperty(SystemConfigs.PROPERTY_LAST_PARAMS_FILE, file.getParent());
        		fileChooser.setInitialDirectory(file.getParentFile());

        		ParametersHolder holder = new ParametersHolder(file, productsBox.getValue());
        		if (holder.isParamsValid()) {
        			table.setItems(holder.getObservableParameters());
        			table.setUserData(holder);
        			runButton.setDisable(false);
        		}
        		else {
        			// Show alert
        		}
        	}
        });
        
        grid.add(openButton, 3, 0, 2, 1);
        grid.add(new Label(l12n.bundle.getString(l12n.AMOUNT_KEY)), 0, 2, 2, 1);
        
        final NumberTextField amountInput = new NumberTextField();
        amountInput.setText(SweetContext.getSystemConfigs().getSystemProperty(SystemConfigs.PROPERTY_LAST_AMOUNT));
        
        runButton.setOnAction((e) -> {
        	SweetContext.getSystemConfigs().setSystemProperty(SystemConfigs.PROPERTY_LAST_AMOUNT, amountInput.getText());
            IGenerator generator = productsBox.getValue().getTemplate().getGenerator(SweetContext.getSystemConfigs(),
                    (ParametersHolder)table.getUserData(), amountInput.getDoubleValue());
        	if (generator.generate())
        		table.getColumns().get(table.getColumns().size() - 1).setVisible(true);
        });
        
        grid.add(amountInput, 2, 2);
        grid.add(runButton, 3, 2, 1, 1);

		setContent(grid);
	}
}