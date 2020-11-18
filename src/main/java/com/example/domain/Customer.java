package com.example.domain;

public class Customer {
	
	private CustomerInfo info;
	private String address;
	private int zipCode;
	private int phoneNumber;
	
	public CustomerInfo getCustomerInfo() {
		return info;
	}
	
	public void setCustomerInfo(CustomerInfo info) {
		this.info = info;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(int zip) {
		this.zipCode = zip;
	}
	
	public int getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(int phone) {
		this.phoneNumber = phone;
	}
	
}