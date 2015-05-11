package ru.morozov.sweetApp.Utils.Constants;

import java.util.Locale;
import java.util.ResourceBundle;

public interface l12n {
	
	public static ResourceBundle bundle = ResourceBundle.getBundle("main.resources.sweets", new Locale("ru", "RU"));
	
	public static final String SETTINGS_KEY = "settings";

}
