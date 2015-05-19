package ru.morozov.sweetApp.config.base;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

public class CellCoord {
	
	private int sheet = 0, col, row;
	
	public int getSheet() {return sheet;}
	public void setSheet(int sheet) {this.sheet = sheet;}
	
	public int getCol() {return col;}
	public void setCol(int col) {this.col = col;}
	
	public int getRow() {return row;}
	public void setRow(int row) {this.row = row;}
	
	public Cell getCell(Workbook wb) {return wb.getSheetAt(getSheet()).getRow(getRow()).getCell(getCol());}
}
