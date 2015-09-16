package ru.morozov.sweetApp.UI;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.UI.Tabs.ICalcsTab;
import ru.morozov.sweetApp.UI.Tabs.SettingsTab;
import ru.morozov.sweetApp.Utils.Constants.l12n;

public class MainScene extends Scene {
	
	public MainScene(Stage stage, BorderPane root) {
		super(root, 550, 400);

		TabPane tabPane = new TabPane();
		
		Tab settingsTab = new SettingsTab();
		Tab calcsTab = (Tab)SweetContext.getSystemConfigs().getCalcsTabRenderer();

		tabPane.getTabs().add(calcsTab);
		tabPane.getTabs().add(settingsTab);
		
		((ICalcsTab) calcsTab).initCalcsTab(stage);
		
		Button exitButton = new Button(l12n.bundle.getString(l12n.EXIT_KEY));
		exitButton.setOnAction((e) -> Platform.exit());
		
		tabPane.prefWidthProperty().bind(this.widthProperty().multiply(0.9));
		tabPane.prefHeightProperty().bind(this.heightProperty().
				subtract(exitButton.getHeight()).multiply(0.9));
		
		root.setTop(tabPane);
		root.setBottom(exitButton);
	}
}
