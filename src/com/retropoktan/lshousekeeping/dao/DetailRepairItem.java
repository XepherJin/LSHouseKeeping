package com.retropoktan.lshousekeeping.dao;

public class DetailRepairItem{

	private String itemId;
	private String content;
	private String price;
	private String title;
	
	public DetailRepairItem(String itemId, String content, String price,
			String title) {
		super();
		this.itemId = itemId;
		this.content = content;
		this.price = price;
		this.title = title;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
