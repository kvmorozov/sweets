package ru.morozov.sweetApp.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemConfigs {
	
	private String outputBaseDir;
	private Path outputBaseDirPath;

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
}
