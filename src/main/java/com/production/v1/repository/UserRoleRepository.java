package com.production.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.production.v1.model.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
	@Query("SELECT r FROM UserRole r WHERE r.name = :name")
	UserRole findByName(String name);
	UserRole findByName(String[] roles);

}
