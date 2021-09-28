package com.production.v1.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.production.v1.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

	Page<Product> findByStatus(String status,Pageable pageable);
	List<Product> findByStatus(String status);

}
