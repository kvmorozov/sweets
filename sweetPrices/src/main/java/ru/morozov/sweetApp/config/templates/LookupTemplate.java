package ru.morozov.sweetApp.config.templates;

import ru.morozov.sweetApp.config.ParametersHolder;
import ru.morozov.sweetApp.config.SystemConfigs;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.generate.IGenerator;
import ru.morozov.sweetApp.generate.LookupGenerator;

public class LookupTemplate extends SweetTemplate {

    private CellCoord totalCell;

    public CellCoord getTotalCell() {return totalCell;}
    public void setTotalCell(CellCoord totalCell) {this.totalCell = totalCell;}

    @Override
    public IGenerator getGenerator(SystemConfigs systemConfig, ParametersHolder parametersHolder, Double amount) {
        return new LookupGenerator(systemConfig, this, parametersHolder, amount);
    }

}
