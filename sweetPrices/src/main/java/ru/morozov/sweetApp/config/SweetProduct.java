package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.config.templates.SweetTemplate;

public class SweetProduct {
	
	private String description;
	private SweetTemplate template;

	public SweetTemplate getTemplate() {return template;}
	public void setTemplate(SweetTemplate template) {this.template = template;}

	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	@Override
	public String toString() {return description;}
}
