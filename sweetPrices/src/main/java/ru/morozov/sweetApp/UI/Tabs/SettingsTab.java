package ru.morozov.sweetApp.UI.Tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import ru.morozov.sweetApp.Utils.Constants.l12n;

public class SettingsTab extends Tab {
	
	public SettingsTab() {
		super();
		
		setClosable(false);
		setText(l12n.bundle.getString(l12n.SETTINGS_KEY));
		
		HBox hbox = new HBox();
		hbox.getChildren().add(new Label("Tab 1"));
		hbox.setAlignment(Pos.CENTER);
		setContent(hbox);
	}

}
