package com.production.v1.services;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.production.v1.model.Product;

import com.production.v1.web.dto.ProductDto;

public interface ProductService {
	Product save(ProductDto productDto);
	Boolean validateVideoFile(MultipartFile file);
    Boolean validateImageFile(MultipartFile file);
    Product getProductById(int id);
	void deleteProduct(int id);
	void updateProduct(ProductDto productDto,int id);
	List<Product> getProductList();
	void approveProduct(int id);
	void declineProduct(int id);
	Page<Product> getProductsByStatus(String status,int pageNum,String sortField,String sortDir);
	List<Product> getApprovedProducts(String status);


	
}
