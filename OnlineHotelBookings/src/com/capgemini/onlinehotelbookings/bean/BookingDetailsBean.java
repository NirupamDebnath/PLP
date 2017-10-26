package com.capgemini.onlinehotelbookings.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Author : Project Group 4 
 * Class Name : BookingDetailsBean 
 * Package : com.capgemini.hotelmanagement.bean 
 * Date : October 4, 2017
 */
//edition yet to be done
@Entity(name="BookingDetails")
public class BookingDetailsBean {
	@Id
	@Column(name="booking_id")
	@SequenceGenerator(name = "BookingSequence", sequenceName = "RoomDetails_seq", allocationSize=1,initialValue=2000)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BookingSequence")
	private Integer bookingId;
	@NotEmpty
	@Column(name="room_id")
	private String roomId;
	@NotNull
	@Column(name="user_id")
	private String userId;
	@NotEmpty
	@Column(name="booked_from")
	private String bookedFrom;
	@NotEmpty
	@Column(name="booked_to")
	private String bookedTo;
	@NotNull
	@Column(name="no_of_adults")
	private Integer noOfAdults;
	@NotNull
	@Column(name="no_of_children")
	private Integer noOfChildren;
	@Column(name="amount")
	private Double amount;

	
	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
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
	public String getBookedFrom() {
		return bookedFrom;
	}
	public void setBookedFrom(String bookedFrom) {
		this.bookedFrom = bookedFrom;
	}
	public String getBookedTo() {
		return bookedTo;
	}
	public void setBookedTo(String bookedTo) {
		this.bookedTo = bookedTo;
	}
	public Integer getNoOfAdults() {
		return noOfAdults;
	}
	public void setNoOfAdults(Integer noOfAdults) {
		this.noOfAdults = noOfAdults;
	}
	public Integer getNoOfChildren() {
		return noOfChildren;
	}
	public void setNoOfChildren(Integer noOfChildren) {
		this.noOfChildren = noOfChildren;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public BookingDetailsBean(){
		super();
	}
	public BookingDetailsBean(Integer bookingId, String roomId, String userId,
			String bookedFrom, String bookedTo, Integer noOfAdults,
			Integer noOfChildren, Double amount) {
		super();
		this.bookingId = bookingId;
		this.roomId = roomId;
		this.userId = userId;
		this.bookedFrom = bookedFrom;
		this.bookedTo = bookedTo;
		this.noOfAdults = noOfAdults;
		this.noOfChildren = noOfChildren;
		this.amount = amount;
	}
}
