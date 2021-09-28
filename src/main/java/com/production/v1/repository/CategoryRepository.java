package com.production.v1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.production.v1.model.Category;
import com.production.v1.web.dto.ProductsByCategory;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>{
	@Query("SELECT new com.production.v1.web.dto.ProductsByCategory(p.id,c.name,p.coverPhoto,p.title,p.price,p.unit,p.streetAddress,p.county)"
			+ " FROM Category c JOIN c.products p WHERE c.id=:id AND p.status like '%Approved%'")
	List<ProductsByCategory> getProductsByCategory(@Param(value = "id") int id);
	
}
