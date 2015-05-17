package ru.morozov.sweetApp.Utils.Constants;

import java.util.Locale;
import java.util.ResourceBundle;

import ru.morozov.utils.UTF8Control;

public interface l12n {
	
	public static ResourceBundle bundle = ResourceBundle.getBundle("sweets", new Locale("ru", "RU"), new UTF8Control());
	
	public static final String SETTINGS_KEY = "settings";
	public static final String CALCS_KEY = "calcs";
	public static final String PRODUCTS_KEY = "products";
	public static final String SELECT_PARAMS_KEY = "select-params";
	public static final String RUN_KEY = "run";
	public static final String EXIT_KEY = "exit";
	public static final String TOTAL_KEY = "total";
}
