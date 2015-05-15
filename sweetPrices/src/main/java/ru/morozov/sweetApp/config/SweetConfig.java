package ru.morozov.sweetApp.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class SweetConfig {
	
	@Autowired
	private Map<String, String> sweetsMap;

	public Map<String, String> getSweetsMap() {return sweetsMap;}
	public void setSweetsMap(Map<String, String> sweetsMap) {this.sweetsMap = sweetsMap;}
}
