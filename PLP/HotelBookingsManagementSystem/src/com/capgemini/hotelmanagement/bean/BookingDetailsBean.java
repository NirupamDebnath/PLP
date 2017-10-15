package com.capgemini.hotelmanagement.bean;

import java.time.LocalDate;

/**
 * Author : Project Group 4 
 * Class Name : BookingDetailsBean 
 * Package : com.capgemini.hotelmanagement.bean 
 * Date : October 4, 2017
 */
public class BookingDetailsBean {

	private String userName;
	private String bookingId;
	private String roomId;
	private String userId;
	private LocalDate bookedFrom;
	private LocalDate bookedTo;
	private int noOfAdults;
	private int noOfChildren;
	private double amount;

	/**
	 * Getter and Setter Methods
	 */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDate getBookedFrom() {
		return bookedFrom;
	}
	public void setBookedFrom(LocalDate bookedFrom) {
		this.bookedFrom = bookedFrom;
	}
	public LocalDate getBookedTo() {
		return bookedTo;
	}
	public void setBookedTo(LocalDate bookedTo) {
		this.bookedTo = bookedTo;
	}
	public int getNoOfAdults() {
		return noOfAdults;
	}
	public void setNoOfAdults(int noOfAdults) {
		this.noOfAdults = noOfAdults;
	}
	public int getNoOfChildren() {
		return noOfChildren;
	}
	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Constructors
	 */
	public BookingDetailsBean(String userName, String bookingId, String roomId,
			String userId, LocalDate bookedFrom, LocalDate bookedTo,
			int noOfAdults, int noOfChildren, double amount) {
		super();
		this.userName = userName;
		this.bookingId = bookingId;
		this.roomId = roomId;
		this.userId = userId;
		this.bookedFrom = bookedFrom;
		this.bookedTo = bookedTo;
		this.noOfAdults = noOfAdults;
		this.noOfChildren = noOfChildren;
		this.amount = amount;
	}
	public BookingDetailsBean(){
		super();
	}
	
	/**
	 * toString() Method
	 */
	@Override
	public String toString() {
		return "BookingDetailsBean [userName=" + userName + ", bookingId="
				+ bookingId + ", roomId=" + roomId + ", userId=" + userId
				+ ", bookedFrom=" + bookedFrom + ", bookedTo=" + bookedTo
				+ ", noOfAdults=" + noOfAdults + ", noOfChildren="
				+ noOfChildren + ", amount=" + amount + "]";
	}
}
