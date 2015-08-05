package ru.morozov.sweetApp.Utils.Constants;

import java.util.Locale;
import java.util.ResourceBundle;

import ru.morozov.utils.UTF8Control;

public interface l12n {
	
	ResourceBundle bundle = ResourceBundle.getBundle("sweets", new Locale("ru", "RU"), new UTF8Control());
	
	String SETTINGS_KEY = "settings";
	String CALCS_KEY = "calcs";
	String PRODUCTS_KEY = "products";
	String SELECT_PARAMS_KEY = "select-params";
	String RUN_KEY = "run";
	String EXIT_KEY = "exit";
	String TOTAL_KEY = "total";
	String AMOUNT_KEY = "amount";
	
	String COST_KEY = "cost_1";
	String COST_KEY_ADD = "cost_1_add";
	
	String OUTPUT_DIR = "output.dir";
	String NO_VALUE = "no_value";
}
