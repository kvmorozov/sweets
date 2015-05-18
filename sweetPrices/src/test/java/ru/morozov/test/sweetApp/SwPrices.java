package ru.morozov.test.sweetApp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import ru.morozov.sweetApp.config.prices.PriceList;

public class SwPrices {
	
	private static final int PRICES_COUNT = 5;
	private static final double PRICE_1_VALUE = 59;
	private static final double PRICE_2_VALUE = 45.64;
	
	private ApplicationContext ctx;
	
	@Before
	public void prepareString() {
		ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");
	}
	
	@Test
	public void testPrices() {
		PriceList priceList = (PriceList)ctx.getBean("priceList");
		Assert.notNull(priceList);
		Assert.isTrue(priceList.isValidConfig());
		Assert.isTrue(priceList.getPrices().size() == PRICES_COUNT);
		Assert.isTrue(priceList.getPrices().get(0).getPrice() == PRICE_1_VALUE);
		Assert.isTrue(priceList.getPrices().get(1).getPrice() == PRICE_2_VALUE);
	}

}
