package com.production.v1.web.dto;

import java.util.Collection;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.production.v1.model.UserRole;

public class UserRegistrationDto {
	private int id;
	@Size(min=3,max=30,message="First Name cannot have less than three letters")
	private String firstName;
	@Size(min=3,max=30, message="Last Name cannot have less than three letters")
	private String lastName;
	@NotEmpty( message="Email Address cannot be empty")
	@Email( message="A valid email is required")
	private String email;
	@Min(value=100000000, message="A valid phone number is required")
	private int phone;
	@Min(value=1000000, message="A valid ID number is required")
	private int idNumber;
	@Size(min = 8, message="Password should have a minimum of 8 characters")
	private String password;
	private Boolean enabled;
	private Collection<UserRole> roles;
	
	public UserRegistrationDto() {
		
	}

	public UserRegistrationDto(String firstName, String lastName, String email, int phone, int idNumber,boolean enabled,
			String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.idNumber = idNumber;
		this.enabled = enabled;
		this.password = password;
	}
	
	
	
	public UserRegistrationDto(
			@Size(min = 3, max = 30, message = "First Name cannot have less than three letters") String firstName,
			@Size(min = 3, max = 30, message = "Last Name cannot have less than three letters") String lastName,
			@NotEmpty(message = "Email Address cannot be empty") @Email(message = "A valid email is required") String email,
			@Min(value = 100000000, message = "A valid phone number is required") int phone,
			@Min(value = 1000000, message = "A valid ID number is required") int idNumber,
			@Size(min = 8, message = "Password should have a minimum of 8 characters") String password, Boolean enabled,
			Collection<UserRole> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.idNumber = idNumber;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	public Collection<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<UserRole> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public int getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
