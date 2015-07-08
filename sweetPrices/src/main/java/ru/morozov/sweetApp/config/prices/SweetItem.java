package ru.morozov.sweetApp.config.prices;

public class SweetItem {
	
	private String itemName;
	
	public SweetItem() {}
	
	public SweetItem(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemName() {return itemName;}
	public void setItemName(String itemName) {this.itemName = itemName;}

}
