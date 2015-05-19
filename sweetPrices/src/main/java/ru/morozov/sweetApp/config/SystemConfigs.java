package ru.morozov.sweetApp.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

public class SystemConfigs {
	
	private static final String PREFERENCES_NODE = "ru/morozov/sweets";
	public static final String PROPERTY_LAST_PARAMS_FILE = "last.params.file";
	public static final String PROPERTY_LAST_AMOUNT = "last.amount";
	public static final String PROPERTY_RUNS_COUNT = "runs.count";
	
	private String outputBaseDir;
	private Path outputBaseDirPath;
	
	private Preferences preferences;

	public String getOutputBaseDir() {return outputBaseDir;}
	
	public void setOutputBaseDir(String outputBaseDir) {
		this.outputBaseDir = outputBaseDir;
		
		outputBaseDirPath = Paths.get(outputBaseDir);
		if (!Files.exists(outputBaseDirPath))
			outputBaseDirPath = null;
	}
	
	public Path getBaseFolderPath() {return outputBaseDirPath;}
	
	public Path createSubdirectory(String subdirName) {
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
}
