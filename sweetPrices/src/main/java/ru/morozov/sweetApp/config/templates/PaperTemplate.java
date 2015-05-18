package ru.morozov.sweetApp.config.templates;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import ru.morozov.sweetApp.config.PropertyValue;
import ru.morozov.sweetApp.config.PropertyValueSet;
import ru.morozov.sweetApp.config.base.CellCoord;
import ru.morozov.sweetApp.config.templates.paper.PaperTitle;

public class PaperTemplate extends SweetTemplate {
	
	private List<PaperTitle> titles;

	public List<PaperTitle> getTitles() {return titles;}
	public void setTitles(List<PaperTitle> titles) {this.titles = titles;}
	
	@Override
	public Workbook applyParams(PropertyValueSet values) {
		Workbook newWorkbook = super.applyParams(values);
		
		List<Object> args = new ArrayList<Object>();
		for(PropertyValue value : values.getValueSet())
			args.add(value.getValue().intValue());
		
		for(PaperTitle title : titles) {
			String titleStr = String.format(title.getTitleMask(), args.toArray());
			
			CellCoord coord = title.getCell();
			Cell cell = newWorkbook.getSheetAt(coord.getSheet()).getRow(coord.getRow()).getCell(coord.getCol());
			cell.setCellValue(titleStr);
		}
		
		return newWorkbook;
	}
}
