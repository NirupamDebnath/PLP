package com.capgemini.hotelmanagement.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.hotelmanagement.bean.BookingDetailsBean;
import com.capgemini.hotelmanagement.bean.HotelBean;
import com.capgemini.hotelmanagement.bean.RoomDetailsBean;
import com.capgemini.hotelmanagement.bean.UsersBean;
import com.capgemini.hotelmanagement.exception.HotelException;
import com.capgemini.hotelmanagement.util.DBConnection;

/**
 * Author : Project Group 4 
 * Class Name : HotelDAO
 * Package : com.capgemini.hotelmanagement.dao 
 * Date : October 7, 2017
 */
public class HotelDAO implements IHotelDAO {

	Logger logger=Logger.getRootLogger();

	@Override
	public String register(UsersBean useroomDetailsBean) throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.INSERT_INTO_USERS);
		
		preparedStatement.setString(1, useroomDetailsBean.getPassword());
		preparedStatement.setString(2, useroomDetailsBean.getRole());
		preparedStatement.setString(3, useroomDetailsBean.getUserName());
		preparedStatement.setString(4, useroomDetailsBean.getMobileNo());
		preparedStatement.setString(5, useroomDetailsBean.getPhone());
		preparedStatement.setString(6, useroomDetailsBean.getAddress());
		preparedStatement.setString(7, useroomDetailsBean.getEmail());
		preparedStatement.executeUpdate();
		
		PreparedStatement preparedStatement2=connection.prepareStatement(QueryMapper.USERS_SEQ_CUR_VAL);
		ResultSet resultSet= preparedStatement2.executeQuery();
		resultSet.next();
		String userId=resultSet.getString(1);
		connection.close();
		
		logger.info("Insertion Successful for User Id : "+userId);
		return userId;
	}

	@Override
	public boolean logIn(UsersBean userBean) throws HotelException, SQLException {
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.GET_PASSWORD_FROM_USERS);
		preparedStatement.setString(1, userBean.getUserId());
		ResultSet resultSet= preparedStatement.executeQuery();
		if(resultSet.next()){
			if(resultSet.getString(1).equals(userBean.getPassword())){
				return true;
			}
		}
		connection.close();
		return false;
	}

	@Override
	public ArrayList<RoomDetailsBean> checkRoomAvailabiliy(String hotelId)
			throws HotelException, SQLException {
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.GET_DETAILS_FROM_ROOMDETAILS);
		preparedStatement.setString(1, hotelId);
		ResultSet resultSet= preparedStatement.executeQuery();
		ArrayList<RoomDetailsBean> roomDetailsList=new ArrayList<>();
		int flag=0;
		while(resultSet.next()){
			flag++;
			RoomDetailsBean rdbean=new RoomDetailsBean();
			rdbean.setHotelId(resultSet.getString(1));
			rdbean.setRoomId(resultSet.getString(2));
			rdbean.setRoomNo(resultSet.getString(3));
			rdbean.setRoomType(resultSet.getString(4));
			rdbean.setPerNightRate(resultSet.getDouble(5));
			rdbean.setAvailability(resultSet.getString(6).equals("yes")?true:false);
			roomDetailsList.add(rdbean);
		}
		if(flag==0)
			return null;
		return roomDetailsList;
	}

	@Override
	public int makeBooking(BookingDetailsBean bookingDetaisBean) throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		Connection connection=DBConnection.getInstance().getConnection();
		
		PreparedStatement viewUserAvailability=connection.prepareStatement(QueryMapper.VIEW_USER_AVAILABILITY);
		viewUserAvailability.setString(1,bookingDetaisBean.getUserId());
		ResultSet userResultSet=viewUserAvailability.executeQuery();
		if(!userResultSet.next()){
			throw new HotelException("User Not Available");
		}
		
		PreparedStatement roomDetailsUpdateStatement=
				connection.prepareStatement(QueryMapper.UPDATE_ROOM_AVAILABILITY);
		roomDetailsUpdateStatement.setString(1, bookingDetaisBean.getRoomId());
		roomDetailsUpdateStatement.executeUpdate();
		
		PreparedStatement insertIntoBookingStatement=
				connection.prepareStatement(QueryMapper.INSERT_INTO_BOOKINGDETAILS);
		insertIntoBookingStatement.setString(1, bookingDetaisBean.getRoomId());
		insertIntoBookingStatement.setString(2, bookingDetaisBean.getUserId());
		insertIntoBookingStatement.setDate(3, Date.valueOf(bookingDetaisBean.getBookedFrom()));
		insertIntoBookingStatement.setDate(4, Date.valueOf(bookingDetaisBean.getBookedTo()));
		insertIntoBookingStatement.setInt(5, bookingDetaisBean.getNoOfAdults());
		insertIntoBookingStatement.setInt(6, bookingDetaisBean.getNoOfChildren());
		insertIntoBookingStatement.setDouble(7, bookingDetaisBean.getAmount());
		insertIntoBookingStatement.executeUpdate();
		
		PreparedStatement bookingIdStmt=connection.prepareStatement(QueryMapper.BOOKINGDETAILS_SEQ_CURRVAL);
		ResultSet resultSet=bookingIdStmt.executeQuery();
		resultSet.next();
		int bookingId=resultSet.getInt(1);
		connection.close();
		logger.info("Booking Successful for Booking Id : "+bookingId);
		return bookingId;
	}
	
	@Override
	public BookingDetailsBean viewBookingStatus(String bookingId) throws HotelException, SQLException {
		BookingDetailsBean bookingDetailsBean=null;
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement=connection.prepareStatement(QueryMapper.GET_DETAILS_FROM_BOOKING_DETAILS);
		preparedStatement.setString(1, bookingId);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()) {
			bookingDetailsBean=new BookingDetailsBean();
			bookingDetailsBean.setBookingId(resultSet.getString(1));
			bookingDetailsBean.setRoomId(resultSet.getString(2));
			bookingDetailsBean.setUserId(resultSet.getString(3));
			bookingDetailsBean.setBookedFrom(resultSet.getDate(4).toLocalDate());
			bookingDetailsBean.setBookedTo(resultSet.getDate(5).toLocalDate());
			bookingDetailsBean.setNoOfAdults(resultSet.getInt(6));
			bookingDetailsBean.setNoOfChildren(resultSet.getInt(7));
			bookingDetailsBean.setAmount(resultSet.getDouble(8));
		}
		connection.close();
		return bookingDetailsBean;
	}

	@Override
	public int addHotels(HotelBean hotelBean) throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.INSERT_INTO_HOTEL);
		preparedStatement.setString(1, hotelBean.getCity());
		preparedStatement.setString(2, hotelBean.getHotelName());
		preparedStatement.setString(3, hotelBean.getAddress());
		preparedStatement.setString(4, hotelBean.getDescription());
		preparedStatement.setDouble(5, hotelBean.getAvgRatePerNight());
		preparedStatement.setString(6, hotelBean.getPhoneNo1());
		preparedStatement.setString(7, hotelBean.getPhoneNo2());
		preparedStatement.setInt(8, hotelBean.getRating());
		preparedStatement.setString(9, hotelBean.getEmail());
		preparedStatement.setString(10, hotelBean.getFax());
		int state=preparedStatement.executeUpdate();
		connection.close();
		logger.info("Hotel "+hotelBean.getHotelName()+" Added Successfully !!");
		return state;
	}

	@Override
	public int deleteHotels(HotelBean hotelBean) throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.DELETE_FROM_HOTEL);
		preparedStatement.setString(1, hotelBean.getHotelId());
		
		PreparedStatement getRoomDetailsStatement=connection.prepareStatement(QueryMapper.GET_ROOM_DETAILS);
		getRoomDetailsStatement.setString(1, hotelBean.getHotelId());
		ResultSet getRoomResultSet=getRoomDetailsStatement.executeQuery();
		while(getRoomResultSet.next()){
			RoomDetailsBean roomDetailsBean=new RoomDetailsBean();
			roomDetailsBean.setRoomId(getRoomResultSet.getString(1));
			deleteRooms(roomDetailsBean);
		}
		
		int status=preparedStatement.executeUpdate();
		connection.close();
		logger.info("Hotel with Id "+hotelBean.getHotelId()+" Deleted Successfully !!");
		return status;
	}

	@Override
	public ArrayList<HotelBean> viewHotels()
			throws HotelException, SQLException {
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.GET_ALL_DATA_FROM_HOTEL);
		ResultSet resultSet= preparedStatement.executeQuery();
		ArrayList<HotelBean> hList=new ArrayList<>();
		int flag=0;
		while(resultSet.next()){
			HotelBean hotelBean=new HotelBean();
			hotelBean.setHotelId(resultSet.getString(1));
			hotelBean.setCity(resultSet.getString(2));
			hotelBean.setHotelName(resultSet.getString(3));
			hotelBean.setAddress(resultSet.getString(4));
			hotelBean.setDescription(resultSet.getString(5));
			hotelBean.setAvgRatePerNight(resultSet.getDouble(6));
			hotelBean.setPhoneNo1(resultSet.getString(7));
			hotelBean.setPhoneNo2(resultSet.getString(8));
			hotelBean.setRating(resultSet.getInt(9));
			hotelBean.setEmail(resultSet.getString(10));
			hotelBean.setFax(resultSet.getString(11));
			hList.add(hotelBean);
			flag++;
		}
		if(flag==0)
			return null;
		return hList;
	}

	@Override
	public String addRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement=connection.prepareStatement(QueryMapper.INSERT_INTO_ROOMDETAILS);
		preparedStatement.setString(1, roomDetailsBean.getHotelId());
		preparedStatement.setString(2, roomDetailsBean.getRoomNo());
		preparedStatement.setString(3, roomDetailsBean.getRoomType());
		preparedStatement.setDouble(4, roomDetailsBean.getPerNightRate());
		preparedStatement.execute();
		
		preparedStatement=connection.prepareStatement(QueryMapper.ROOMDETAILS_SEQ_CURRVAL);
		ResultSet resultSet=preparedStatement.executeQuery();
		resultSet.next();
		String roomId=resultSet.getString(1);
		connection.close();
		logger.info("Room "+roomId+" Added Successfully !!");
		return roomId;
	}

	@Override
	public int deleteRooms(RoomDetailsBean roomDetailsBean) throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement1= connection.prepareStatement(QueryMapper.DELETE_FROM_BOOKINGDETAILS);
		PreparedStatement preparedStatement2= connection.prepareStatement(QueryMapper.DELETE_FROM_ROOMDETAILS_BY_ROOMID);
		preparedStatement1.setString(1, roomDetailsBean.getRoomId());
		preparedStatement2.setString(1, roomDetailsBean.getRoomId());
		preparedStatement1.executeUpdate();
		int status=preparedStatement2.executeUpdate();
		connection.close();
		logger.info("Room with Id "+roomDetailsBean.getRoomId()+" Deleted Successfully !!");
		return status;
	}

	@Override
	public ArrayList<RoomDetailsBean> viewRooms(RoomDetailsBean roomDetailsBean)
			throws HotelException, SQLException {
		ArrayList<RoomDetailsBean> roomDetailsList=new ArrayList<>();
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.GET_DETAILS_FROM_ROOMDETAILS);
		preparedStatement.setString(1, roomDetailsBean.getHotelId());
		ResultSet resultSet= preparedStatement.executeQuery();
		int flag=1;
		while(resultSet.next()){
			RoomDetailsBean rdBean=new RoomDetailsBean();
			rdBean.setHotelId(resultSet.getString(1));
			rdBean.setRoomId(resultSet.getString(2));
			rdBean.setRoomNo(resultSet.getString(3));
			rdBean.setRoomType(resultSet.getString(4));
			rdBean.setPerNightRate(resultSet.getDouble(5));
			rdBean.setAvailability(resultSet.getString(6).equals("yes")?true:false);
			roomDetailsList.add(rdBean);
			flag++;
		}
		if(flag==0)
			return null;
		return roomDetailsList;
	}

	@Override
	public ArrayList<BookingDetailsBean> viewBookingListOfSpecificHotels(String hotelId)throws HotelException, SQLException {
		ArrayList<BookingDetailsBean> bookingDetailsList=new ArrayList<>();
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.VIEW_BOOKING_LIST_OF_SPECIFIC_HOTEL);
		preparedStatement.setString(1, hotelId);
		ResultSet resultSet=preparedStatement.executeQuery();
		int flag=0;
		while(resultSet.next()) {
			BookingDetailsBean bookingDetailsBean=new BookingDetailsBean();
			bookingDetailsBean.setBookingId(resultSet.getString(1));
			bookingDetailsBean.setRoomId(resultSet.getString(2));
			bookingDetailsBean.setUserId(resultSet.getString(3));
			bookingDetailsBean.setBookedFrom(resultSet.getDate(4).toLocalDate());
			bookingDetailsBean.setBookedTo(resultSet.getDate(5).toLocalDate());
			bookingDetailsBean.setNoOfAdults(resultSet.getInt(6));
			bookingDetailsBean.setNoOfChildren(resultSet.getInt(7));
			bookingDetailsBean.setAmount(resultSet.getDouble(8));
			bookingDetailsList.add(bookingDetailsBean);
			flag++;
		}
		if(flag==0)
			return null;
		return bookingDetailsList;
	}

	@Override
	public ArrayList<BookingDetailsBean> viewGuestListOfSpecificHotels(String hotelId)
			throws HotelException, SQLException {
		ArrayList<BookingDetailsBean> bookingDetailsList=new ArrayList<>();
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.VIEW_GUEST_LIST_OF_SPECIFIC_HOTEL);
		preparedStatement.setString(1, hotelId);
		ResultSet resultSet=preparedStatement.executeQuery();
		int flag=0;
		while(resultSet.next()) {
			BookingDetailsBean bookingDetailsBean=new BookingDetailsBean();
			bookingDetailsBean.setRoomId(resultSet.getString(1));
			bookingDetailsBean.setUserId(resultSet.getString(2));
			bookingDetailsBean.setUserName(resultSet.getString(3));
			bookingDetailsList.add(bookingDetailsBean);
			flag++;
		}
		if(flag==0)
			return null;
		return bookingDetailsList;
	}

	@Override
	public ArrayList<BookingDetailsBean> viewBookingsForSpecificDate(
			LocalDate bookingDate) throws HotelException, SQLException {
		ArrayList<BookingDetailsBean> bookingDetailsList=new ArrayList<>();
		Connection connection=DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement= connection.prepareStatement(QueryMapper.VIEW_BOOKING_BY_DATE);
		preparedStatement.setDate(1, Date.valueOf(bookingDate));
		preparedStatement.setDate(2, Date.valueOf(bookingDate));
		ResultSet resultSet=preparedStatement.executeQuery();
		int flag=0;
		while(resultSet.next()) {
			BookingDetailsBean bookingDetailsBean=new BookingDetailsBean();
			bookingDetailsBean.setBookingId(resultSet.getString(1));
			bookingDetailsBean.setRoomId(resultSet.getString(2));
			bookingDetailsBean.setUserId(resultSet.getString(3));
			bookingDetailsBean.setBookedFrom(resultSet.getDate(4).toLocalDate());
			bookingDetailsBean.setBookedTo(resultSet.getDate(5).toLocalDate());
			bookingDetailsBean.setNoOfAdults(resultSet.getInt(6));
			bookingDetailsBean.setNoOfChildren(resultSet.getInt(7));
			bookingDetailsBean.setAmount(resultSet.getDouble(8));
			bookingDetailsList.add(bookingDetailsBean);
			flag++;
		}
		if(flag==0) {
			return null;
		}
		return bookingDetailsList;
	}
	
	@Override
	public RoomDetailsBean getRoomDetailsBeanById(String id)throws HotelException, SQLException{
			Connection connection=DBConnection.getInstance().getConnection();
			PreparedStatement preparedStatement= connection.prepareStatement("select * from ROOMDETAILS where availability like 'yes' and room_id = ?");
			preparedStatement.setString(1, id);
			ResultSet resultSet= preparedStatement.executeQuery();
			RoomDetailsBean roomDetailsBean=null;
			if(resultSet.next()){
				roomDetailsBean=new RoomDetailsBean();
				roomDetailsBean.setHotelId(resultSet.getString(1));
				roomDetailsBean.setRoomId(resultSet.getString(2));
				roomDetailsBean.setRoomNo(resultSet.getString(3));
				roomDetailsBean.setRoomType(resultSet.getString(4));
				roomDetailsBean.setPerNightRate(resultSet.getDouble(5));
				roomDetailsBean.setAvailability(resultSet.getString(6).equals("yes")?true:false);
			}
		return roomDetailsBean;
	}
}
