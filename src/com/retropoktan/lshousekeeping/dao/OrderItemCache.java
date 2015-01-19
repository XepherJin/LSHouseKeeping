package com.retropoktan.lshousekeeping.dao;

public class OrderItemCache {
	
	private static OrderItemCache instance = null;
	private String name;
	private String phone;
	private String address;
	private float price;
	private String orderItem;
	private String ps;
	private OrderItemCache() {
		
	}
	
	public static OrderItemCache getInstance() {
		if (instance == null) {
			instance = new OrderItemCache();
		}
		return instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(String oderItem) {
		this.orderItem = oderItem;
	}
	
	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public void clearAll() {
		name = null;
		phone = null;
		address = null;
		price = 0;
		orderItem = null;
		ps = null;
	}
}
