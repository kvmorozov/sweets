package ru.morozov.sweetApp.UI.Tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

		final VBox propsBox = new VBox();
		propsBox.setSpacing(10);
		propsBox.setMaxHeight(Double.MAX_VALUE);

		productsBox.valueProperty().addListener(
				new ChangeListener<SweetProduct>() {
					@Override
					public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, SweetProduct oldValue, SweetProduct newValue) {
						propsBox.getChildren().clear();
						
						SweetTemplate template = newValue.getTemplate();
						for(SweetProperty property : template.getProperties().getProperties()) {
							HBox propHBox = new HBox();
							propHBox.getChildren().add(new Label(property.getPropertyName() + ":"));
							
							propsBox.getChildren().add(propHBox);
						}
					}
				});

		borderPane.setCenter(propsBox);
		
        final Button runButton = new Button(l12n.bundle.getString(l12n.RUN_KEY));
        runButton.setDisable(true);
        
        HBox bottomBox = new HBox();
        Label amountLabel = new Label(l12n.bundle.getString(l12n.AMOUNT_KEY));
		
        final NumberTextField amountInput = new NumberTextField();
        amountInput.setText(SweetContext.getSystemConfigs().getSystemProperty(SystemConfigs.PROPERTY_LAST_AMOUNT));
        
        runButton.setOnAction((e) -> {
        	SweetContext.getSystemConfigs().setSystemProperty(SystemConfigs.PROPERTY_LAST_AMOUNT, amountInput.getText());
        	BaseSweetGenerator generator = new BaseSweetGenerator(SweetContext.getSystemConfigs(), productsBox.getValue().getTemplate(),
        			(ParametersHolder)propsBox.getUserData(), amountInput.getDoubleValue());
        	if (generator.generate()) {
        		
        	}
        });
        
        bottomBox.getChildren().addAll(amountLabel, amountInput, runButton);
        bottomBox.setSpacing(10);
        borderPane.setBottom(bottomBox);

		setContent(borderPane);
	}
}