package ru.morozov.sweetApp.config.templates;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import ru.morozov.sweetApp.config.PropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.templates.paper.PaperTitle;

import java.util.List;
import java.util.stream.Collectors;

public class PaperTemplate extends SweetTemplate {
	
	private List<PaperTitle> titles;

	public List<PaperTitle> getTitles() {return titles;}
	public void setTitles(List<PaperTitle> titles) {this.titles = titles;}
	
	@Override
	public Workbook applyParams(PropertyValueSet values, Double amount) {
		Workbook newWorkbook = super.applyParams(values, amount);
		
		List<Object> args = values.getValueSet().stream().map(PropertyValue::getValue).collect(Collectors.toList());

		for(PaperTitle title : titles) {
			String titleStr = String.format(title.getTitleMask(), args.toArray());
			
			titleStr = titleStr.replaceAll(".0", "");
			titleStr = titleStr.replaceAll(",0", "");
			
			Cell cell = title.getCell().getCell(newWorkbook);
			cell.setCellValue(titleStr);
		}
		
		return newWorkbook;
	}
}
