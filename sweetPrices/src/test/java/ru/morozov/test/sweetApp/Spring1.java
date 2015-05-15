package ru.morozov.test.sweetApp;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class Spring1 {
	
	@Test
	public void checkContextExists() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");
		Assert.notNull(ctx);
	}

}
