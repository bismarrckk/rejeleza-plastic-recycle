package com.production.v1.web.dto;

public class SearchProduct {
	private int id;
	private String categoryName;
	private String title;
	private double	price;
	private String unit;
	private String streetAddress;
	private String county;
	private String coverPhoto;
	public SearchProduct(int id, String categoryName, String title, double price, String unit, String streetAddress,
			String county,String coverPhoto) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.title = title;
		this.price = price;
		this.unit = unit;
		this.streetAddress = streetAddress;
		this.county = county;
		this.coverPhoto=coverPhoto;
	}
	public SearchProduct() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "SearchProduct [id=" + id + ", categoryName=" + categoryName + ", title=" + title + ", price=" + price
				+ ", unit=" + unit + ", streetAddress=" + streetAddress + ", county=" + county + ", coverPhoto="
				+ coverPhoto + "]";
	}
	public String getCoverPhoto() {
		return coverPhoto;
	}
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getcategoryName() {
		return categoryName;
	}
	public void setcategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	
	
	
}
