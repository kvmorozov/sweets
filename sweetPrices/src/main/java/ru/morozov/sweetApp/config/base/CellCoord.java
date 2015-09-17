package ru.morozov.sweetApp.config.base;

import org.apache.poi.ss.usermodel.Cell;
import ru.morozov.utils.components.xls.XlsFile;

public class CellCoord {
	
	private int sheet = -1, col, row;
	
	public CellCoord() {}
	
	public CellCoord(int sheet, int col, int row) {
		this.sheet = sheet;
		this.col = col;
		this.row = row;
	}
	
	public CellCoord(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	public CellCoord(int row) {
		this.row = row;
	}
	
	public int getSheet() {return sheet;}
	public void setSheet(int sheet) {this.sheet = sheet;}
	
	public int getCol() {return col;}
	public void setCol(int col) {this.col = col;}
	
	public int getRow() {return row;}
	public void setRow(int row) {this.row = row;}
	
	public Cell getCell(XlsFile xlsFile) {return xlsFile.getCell(this);}
	
	public Double getDoubleValue(XlsFile xlsFile) {return xlsFile.getDoubleValue(this);}
	
	public String getStringValue(XlsFile xlsFile) {return xlsFile.getStringValue(this);}
}
