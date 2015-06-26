package ru.morozov.sweetApp.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import ru.morozov.sweetApp.UI.Tabs.ICalcsTab;

public class SystemConfigs {
	
	private static final String PREFERENCES_NODE = "ru/morozov/sweets";
	public static final String PROPERTY_LAST_PARAMS_FILE = "last.params.file";
	public static final String PROPERTY_LAST_AMOUNT = "last.amount";
	public static final String PROPERTY_RUNS_COUNT = "runs.count";
	
	private String outputBaseDir;
	private Path outputBaseDirPath;
	private ICalcsTab calcsTabRenderer;
	
	private Preferences preferences;

	public String getOutputBaseDir() {return outputBaseDir;}
	
	public void setOutputBaseDir(String outputBaseDir) {
		this.outputBaseDir = outputBaseDir;
		
		outputBaseDirPath = Paths.get(outputBaseDir);
		if (!Files.exists(outputBaseDirPath)) {
			this.outputBaseDir = System.getProperty("user.dir");
			outputBaseDirPath = Paths.get(outputBaseDir);
		}
	}
	
	public Path getBaseFolderPath() {return outputBaseDirPath;}
	
	public Path createSubdirectory(String subdirName) {
		if (outputBaseDirPath == null)
			return null;
		
		Path newPath = Paths.get(outputBaseDir, subdirName);
		
		if (Files.exists(newPath))
			return null;
		
		try {
			return Files.createDirectory(newPath);
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	public Preferences getPreferences() {
		if (preferences == null)
			preferences = Preferences.userRoot().node(PREFERENCES_NODE);
		
		return preferences;
	}
	
	public String getSystemProperty(String propKey) {return getPreferences() == null ? null : getPreferences().get(propKey, null);}
	public void setSystemProperty(String propKey, String value) {getPreferences().put(propKey, value);}
	
	public int getIntProperty(String propKey) {return  getPreferences() == null ? 0 :  getPreferences().getInt(propKey, 0);}
	public void setIntProperty(String propKey, int value) {getPreferences().putInt(propKey, value);}

	public ICalcsTab getCalcsTabRenderer() {return calcsTabRenderer;}
	public void setCalcsTabRenderer(ICalcsTab calcsTabRenderer) {this.calcsTabRenderer = calcsTabRenderer;}
}