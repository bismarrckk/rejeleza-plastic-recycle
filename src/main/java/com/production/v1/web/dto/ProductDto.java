package com.production.v1.web.dto;

import java.util.Date;

import com.production.v1.model.Category;
import com.production.v1.model.User;

public class ProductDto {
	
	private int id;
	private String title;
	private Category category;
	private String condition;
	private double quantity;
	private String unit;
	private double price;
	private String description;
	private String county;
	private String streetAddress;
	private String coverPhoto;
	private String video;
	private Date postDate;
	private String status;
	private User user;
	
	
	public ProductDto() {
		super();
	}
	
	
	public ProductDto(String title,Category category, String condition, double quantity, String unit,
			double price, String description, String county, String streetAddress, String coverPhoto,
			String secondPhoto, String video, Date postDate, String status, User user) {
		super();
		
		this.title = title;
		this.category = category;
		this.condition = condition;
		this.quantity = quantity;
		this.unit = unit;
		this.price = price;
		this.description = description;
		this.county = county;
		this.streetAddress = streetAddress;
		this.coverPhoto = coverPhoto;
		this.video = video;
		this.postDate = postDate;
		this.status = status;
		this.user = user;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCoverPhoto() {
		return coverPhoto;
	}
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
