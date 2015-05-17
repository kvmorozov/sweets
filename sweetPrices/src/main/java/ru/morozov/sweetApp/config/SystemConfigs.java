package ru.morozov.sweetApp.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SystemConfigs {
	
	private static final String PROPS_FILE_NAME = "sweet.properties";
	public static final String PROPERTY_LAST_PARAMS_FILE = "last.params.file";
	
	private String outputBaseDir;
	private Path outputBaseDirPath;
	
	private Properties properties;

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
	
	public Properties getProperties() {
		if (properties == null) {
			FileInputStream fis = null;
			try {
				properties = new Properties();
				fis = new FileInputStream(getOutputBaseDir() + PROPS_FILE_NAME);
				if(fis != null) {
					properties.load(fis);
				}
			} catch (FileNotFoundException e) {
				properties = new Properties();
				saveProperties();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if (fis != null)
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		
		return properties;
	}
	
	private void saveProperties() {
		if (properties != null) {
			try {
				properties.store(new FileOutputStream(getOutputBaseDir() + PROPS_FILE_NAME), null);
				properties = null;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public String getSystemProperty(String propKey) {return getProperties().getProperty(propKey);}
	public void setSystemProperty(String propKey, String value) {
		getProperties().setProperty(propKey, value);
		saveProperties();
	}
}
