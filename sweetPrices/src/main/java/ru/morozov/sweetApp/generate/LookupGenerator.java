package ru.morozov.sweetApp.generate;

import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.templates.LookupTemplate;
import ru.morozov.utils.components.xls.XlsFile;

/**
 * Created by km on 21.09.2015.
 */
public class LookupGenerator extends AbstractGenerator {

    public LookupGenerator(SystemConfigs systemConfig, LookupTemplate template, ParametersHolder parametersHolder, Double amount) {
        this.systemConfig = systemConfig;
        this.template = template;
        this.parametersHolder = parametersHolder;
        this.amount = amount;
    }

    @Override
    public boolean generate() {
        for(PropertyValueSet params : parametersHolder.getParameters()) {
            XlsFile generatedTemplate = template.applyParams(params, amount);

            if (!writeOut(params, generatedTemplate))
                return false;

            Double total = generatedTemplate.getDoubleValue(((LookupTemplate)template).getTotalCell());

            params.setTotal(total, amount);
        }

        parametersHolder.invalidate();

        return true;
    }
}
