package ru.morozov.sweetApp.UI.Tabs;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.Utils.Constants.l12n;
import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.SweetProduct;
import ru.morozov.sweetApp.config.SweetProperty;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.templates.SweetTemplate;
import ru.morozov.sweetApp.generate.BaseSweetGenerator;
import ru.morozov.utils.components.NumberTextField;

public class CalcsTabSingle extends Tab implements ICalcsTab {
	
	private final List<NumberTextField> valueFieldList = new ArrayList<NumberTextField>(1);

	public void initCalcsTab(Stage stage) {
		setClosable(false);
		setText(l12n.bundle.getString(l12n.CALCS_KEY));

		BorderPane borderPane = new BorderPane();
		borderPane.prefHeightProperty().bind(getTabPane().heightProperty());
		borderPane.prefWidthProperty().bind(getTabPane().widthProperty());
		
		HBox topBox = new HBox();
		ComboBox<SweetProduct> productsBox = new ComboBox<SweetProduct>(FXCollections.observableArrayList(SweetContext.getProducts()));
		topBox.getChildren().addAll(new Label(l12n.bundle.getString(l12n.PRODUCTS_KEY)), productsBox);
		topBox.setSpacing(10);
		borderPane.setTop(topBox);

		final GridPane gridProps = new GridPane();
		gridProps.setMaxHeight(Double.MAX_VALUE);
		gridProps.setPadding(new Insets(5, 10, 5, 10));
		
		ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
		ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        gridProps.getColumnConstraints().addAll(col1, col2);
		
		final Button runButton = new Button(l12n.bundle.getString(l12n.RUN_KEY));

		productsBox.valueProperty().addListener(new ChangeListener<SweetProduct>() {
			@Override
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, SweetProduct oldValue, SweetProduct newValue) {
				gridProps.getChildren().clear();

				SweetTemplate template = newValue.getTemplate();
				final ParametersHolder pHolder = new ParametersHolder(template.getProperties());
						
				int rowIndex = 0;
				valueFieldList.clear();

				for(SweetProperty property : template.getProperties().getProperties()) {
					NumberTextField valueInput = new NumberTextField();
					valueFieldList.add(valueInput);
					
					valueInput.textProperty().addListener(new ChangeListener<String>() {
					    @Override
					    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					    	if (newValue != null && !newValue.isEmpty())
					    		pHolder.getParameters().get(0).setValue(property.getPropertyName(), Double.valueOf(newValue));
					    	
					    	boolean runEnabled = true;
					    	for(NumberTextField input : valueFieldList)
					    		if (input.getText().trim().isEmpty()) {
					    			runEnabled = false;
					    			break;
					    		}
					    	
					    	runButton.setDisable(!runEnabled);
					    }
					});
							

					gridProps.add(new Label(property.getPropertyName() + ":"), 0, rowIndex);
					gridProps.add(valueInput, 1, rowIndex);

					rowIndex++;
				}

				gridProps.setUserData(pHolder);
			}
		});

		borderPane.setCenter(gridProps);
       
        runButton.setDisable(true);
        
        HBox bottomBox = new HBox();
        Label amountLabel = new Label(l12n.bundle.getString(l12n.AMOUNT_KEY));
		
        final NumberTextField amountInput = new NumberTextField();
        amountInput.setText(SweetContext.getSystemConfigs().getSystemProperty(SystemConfigs.PROPERTY_LAST_AMOUNT));
        
        runButton.setOnAction((e) -> {
        	SweetContext.getSystemConfigs().setSystemProperty(SystemConfigs.PROPERTY_LAST_AMOUNT, amountInput.getText());
        	BaseSweetGenerator generator = new BaseSweetGenerator(SweetContext.getSystemConfigs(), productsBox.getValue().getTemplate(),
        			(ParametersHolder)gridProps.getUserData(), amountInput.getDoubleValue());
        	if (generator.generate()) {
        		
        	}
        });
        
        bottomBox.getChildren().addAll(amountLabel, amountInput, runButton);
        bottomBox.setSpacing(10);
        borderPane.setBottom(bottomBox);

		setContent(borderPane);
	}
}