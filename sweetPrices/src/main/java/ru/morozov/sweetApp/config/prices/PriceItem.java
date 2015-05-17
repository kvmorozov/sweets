package ru.morozov.sweetApp.config.prices;

public class PriceItem {
	
	private SweetItem item;
	private Double price;
	private int col, row;
	
	public int getCol() {return col;}
	public void setCol(int col) {this.col = col;}
	
	public int getRow() {return row;}
	public void setRow(int row) {this.row = row;}
	
	public SweetItem getItem() {return item;}
	public void setItem(SweetItem item) {this.item = item;}
	
	public Double getPrice() {return price;}
	public void setPrice(Double price) {this.price = price;}

}
