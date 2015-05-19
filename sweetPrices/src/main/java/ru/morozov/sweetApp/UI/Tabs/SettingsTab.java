package ru.morozov.sweetApp.UI.Tabs;

import java.util.prefs.Preferences;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.Utils.Constants.l12n;

public class SettingsTab extends Tab {
	
	public SettingsTab() {
		super();
		
		setClosable(false);
		setText(l12n.bundle.getString(l12n.SETTINGS_KEY));
		
		Preferences preferences = SweetContext.getSystemConfigs().getPreferences();
		
		GridPane grid = new GridPane();

		int rowIndex = 0;
		
		try {
			for(String key : preferences.keys()) {
				grid.add(new Label(l12n.bundle.getString(key) + ":"), 0, rowIndex);
				grid.add(new Label(preferences.get(key, "").toString()), 1, rowIndex);
				
				rowIndex++;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		setContent(grid);
	}
}
