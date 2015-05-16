package ru.morozov.sweetApp;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.morozov.sweetApp.config.SweetConfig;
import ru.morozov.sweetApp.config.SweetProduct;
import ru.morozov.sweetApp.config.SweetTemplate;

public class SweetContext {
	
	private static final ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");
	
	public static List<SweetProduct> getProducts() {return ctx.getBean(SweetConfig.class).getSweetsList();}
	public static SweetTemplate getTemplate(String templateKey) {return (SweetTemplate)ctx.getBean(templateKey);}

}
