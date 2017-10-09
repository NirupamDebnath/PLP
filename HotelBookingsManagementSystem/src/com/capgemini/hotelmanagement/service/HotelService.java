package com.capgemini.hotelmanagement.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.capgemini.hotelmanagement.bean.BookingDetailsBean;
import com.capgemini.hotelmanagement.bean.HotelBean;
import com.capgemini.hotelmanagement.bean.RoomDetailsBean;
import com.capgemini.hotelmanagement.bean.UsersBean;
import com.capgemini.hotelmanagement.dao.HotelDAO;
import com.capgemini.hotelmanagement.exception.HotelException;

public class HotelService implements IHotelService {

	public HotelDAO hdao=new HotelDAO();
	
	@Override
	public String register(UsersBean userBean) throws HotelException, SQLException {
		return hdao.register(userBean);
	}

	@Override
	public boolean logIn(UsersBean userBean) throws HotelException, SQLException {
		return hdao.logIn(userBean);
	}

	@Override
	public ArrayList<RoomDetailsBean> checkRoomAvailabiliy(String hotelId)
			throws HotelException, SQLException {
		return hdao.checkRoomAvailabiliy(hotelId);
	}

	@Override
	public int makeBooking(BookingDetailsBean bookingDetaisBean) throws HotelException, SQLException{
		return hdao.makeBooking(bookingDetaisBean);
	}

	@Override
	public BookingDetailsBean viewBookingStatus(String bookingId) throws HotelException, SQLException {
		return hdao.viewBookingStatus(bookingId);
	}

	@Override
	public int addHotels(HotelBean hotelBean) throws HotelException, SQLException {
		return hdao.addHotels(hotelBean);
	}

	@Override
	public int deleteHotels(HotelBean hotelBean) throws HotelException, SQLException {
		return hdao.deleteHotels(hotelBean);
	}

	@Override
	public ArrayList<HotelBean> viewHotels()
			throws HotelException, SQLException {
		return hdao.viewHotels();
	}

	@Override
	public String addRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException {
		return hdao.addRooms(roomDetailsBean);
	}

	@Override
	public int deleteRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException {
		return hdao.deleteRooms(roomDetailsBean);
	}

	@Override
	public ArrayList<RoomDetailsBean> viewRooms(RoomDetailsBean roomDetailsBean)
			throws HotelException, SQLException {
		return hdao.viewRooms(roomDetailsBean);
	}

	@Override
	public ArrayList<BookingDetailsBean> viewBookingListOfSpecificHotels(String hotelId)
			throws HotelException, SQLException {
		return hdao.viewBookingListOfSpecificHotels(hotelId);
	}

	@Override
	public ArrayList<BookingDetailsBean> viewGuestListOfSpecificHotels(String hotelId)
			throws HotelException, SQLException {
		return hdao.viewGuestListOfSpecificHotels(hotelId);
	}

	@Override
	public ArrayList<BookingDetailsBean> viewBookingsForSpecificDate(
			LocalDate bookingDate) throws HotelException, SQLException {
		return hdao.viewBookingsForSpecificDate(bookingDate);
	}
	
	@Override
	public RoomDetailsBean getRoomDetailsBeanById(String id)throws HotelException, SQLException{
		return hdao.getRoomDetailsBeanById(id);
	}

}
