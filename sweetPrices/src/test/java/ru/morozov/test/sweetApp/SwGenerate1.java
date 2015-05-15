package ru.morozov.test.sweetApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import ru.morozov.sweetApp.generate.BaseSweetGenerator;

public class SwGenerate1 {
	
	private ApplicationContext ctx;
	
	@Before
	public void prepareString() {
		ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");
	}
	
	@Test
	public void prepareGeneration() {
		BaseSweetGenerator getProkl3 = (BaseSweetGenerator) ctx.getBean("prol3_demo_generation");
		BaseSweetGenerator getProkl5 = (BaseSweetGenerator) ctx.getBean("prol5_demo_generation");
		
		Assert.notNull(getProkl3);
		Assert.notNull(getProkl5);
		
		Assert.notNull(getProkl3.getSystemConfig());
		
		Path testPath;
		Assert.notNull(testPath = getProkl3.getSystemConfig().createSubdirectory("test"));
		Assert.isNull(getProkl3.getSystemConfig().createSubdirectory("test"));
		
		try {
			Files.delete(testPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGeneration() {
		BaseSweetGenerator getProkl3 = (BaseSweetGenerator) ctx.getBean("prol3_demo_generation");
		Assert.notNull(getProkl3);
		
		Assert.isTrue(getProkl3.generate());
	}
}
