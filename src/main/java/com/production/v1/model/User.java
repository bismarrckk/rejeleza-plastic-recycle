package com.production.v1.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users",uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	private String email;
	private int phone;
	@Column(name="id_number")
	private int idNumber;
	private String password;
	@Column(name="verification_code", length=64)
	private String verificationCode;
	private boolean enabled;
	@ManyToMany(fetch=FetchType.EAGER )
	@JoinTable(
			name="users_roles",
			joinColumns=@JoinColumn(name="user_id",referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName="id")
			)
	
	private Collection<UserRole> roles;
	
	@Column(name="reset_password_token",length=30)
	private String resetPasswordToken;
	
	@OneToMany(mappedBy = "user", cascade = {
		        CascadeType.ALL
		    })
	private List<Product> products;
	
	
	public User() {
		
	}
	
	public User(String firstName, String lastName, String email, int phone, int idNumber, String password,
			String verificationCode, boolean enabled) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.idNumber = idNumber;
		this.password = password;
		this.verificationCode = verificationCode;
		this.enabled = enabled;
	
	}
	public User(int id,String firstName, String lastName, String email, int phone, int idNumber, String password,
			String verificationCode, boolean enabled, Collection<UserRole> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.idNumber = idNumber;
		this.password = password;
		this.verificationCode = verificationCode;
		this.enabled = enabled;
		this.roles = roles;
	}

	public User(int id, boolean enabled) {
		this.id = id;
		this.enabled = enabled;
	}

	public String getFirstName() {
		return firstName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public Collection<UserRole> getRoles() {
		return roles;
	}
	public void setRoles(Collection<UserRole> roles) {
		this.roles = roles;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
	

}
