package com.capgemini.hotelmanagement.bean;

import java.time.LocalDate;

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

}
