package com.capgemini.onlinehotelbookings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.onlinehotelbookings.bean.BookingDetailsBean;
import com.capgemini.onlinehotelbookings.bean.HotelBean;
import com.capgemini.onlinehotelbookings.bean.RoomDetailsBean;
import com.capgemini.onlinehotelbookings.bean.UsersBean;
import com.capgemini.onlinehotelbookings.dao.IHotelDao;

@Service
public class HotelService implements IHotelService {

	@Autowired
	IHotelDao hotelDao;
	@Override
	public int register(UsersBean usersBean)
	{
		 return hotelDao.register(usersBean);
	}
	
	@Override
	public boolean logIn(Integer userId,String password)
	{
		return hotelDao.logIn(userId,password);
	}
	
	@Override
	public List<HotelBean> getHotelDetails()
	{
		return hotelDao.getHotelDetails();
	}
	
	@Override
	public List<RoomDetailsBean> getRoomDetails(String hotelId)
	{
		System.out.println("Service hotelID");
		return hotelDao.getRoomDetails(hotelId);
	}
	
	@Override
	public String bookRoom()
	{
		return null;
	}
	@Override
	public int makeBooking(BookingDetailsBean bookingDetailsBean){
		return hotelDao.makeBooking(bookingDetailsBean);
	}

	@Override
	public BookingDetailsBean getBookingDetails(int bookingId) {
		return hotelDao.getBookingDetails(bookingId);
	}
}
