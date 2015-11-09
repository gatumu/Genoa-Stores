package com.themega.genoastores;

public class Sale {
	private String uid, date, product, customer_names, customer_location, status;
	private int qty;
	public Sale(String uid, String date, String product, String customer_names,
			String customer_location, String status, int qty) {
		super();
		this.uid = uid;
		this.date = date;
		this.product = product;
		this.customer_names = customer_names;
		this.customer_location = customer_location;
		this.status = status;
		this.qty = qty;
	}
	public String getUid() {
		return uid;
	}
	public String getDate() {
		return date;
	}
	public String getProduct() {
		return product;
	}
	public String getCustomer_names() {
		return customer_names;
	}
	public String getCustomer_location() {
		return customer_location;
	}
	public String getStatus() {
		return status;
	}
	public int getQty() {
		return qty;
	}
}
