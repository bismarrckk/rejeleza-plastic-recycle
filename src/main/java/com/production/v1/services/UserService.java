package com.production.v1.services;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.production.v1.model.User;
import com.production.v1.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	
	void save(UserRegistrationDto userRegistrationDto,String siteURL);
	
	void updateUser(int id,UserRegistrationDto userDto);
		
	User findByEmail(String email);
	
	Boolean verifyCode(String code);
	
	Page<User> getAllUsers(int pageNum,String sortField,String sortDir);
	
	User getUserById(int id);
	
	void deleteUser(int id);
	
	void updateResetPasswordToken(String token, String email);
	
	User getByResetPasswordToken(String token);
	
	void updatePassword(User user, String newPassword);
	
	void sendResetEmail(String recipientEmail, String link) throws UnsupportedEncodingException, MessagingException;
	
	void updateProfile(int id,UserRegistrationDto userDto);
	
	void updatePassword(String pwd,String email);

}
