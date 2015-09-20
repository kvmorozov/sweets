package ru.morozov.sweetApp.config;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SweetConfig {
	
	@Autowired
	private List<SweetProduct> sweetsList;

	public List<SweetProduct> getSweetsList() {return sweetsList;}
	public void setSweetsList(List<SweetProduct> sweetsList) {this.sweetsList = sweetsList;}
}
