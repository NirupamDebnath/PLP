package com.capgemini.hotelmanagement.bean;

/**
 * Author : Project Group 4 
 * Class Name : RoomDetailsBean 
 * Package : com.capgemini.hotelmanagement.bean 
 * Date : October 4, 2017
 */
public class RoomDetailsBean {
	private String hotelId; 
	private String roomId;  
	private String roomNo;
	private String roomType;
	private double perNightRate; 
	private boolean availability;
	
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
	public boolean isAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
	/**
	 * Constructors
	 */
	public RoomDetailsBean(String hotelId, String roomId, String roomNo,
			String roomType, double perNightRate, boolean availability) {
		super();
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.roomNo = roomNo;
		this.roomType = roomType;
		this.perNightRate = perNightRate;
		this.availability = availability;
	}
	public RoomDetailsBean(){
		super();
	}
	
	/**
	 * toString() Method
	 */
	@Override
	public String toString() {
		return "RoomDetailsBean [hotelId=" + hotelId + ", roomId=" + roomId
				+ ", roomNo=" + roomNo + ", roomType=" + roomType
				+ ", perNightRate=" + perNightRate + ", availability="
				+ availability + "]";
	}
}
