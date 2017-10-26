package com.capgemini.onlinehotelbookings.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Author : Project Group 4 
 * Class Name : RoomDetailsBean 
 * Package : com.capgemini.hotelmanagement.bean 
 * Date : October 4, 2017
 */
@Entity(name="RoomDetails")
@NamedQuery(name="roomView",query="SELECT u from RoomDetails u")
public class RoomDetailsBean {
	@Column(name="hotel_id")
	private String hotelId;
	@Id
	@Column(name="room_id")
	private String roomId; 
	@Column(name="room_no")
	private String roomNo;
	@Column(name="room_type")
	private String roomType;
	@Column(name="per_night_rate")
	private double perNightRate;
	@Column(name="availability")
	private String availability;
	
	
	public RoomDetailsBean(String hotelId, String roomId, String roomNo,
			String roomType, double perNightRate, String availability) {
		super();
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.roomNo = roomNo;
		this.roomType = roomType;
		this.perNightRate = perNightRate;
		this.availability = availability;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	/**
	 * Getter and Setter Methods
	 */
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public double getPerNightRate() {
		return perNightRate;
	}
	public void setPerNightRate(double perNightRate) {
		this.perNightRate = perNightRate;
	}
	
	
	
	public RoomDetailsBean(){
		super();
	}
	
}
