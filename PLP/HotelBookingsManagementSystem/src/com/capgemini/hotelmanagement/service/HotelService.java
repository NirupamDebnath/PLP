package com.capgemini.hotelmanagement.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.hotelmanagement.bean.BookingDetailsBean;
import com.capgemini.hotelmanagement.bean.HotelBean;
import com.capgemini.hotelmanagement.bean.RoomDetailsBean;
import com.capgemini.hotelmanagement.bean.UsersBean;
import com.capgemini.hotelmanagement.dao.HotelDAO;
import com.capgemini.hotelmanagement.exception.HotelException;

/**
 * Author : Project Group 4 
 * Class Name : HotelService
 * Package : com.capgemini.hotelmanagement.service 
 * Date : October 7, 2017
 */
public class HotelService implements IHotelService {

	public HotelDAO hotelDao=new HotelDAO();
	
	@Override
	public String register(UsersBean userBean) throws HotelException, SQLException {
		return hotelDao.register(userBean);
	}

	@Override
	public boolean logIn(UsersBean userBean) throws HotelException, SQLException {
		return hotelDao.logIn(userBean);
	}

	@Override
	public ArrayList<RoomDetailsBean> checkRoomAvailabiliy(String hotelId)
			throws HotelException, SQLException {
		return hotelDao.checkRoomAvailabiliy(hotelId);
	}

	@Override
	public int makeBooking(BookingDetailsBean bookingDetaisBean) throws HotelException, SQLException{
		return hotelDao.makeBooking(bookingDetaisBean);
	}

	@Override
	public BookingDetailsBean viewBookingStatus(String bookingId) throws HotelException, SQLException {
		return hotelDao.viewBookingStatus(bookingId);
	}

	@Override
	public int addHotels(HotelBean hotelBean) throws HotelException, SQLException {
		return hotelDao.addHotels(hotelBean);
	}

	@Override
	public int deleteHotels(HotelBean hotelBean) throws HotelException, SQLException {
		return hotelDao.deleteHotels(hotelBean);
	}

	@Override
	public ArrayList<HotelBean> viewHotels()
			throws HotelException, SQLException {
		return hotelDao.viewHotels();
	}

	@Override
	public String addRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException {
		return hotelDao.addRooms(roomDetailsBean);
	}

	@Override
	public int deleteRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException {
		return hotelDao.deleteRooms(roomDetailsBean);
	}

	@Override
	public ArrayList<RoomDetailsBean> viewRooms(RoomDetailsBean roomDetailsBean)
			throws HotelException, SQLException {
		return hotelDao.viewRooms(roomDetailsBean);
	}

	@Override
	public ArrayList<BookingDetailsBean> viewBookingListOfSpecificHotels(String hotelId)
			throws HotelException, SQLException {
		return hotelDao.viewBookingListOfSpecificHotels(hotelId);
	}

	@Override
	public ArrayList<BookingDetailsBean> viewGuestListOfSpecificHotels(String hotelId)
			throws HotelException, SQLException {
		return hotelDao.viewGuestListOfSpecificHotels(hotelId);
	}

	@Override
	public ArrayList<BookingDetailsBean> viewBookingsForSpecificDate(
			LocalDate bookingDate) throws HotelException, SQLException {
		return hotelDao.viewBookingsForSpecificDate(bookingDate);
	}
	
	@Override
	public RoomDetailsBean getRoomDetailsBeanById(String id)throws HotelException, SQLException{
		return hotelDao.getRoomDetailsBeanById(id);
	}

	public boolean hotelIdValidator(String hotelId){
		Pattern pattern=Pattern.compile("[0-9]{3}");
		Matcher matcher=pattern.matcher(hotelId);
		return matcher.matches();
	}
	public boolean userIdValidator(String userId){
		Pattern pattern=Pattern.compile("[0-9]{4}");
		Matcher matcher=pattern.matcher(userId);
		return matcher.matches();
	}
	public boolean bookingIdValidator(String bookingId){
		Pattern pattern=Pattern.compile("[0-9]{4}");
		Matcher matcher=pattern.matcher(bookingId);
		return matcher.matches();
	}
	public boolean userPasswordValidator(String password){
		Pattern pattern=Pattern.compile("[a-zA-Z0-9]{1,7}");
		Matcher matcher=pattern.matcher(password);
		return matcher.matches();
	}
	public boolean stringValidator(String string){
		Pattern pattern=Pattern.compile("[a-zA-Z\\s]{3,}");
		Matcher matcher=pattern.matcher(string);
		return matcher.matches();
	}
	public boolean phoneNumberValidator(String phone){
		Pattern pattern=Pattern.compile("[789]{1}[0-9]{9}");
		Matcher matcher=pattern.matcher(phone);
		return matcher.matches();
	}
	public boolean emailValidator(String email){
		Pattern pattern=Pattern.compile("^(.+)@(.+)${12,}");
		Matcher matcher=pattern.matcher(email);
		return matcher.matches();
	}
	public boolean faxValidator(String fax){
		Pattern pattern=Pattern.compile("[1-9]{1}[0-9]{6,}");
		Matcher matcher=pattern.matcher(fax);
		return matcher.matches();
	}
}