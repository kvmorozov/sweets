package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.config.templates.SweetTemplate;

public class SweetProduct {
	
	private String prefix, description;
	private SweetTemplate template;

	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}

	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	@Override
	public String toString() {return description;}
	
	public SweetTemplate getTemplate() {return template == null ? template = SweetContext.getTemplate(prefix) : template;}
}
