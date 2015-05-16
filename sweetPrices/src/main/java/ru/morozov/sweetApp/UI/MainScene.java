package ru.morozov.sweetApp.UI;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import ru.morozov.sweetApp.UI.Tabs.SettingsTab;

public class MainScene extends Scene {
	
	public MainScene(VBox root) {
		super(root, 400, 400);

		TabPane tabPane = new TabPane();
		Tab settingsTab = new SettingsTab();

		tabPane.getTabs().add(settingsTab);
		root.getChildren().add(tabPane);
	}

}
