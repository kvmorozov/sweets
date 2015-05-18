package ru.morozov.sweetApp.config.templates.paper;

import ru.morozov.sweetApp.config.base.CellCoord;

public class PaperTitle {
	
	private String titleMask;
	private CellCoord cell;
	
	public String getTitleMask() {return titleMask;}
	public void setTitleMask(String titleMask) {this.titleMask = titleMask;}
	
	public CellCoord getCell() {return cell;}
	public void setCell(CellCoord cell) {this.cell = cell;}
}
