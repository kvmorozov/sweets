package ru.morozov.sweetApp;

import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.morozov.sweetApp.config.SweetConfig;
import ru.morozov.sweetApp.config.SweetProduct;
import ru.morozov.sweetApp.config.SweetTemplate;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.prices.PriceList;

public class SweetContext {
	
	private static final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");
	
	public static List<SweetProduct> getProducts() {return ctx.getBean(SweetConfig.class).getSweetsList();}
	public static SweetTemplate getTemplate(String templateKey) {return (SweetTemplate)ctx.getBean(templateKey);}
	public static SystemConfigs getSystemConfigs() {return (SystemConfigs)ctx.getBean("systemConfig_demo");}
	public static PriceList getPriceList() {return (PriceList)ctx.getBean("priceList");}

	@PreDestroy
	public void finalize() {
		ctx.close();
	}
}
