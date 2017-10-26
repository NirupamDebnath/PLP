package com.capgemini.onlinehotelbookings.service;

import java.util.List;

import com.capgemini.onlinehotelbookings.bean.BookingDetailsBean;
import com.capgemini.onlinehotelbookings.bean.HotelBean;
import com.capgemini.onlinehotelbookings.bean.RoomDetailsBean;
import com.capgemini.onlinehotelbookings.bean.UsersBean;

public interface IHotelService {

	public int register(UsersBean usersBean);
	public boolean logIn(Integer userId,String password);
	public List<HotelBean> getHotelDetails();
	public List<RoomDetailsBean> getRoomDetails(String hotelId);
	public String bookRoom();
	public int makeBooking(BookingDetailsBean bookingDetailsBean);
	public BookingDetailsBean getBookingDetails(int bookingId);
}
