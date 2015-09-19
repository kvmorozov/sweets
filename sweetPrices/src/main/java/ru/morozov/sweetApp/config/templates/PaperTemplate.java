package ru.morozov.sweetApp.config.templates;

import org.apache.poi.ss.usermodel.Cell;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.templates.paper.PaperTitle;
import ru.morozov.sweetApp.config.values.AbstractPropertyValue;
import ru.morozov.utils.components.xls.XlsFile;

import java.util.List;
import java.util.stream.Collectors;

public class PaperTemplate extends SweetTemplate {
	
	private List<PaperTitle> titles;

	public void setTitles(List<PaperTitle> titles) {this.titles = titles;}
	
	@Override
	public XlsFile applyParams(PropertyValueSet values, Double amount) {
        XlsFile newTemplate = super.applyParams(values, amount);
		
		List<Object> args = values.getValueSet().stream().map(AbstractPropertyValue::getValue).collect(Collectors.toList());

		for(PaperTitle title : titles) {
			String titleStr = String.format(title.getTitleMask(), args.toArray());
			
			titleStr = titleStr.replaceAll(".0", "");
			titleStr = titleStr.replaceAll(",0", "");
			
			Cell cell = title.getCell().getCell(newTemplate);
			cell.setCellValue(titleStr);
		}
		
		return newTemplate;
	}
}
