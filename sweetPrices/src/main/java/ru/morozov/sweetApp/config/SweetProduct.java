package ru.morozov.sweetApp.config;

import ru.morozov.sweetApp.SweetContext;

public class SweetProduct {
	
	private String prefix, description;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {return description;}
	
	public SweetTemplate getTemplate() {
		return SweetContext.getTemplate(prefix);
	}
}
