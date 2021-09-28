package com.production.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.production.v1.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	
	User findByVerificationCode(String code);
	User findByResetPasswordToken(String token);

	//@Modifying
	//@Query("update User u set u.phone = :phone where u.id = :id")
	//void updatePhone(@Param(value = "id") long id, @Param(value = "phone") int phone);
	
}
