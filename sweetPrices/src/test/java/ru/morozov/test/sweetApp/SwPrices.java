package ru.morozov.test.sweetApp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import ru.morozov.sweetApp.config.prices.PriceList;
import ru.morozov.sweetApp.config.prices.PricesSet;

public class SwPrices {
	
	private ApplicationContext ctx;
	
	@Before
	public void prepareString() {
		ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");
	}
	
	@Test
	public void testPrices() {
		PricesSet priceSet = (PricesSet)ctx.getBean("all_prices");
		
		for(PriceList priceList : priceSet.getPriceLists()) {
			Assert.notNull(priceList);
			Assert.isTrue(priceList.isValidConfig());
		}
	}
}
