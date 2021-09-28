package com.production.v1.services;


import java.util.List;

import org.springframework.data.domain.Page;

import com.production.v1.model.Category;
import com.production.v1.web.dto.CategoryDTO;
import com.production.v1.web.dto.ProductsByCategory;

public interface CategoryService {
	
	void save(CategoryDTO categorydto);
	void update(CategoryDTO categorydto);
	Category getById(int id);
	Page<Category> getAllCategories(int pageNum,String sortField,String sortDir);
	void deleteCategory(int id);
	List<Category> getAllCategoriesDropdown();
	List<ProductsByCategory> getProductsByCategory(int id);
	

}
 