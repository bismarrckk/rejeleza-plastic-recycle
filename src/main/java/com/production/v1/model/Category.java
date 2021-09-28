package com.production.v1.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@OneToMany(mappedBy = "category", cascade = {
	        CascadeType.ALL
	    })
	private List<Product> products;
	
	
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Category(String name) {
		super();
		this.name = name;
	}
	public Category() {
		
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
	
	

}
