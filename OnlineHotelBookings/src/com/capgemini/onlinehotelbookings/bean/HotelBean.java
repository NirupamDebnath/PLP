package com.capgemini.onlinehotelbookings.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Author : Project Group 4 
 * Class Name : HotelBean 
 * Package : com.capgemini.hotelmanagement.bean 
 * Date : October 4, 2017
 */

@Entity(name="hotel")
@NamedQuery(name="hotelView",query="SELECT u from hotel u")
public class HotelBean {
	@Id
	@Column(name="hotel_id")
	private String hotelId; 
	
	@NotEmpty
	@Column(name="city")
	private String city; 
	
	@NotEmpty
	@Column(name="hotel_name")
	private String hotelName;
	
	@NotEmpty
	@Column(name="address")
	private String address; 
	
	@NotEmpty
	@Column(name="description")
	private String description;
	
	@NotEmpty
	@Column(name="avg_rate_per_night")
	private double avgRatePerNight;
	
	@NotEmpty
	@Column(name="phone_no1")
	private String phoneNo1; 
	
	@NotEmpty
	@Column(name="phone_no2")
	private String phoneNo2; 
	
	@NotEmpty
	@Column(name="rating")
	private int rating;
	
	@NotEmpty
	@Column(name="email")
	private String email;
	
	@NotEmpty
	@Column(name="fax")
	private String fax;
	
	/**
	 * Getter and Setter Methods
	 */
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAvgRatePerNight() {
		return avgRatePerNight;
	}
	public void setAvgRatePerNight(double avgRatePerNight) {
		this.avgRatePerNight = avgRatePerNight;
	}
	public String getPhoneNo1() {
		return phoneNo1;
	}
	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}
	public String getPhoneNo2() {
		return phoneNo2;
	}
	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	/**
	 * Constructors
	 */
	public HotelBean(String hotelId, String city, String hotelName,
			String address, String description, double avgRatePerNight,
			String phoneNo1, String phoneNo2, int rating, String email,
			String fax) {
		super();
		this.hotelId = hotelId;
		this.city = city;
		this.hotelName = hotelName;
		this.address = address;
		this.description = description;
		this.avgRatePerNight = avgRatePerNight;
		this.phoneNo1 = phoneNo1;
		this.phoneNo2 = phoneNo2;
		this.rating = rating;
		this.email = email;
		this.fax = fax;
	}
	public HotelBean(){
		super();
	}
	
	/**
	 * toString() Method
	 */
	@Override
	public String toString() {
		return "HotelBean [hotelId=" + hotelId + ", city=" + city
				+ ", hotelName=" + hotelName + ", address=" + address
				+ ", description=" + description + ", avgRatePerNight="
				+ avgRatePerNight + ", phoneNo1=" + phoneNo1 + ", phoneNo2="
				+ phoneNo2 + ", rating=" + rating + ", email=" + email
				+ ", fax=" + fax + "]";
	}
}
