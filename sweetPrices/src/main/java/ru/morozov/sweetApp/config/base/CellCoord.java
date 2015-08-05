package ru.morozov.sweetApp.config.base;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import ru.morozov.utils.ParserUtils;

public class CellCoord {
	
	private int sheet = 0, col, row;
	
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
	
	public Cell getCell(Workbook wb) {return wb.getSheetAt(getSheet()).getRow(getRow()).getCell(getCol());}
	
	public Double getDoubleValue(Workbook wb) {
		if (sheet < 0 || col < 0 || row < 0)
			return null;
		
		Double result = 0d;
		Cell cell = getCell(wb);
		
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return cell.getNumericCellValue();
			case Cell.CELL_TYPE_STRING:
				return ParserUtils.getDoubleResult(cell.getStringCellValue());
			default:
				break;
		}
		
		return result;
	}
	
	public String getStringValue(Workbook wb) {
		if (sheet < 0 || col < 0 || row < 0)
			return null;

		Cell cell = getCell(wb);
		
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				return String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			default:
				break;
		}
		
		return "";
	}
}
