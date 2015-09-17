package ru.morozov.sweetApp;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.morozov.sweetApp.config.SweetConfig;
import ru.morozov.sweetApp.config.SweetProduct;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.prices.PricesSet;

import javax.annotation.PreDestroy;
import java.util.List;

public class SweetContext {
	
	private static final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");
	
	public static List<SweetProduct> getProducts() {return ctx.getBean(SweetConfig.class).getSweetsList();}
	public static SystemConfigs getSystemConfigs() {return (SystemConfigs)ctx.getBean("systemConfig_demo");}
	public static PricesSet getPricesSet() {return (PricesSet)ctx.getBean("all_prices");}

	@PreDestroy
	protected void finalize() throws Throwable {
		super.finalize();
		ctx.close();
	}
}
