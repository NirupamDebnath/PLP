package com.capgemini.onlinehotelbookings.dao;

import java.util.List;

import com.capgemini.onlinehotelbookings.bean.BookingDetailsBean;
import com.capgemini.onlinehotelbookings.bean.HotelBean;
import com.capgemini.onlinehotelbookings.bean.RoomDetailsBean;
import com.capgemini.onlinehotelbookings.bean.UsersBean;

public interface IHotelDao {

	public int register(UsersBean usersBean);
	public boolean logIn(Integer userId,String password);
	public List<HotelBean> getHotelDetails();
	public List<RoomDetailsBean> getRoomDetails(String hotelId);
	public int makeBooking(BookingDetailsBean bookingDetailsBean);
	public BookingDetailsBean getBookingDetails(int bookingId);
}
