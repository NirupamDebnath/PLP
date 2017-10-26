package com.capgemini.onlinehotelbookings.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Author : Project Group 4 
 * Class Name : UsersBean 
 * Package : com.capgemini.hotelmanagement.bean 
 * Date : October 4, 2017
 */
@Entity(name="users")
@NamedQuery(name="usersView",query="SELECT u from users u")
public class UsersBean{

	@Id
	@Column(name="user_id")
	@SequenceGenerator(name = "MySequence", sequenceName = "users_seq", allocationSize=1,initialValue=1000)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MySequence")
	private	Integer userId;
	
	@NotEmpty
	@Column(name="user_name")
	@Size(min=3, max=20)
	private String userName;
	
	@NotEmpty
	@Column(name="password")
	private String password;
	
	@NotEmpty
	@Column(name="role")
	private String role;
	
	@NotEmpty
	@Column(name="mobile_no")
	private String mobileNo;
	
	@NotEmpty
	@Column(name="phone")
	private String phone;
	
	@NotEmpty
	@Column(name="address")
	private String address;
	
	@Email
	@NotEmpty
	@Column(name="email")
	private String email;

	/**
	 * Getter and Setter Methods
	 */

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Constructors
	 */
	public UsersBean(){
		super();
	}

	public UsersBean(Integer userId, String userName, String password,
			String role, String mobileNo, String phone, String address,
			String email) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.mobileNo = mobileNo;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}

	/**
	 * toString() Method
	 */
	@Override
	public String toString() {
		return "UsersBean [userId=" + userId + ", userName=" + userName
				+ ", password=" + password + ", role=" + role + ", mobileNo="
				+ mobileNo + ", phone=" + phone + ", address=" + address
				+ ", email=" + email + "]";
	}
}
