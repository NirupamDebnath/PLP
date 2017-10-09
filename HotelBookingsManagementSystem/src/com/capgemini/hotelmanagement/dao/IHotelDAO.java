package com.capgemini.hotelmanagement.dao;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.capgemini.hotelmanagement.bean.BookingDetailsBean;
import com.capgemini.hotelmanagement.bean.HotelBean;
import com.capgemini.hotelmanagement.bean.RoomDetailsBean;
import com.capgemini.hotelmanagement.bean.UsersBean;
import com.capgemini.hotelmanagement.exception.HotelException;

public interface IHotelDAO {
	
	public String register(UsersBean userBean) throws HotelException, SQLException;
	public boolean logIn(UsersBean userBean)throws HotelException, SQLException;
	public ArrayList<RoomDetailsBean> checkRoomAvailabiliy(String hotelId)throws HotelException, SQLException;
	public int makeBooking(BookingDetailsBean bookingDetaisBean) throws HotelException, SQLException;
	public BookingDetailsBean viewBookingStatus(String bookingId) throws HotelException, SQLException;
	
	public int addHotels(HotelBean hotelBean)throws HotelException, SQLException;
	public int deleteHotels(HotelBean hotelBean)throws HotelException, SQLException;
	public ArrayList<HotelBean> viewHotels()throws HotelException, SQLException;
	
	public String addRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException;
	public int deleteRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException;
	public ArrayList<RoomDetailsBean> viewRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException;
	
	public ArrayList<BookingDetailsBean> viewBookingListOfSpecificHotels(String hotelId) throws HotelException, SQLException;
	public ArrayList<BookingDetailsBean> viewGuestListOfSpecificHotels(String hotelId) throws HotelException, SQLException;
	public ArrayList<BookingDetailsBean> viewBookingsForSpecificDate(LocalDate bookingDate) throws HotelException, SQLException;
	
	public RoomDetailsBean getRoomDetailsBeanById(String id)throws HotelException, SQLException;
	
}
