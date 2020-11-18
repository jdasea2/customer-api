package com.example.domain;

public class CustomerListing{
	
	private CustomerInfo[] info ;
	private String cursorId;
	
	public String getCursor() {
		return cursorId;
	}
	
	private void setCursor(String id) {
		this.cursorId = id;
	}
	
	private CustomerInfo[] getCustomerInfo() {
		return this.info;
	}
	
	private void setCustomerInfo(CustomerInfo[] info) {
		this.info = info;
	}
}