package ru.morozov.sweetApp.UI;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.morozov.sweetApp.UI.Tabs.CalcsTab;
import ru.morozov.sweetApp.UI.Tabs.SettingsTab;
import ru.morozov.sweetApp.Utils.Constants.l12n;

public class MainScene extends Scene {
	
	public MainScene(Stage stage, VBox root) {
		super(root, 400, 400);

		TabPane tabPane = new TabPane();
		Tab settingsTab = new SettingsTab();
		Tab calcsTab = new CalcsTab(stage);

		tabPane.getTabs().add(calcsTab);
		tabPane.getTabs().add(settingsTab);
		
		Button exitButton = new Button(l12n.bundle.getString(l12n.EXIT_KEY));
		exitButton.setOnAction((e) -> Platform.exit());
		
		root.getChildren().addAll(tabPane, exitButton);
	}
}
