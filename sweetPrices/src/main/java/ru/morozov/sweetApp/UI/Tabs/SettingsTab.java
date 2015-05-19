package ru.morozov.sweetApp.UI.Tabs;

import java.util.Map.Entry;
import java.util.Properties;

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
		
		Properties properties = SweetContext.getSystemConfigs().getProperties();
		
		GridPane grid = new GridPane();

		int rowIndex = 0;
		for(Entry<Object, Object> entries : properties.entrySet()) {
			grid.add(new Label(l12n.bundle.getString(entries.getKey().toString()) + ":"), 0, rowIndex);
			grid.add(new Label(entries.getValue().toString()), 1, rowIndex);
			
			rowIndex++;
		}

		setContent(grid);
	}
}
