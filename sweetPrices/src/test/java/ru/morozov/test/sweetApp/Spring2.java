package ru.morozov.test.sweetApp;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SweetConfig;
import ru.morozov.sweetApp.config.SweetProduct;
import ru.morozov.sweetApp.config.SweetTemplate;
import ru.morozov.sweetApp.config.SystemConfigs;

public class Spring2 {
	
	private static final int REAL_SWEET_MAP_SIZE = 2;
	private static final int REAL_DEMO_PARAMS_ROWS_COUNT = 8;
	private static final int REAL_DEMO_PARAMS_COUNT = 2;
	
	private ApplicationContext ctx;
	
	@Before
	public void prepareString() {
		ctx = new ClassPathXmlApplicationContext("spring/app-context.xml");
	}

	@Test
	public void checkSweetMap() {
		SweetConfig sweetConfig = ctx.getBean(SweetConfig.class);
		Assert.notNull(sweetConfig);
		Assert.notNull(sweetConfig.getSweetsList());
		Assert.notEmpty(sweetConfig.getSweetsList());
		Assert.isTrue(sweetConfig.getSweetsList().size() == REAL_SWEET_MAP_SIZE);
	}
	
	@Test
	public void checkTemplates() {
		SweetConfig sweetConfig = ctx.getBean(SweetConfig.class);
		for(SweetProduct product : sweetConfig.getSweetsList()) {
			SweetTemplate template = product.getTemplate();
			Assert.notNull(template);
			Assert.isTrue(template.isValidConfig());
			Assert.notNull(template.getProperties());
			Assert.notEmpty(template.getProperties().getProperties());
		}
	}
	
	@Test
	public void checkParams() {
		ParametersHolder prokl3params = (ParametersHolder)ctx.getBean("prol3_demo_params");
		ParametersHolder prokl5params = (ParametersHolder)ctx.getBean("prol5_demo_params");
		
		Assert.notNull(prokl3params);
		Assert.notNull(prokl5params);
		
		Assert.notEmpty(prokl3params.getParameters());
		Assert.notEmpty(prokl5params.getParameters());
		
		Assert.isTrue(prokl3params.getParameters().size() == REAL_DEMO_PARAMS_ROWS_COUNT);
		Assert.isTrue(prokl5params.getParameters().size() == REAL_DEMO_PARAMS_ROWS_COUNT);
		
		for(PropertyValueSet paramSet : prokl3params.getParameters())
			Assert.isTrue(paramSet.size() == REAL_DEMO_PARAMS_COUNT);

		for(PropertyValueSet paramSet : prokl5params.getParameters())
			Assert.isTrue(paramSet.size() == REAL_DEMO_PARAMS_COUNT);
	}
	
	@Test
	public void checkSystemParams() {
		SystemConfigs generalConfigs = (SystemConfigs) ctx.getBean("systemConfig");
		SystemConfigs demoConfigs = (SystemConfigs) ctx.getBean("systemConfig_demo");
		
		Assert.notNull(generalConfigs);
		Assert.notNull(demoConfigs);
		
		Assert.isNull(generalConfigs.getOutputBaseDir());
		Assert.notNull(demoConfigs.getOutputBaseDir());
		Assert.notNull(demoConfigs.getBaseFolderPath());
	}
}
