package com.production.v1.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.production.v1.model.Product;
import com.production.v1.model.User;
import com.production.v1.web.dto.SearchProduct;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

	Page<Product> findByStatus(String status,Pageable pageable);
	List<Product> findByStatus(String status);
	List<Product> findByUser(User user);
	@Query("SELECT new com.production.v1.web.dto.SearchProduct(p.id,c.name,p.title,p.price,p.unit,p.streetAddress,p.county,p.coverPhoto)"
			+ " FROM Product p join p.category c WHERE p.status LIKE '%Approved%' AND CONCAT(p.id ,'',c.name,'',p.title,'',p.price,'',p.unit,'',p.streetAddress,'',p.county,'',p.coverPhoto)"
			+ "LIKE %:q% ")
	List<SearchProduct> search(@Param(value = "q") String q); 

}
