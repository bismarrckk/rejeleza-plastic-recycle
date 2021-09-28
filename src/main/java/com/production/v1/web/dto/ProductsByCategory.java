package com.production.v1.web.dto;public class ProductsByCategory {
	private int id;
	private String name;
	private String coverPhoto;
	private String title;
	private double price;
	private String unit;
	private String streetAddress;
	private String county;
	
	public ProductsByCategory() {
		super();
	}
	
	public ProductsByCategory(int id, String name, String coverPhoto, String title, double price, String unit,
			String streetAddress, String county) {
		super();
		this.id = id;
		this.name = name;
		this.coverPhoto = coverPhoto;
		this.title = title;
		this.price = price;
		this.unit = unit;
		this.streetAddress = streetAddress;
		this.county = county;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoverPhoto() {
		return coverPhoto;
	}
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	@Override
	public String toString() {
		return "ProductsByCategory [name=" + name + ", coverPhoto=" + coverPhoto + ", title=" + title + ", price="
				+ price + ", unit=" + unit + ", streetAddress=" + streetAddress + ", county=" + county + "]";
	}
	
	
	
}
