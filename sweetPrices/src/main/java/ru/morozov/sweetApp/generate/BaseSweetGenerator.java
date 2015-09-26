package ru.morozov.sweetApp.generate;

import ru.morozov.sweetApp.SweetContext;
import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.prices.PriceItem;
import ru.morozov.sweetApp.config.prices.PricesSet;
import ru.morozov.sweetApp.config.templates.SweetTemplate;
import ru.morozov.utils.components.xls.XlsFile;

public class BaseSweetGenerator extends AbstractGenerator {
	
	public BaseSweetGenerator(SystemConfigs systemConfig, SweetTemplate template, ParametersHolder parametersHolder) {
		this.systemConfig = systemConfig;
		this.template = template;
		this.parametersHolder = parametersHolder;
		this.amount = DEFAULT_AMOUNT;
	}

	public BaseSweetGenerator(SystemConfigs systemConfig, SweetTemplate template, ParametersHolder parametersHolder, Double amount) {
		this.systemConfig = systemConfig;
		this.template = template;
		this.parametersHolder = parametersHolder;
		this.amount = amount;
	}

    @Override
	public boolean generate() {
		PricesSet appPrices = SweetContext.getPricesSet();
		
		for(PropertyValueSet params : parametersHolder.getParameters()) {
			XlsFile generatedTemplate = template.applyParams(params, amount);
			
			Double total = 0d;

            if (template.getAmounts() != null)
                for(PriceItem amount : template.getAmounts()) {
                    PriceItem price = amount.getPrice(appPrices);

                    try {
                        if (price != null)
                            total += price.getTotal(amount, generatedTemplate);
                    }
                    catch(Exception ex) {
                        System.out.println("Ошибка вычисления!");
                    }
                }
			
            if (!writeOut(params, generatedTemplate))
                return false;
			
			params.setTotal(total, amount);
		}
		
		parametersHolder.invalidate();

		return true;
	}
}
