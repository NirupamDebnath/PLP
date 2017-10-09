package com.capgemini.hotelmanagement.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.capgemini.hotelmanagement.bean.BookingDetailsBean;
import com.capgemini.hotelmanagement.bean.HotelBean;
import com.capgemini.hotelmanagement.bean.RoomDetailsBean;
import com.capgemini.hotelmanagement.bean.UsersBean;
import com.capgemini.hotelmanagement.exception.HotelException;
import com.capgemini.hotelmanagement.util.DBConnection;

public class HotelDAO implements IHotelDAO {

	
	@Override
	public String register(UsersBean userBean) throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.INSERT_INTO_USERS);
		
		ps.setString(1, userBean.getPassword());
		ps.setString(2, userBean.getRole());
		ps.setString(3, userBean.getUserName());
		ps.setString(4, userBean.getMobileNo());
		ps.setString(5, userBean.getPhone());
		ps.setString(6, userBean.getAddress());
		ps.setString(7, userBean.getEmail());
		ps.executeUpdate();
		
		PreparedStatement ps2=conn.prepareStatement(QueryMapper.USERS_SEQ_CUR_VAL);
		ResultSet rs= ps2.executeQuery();
		rs.next();
		String uId=rs.getString(1);
		conn.close();
		return uId;
	}

	@Override
	public boolean logIn(UsersBean userBean) throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.GET_PASSWORD_FROM_USERS);
		ps.setString(1, userBean.getUserId());
		ResultSet rs= ps.executeQuery();
		if(rs.next()){
			if(rs.getString(1).equals(userBean.getPassword())){
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ArrayList<RoomDetailsBean> checkRoomAvailabiliy(String hotelId)
			throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.GET_DETAILS_FROM_ROOMDETAILS);
		ps.setString(1, hotelId);
		ResultSet rs= ps.executeQuery();
		ArrayList<RoomDetailsBean> rdList=null;
		while(rs.next()){
			rdList=new ArrayList<>();
			RoomDetailsBean rdbean=new RoomDetailsBean();
			rdbean.setHotelId(rs.getString(1));
			rdbean.setRoomId(rs.getString(2));
			rdbean.setRoomNo(rs.getString(3));
			rdbean.setRoomType(rs.getString(4));
			rdbean.setPerNightRate(rs.getDouble(5));
			rdbean.setAvailability(rs.getString(6).equals("yes")?true:false);
			rdList.add(rdbean);
		}
		return rdList;
	}

	@Override
	public int makeBooking(BookingDetailsBean bookingDetaisBean) throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement roomDetailsUpdateStatement=
				conn.prepareStatement(QueryMapper.UPDATE_ROOM_AVAILABILITY);
		roomDetailsUpdateStatement.setString(1, bookingDetaisBean.getRoomId());
		roomDetailsUpdateStatement.executeUpdate();
		
		PreparedStatement insertIntoBookingStatement=
				conn.prepareStatement(QueryMapper.INSERT_INTO_BOOKINGDETAILS);
		insertIntoBookingStatement.setString(1, bookingDetaisBean.getRoomId());
		insertIntoBookingStatement.setString(2, bookingDetaisBean.getUserId());
		insertIntoBookingStatement.setDate(3, Date.valueOf(bookingDetaisBean.getBookedFrom()));
		insertIntoBookingStatement.setDate(4, Date.valueOf(bookingDetaisBean.getBookedTo()));
		insertIntoBookingStatement.setInt(5, bookingDetaisBean.getNoOfAdults());
		insertIntoBookingStatement.setInt(6, bookingDetaisBean.getNoOfChildren());
		insertIntoBookingStatement.setDouble(7, bookingDetaisBean.getAmount());
		insertIntoBookingStatement.executeUpdate();
		
		PreparedStatement bookingIdStmt=conn.prepareStatement(QueryMapper.BOOKINGDETAILS_SEQ_CURRVAL);
		ResultSet rs=bookingIdStmt.executeQuery();
		rs.next();
		int bookId=rs.getInt(1);
		return bookId;
	}

	@Override
	public BookingDetailsBean viewBookingStatus(String bookingId) throws HotelException, SQLException {
		BookingDetailsBean bdBean=null;
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps=conn.prepareStatement(QueryMapper.GET_DETAILS_FROM_BOOKING_DETAILS);
		ps.setString(1, bookingId);
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			bdBean=new BookingDetailsBean();
			bdBean.setBookingId(rs.getString(1));
			bdBean.setRoomId(rs.getString(2));
			bdBean.setUserId(rs.getString(3));
			bdBean.setBookedFrom(rs.getDate(4).toLocalDate());
			bdBean.setBookedTo(rs.getDate(5).toLocalDate());
			bdBean.setNoOfAdults(rs.getInt(6));
			bdBean.setNoOfChildren(rs.getInt(7));
			bdBean.setAmount(rs.getDouble(8));
		}
		return bdBean;
	}

	@Override
	public int addHotels(HotelBean hotelBean) throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.INSERT_INTO_HOTEL);
		ps.setString(1, hotelBean.getCity());
		ps.setString(2, hotelBean.getHotelName());
		ps.setString(3, hotelBean.getAddress());
		ps.setString(4, hotelBean.getDescription());
		ps.setDouble(5, hotelBean.getAvgRatePerNight());
		ps.setString(6, hotelBean.getPhoneNo1());
		ps.setString(7, hotelBean.getPhoneNo2());
		ps.setInt(8, hotelBean.getRating());
		ps.setString(9, hotelBean.getEmail());
		ps.setString(10, hotelBean.getFax());
		return ps.executeUpdate();
	}

	@Override
	public int deleteHotels(HotelBean hotelBean) throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps1= conn.prepareStatement(QueryMapper.DELETE_FROM_HOTEL);
		PreparedStatement ps2= conn.prepareStatement(QueryMapper.DELETE_FROM_ROOMDETAILS_BY_HOTELID);
		ps1.setString(1, hotelBean.getHotelId());
		ps2.setString(1, hotelBean.getHotelId());
		ps2.executeUpdate();
		int status=ps1.executeUpdate();
		return status;
	}

	@Override
	public ArrayList<HotelBean> viewHotels()
			throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.GET_ALL_DATA_FROM_HOTEL);
		ResultSet rs= ps.executeQuery();
		ArrayList<HotelBean> hList=new ArrayList<>();
		int flag=0;
		while(rs.next()){
			HotelBean hBean=new HotelBean();
			hBean.setHotelId(rs.getString(1));
			hBean.setCity(rs.getString(2));
			hBean.setHotelName(rs.getString(3));
			hBean.setAddress(rs.getString(4));
			hBean.setDescription(rs.getString(5));
			hBean.setAvgRatePerNight(rs.getDouble(6));
			hBean.setPhoneNo1(rs.getString(7));
			hBean.setPhoneNo2(rs.getString(8));
			hBean.setRating(rs.getInt(9));
			hBean.setEmail(rs.getString(10));
			hBean.setFax(rs.getString(11));
			hList.add(hBean);
			flag++;
		}
		if(flag==0)
			return null;
		return hList;
	}

	@Override
	public String addRooms(RoomDetailsBean roomDetailsBean)throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps=conn.prepareStatement(QueryMapper.INSERT_INTO_ROOMDETAILS);
		ps.setString(1, roomDetailsBean.getHotelId());
		ps.setString(2, roomDetailsBean.getRoomNo());
		ps.setString(3, roomDetailsBean.getRoomType());
		ps.setDouble(4, roomDetailsBean.getPerNightRate());
		ps.execute();
		
		ps=conn.prepareStatement(QueryMapper.ROOMDETAILS_SEQ_CURRVAL);
		ResultSet rs=ps.executeQuery();
		rs.next();
		return rs.getString(1);
	}

	@Override
	public int deleteRooms(RoomDetailsBean roomDetailsBean) throws HotelException, SQLException {
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps1= conn.prepareStatement(QueryMapper.DELETE_FROM_BOOKINGDETAILS);
		PreparedStatement ps2= conn.prepareStatement(QueryMapper.DELETE_FROM_ROOMDETAILS_BY_ROOMID);
		ps1.setString(1, roomDetailsBean.getRoomId());
		ps2.setString(1, roomDetailsBean.getRoomId());
		ps1.executeUpdate();
		int status=ps2.executeUpdate();
		return status;
	}

	@Override
	public ArrayList<RoomDetailsBean> viewRooms(RoomDetailsBean roomDetailsBean)
			throws HotelException, SQLException {
		ArrayList<RoomDetailsBean> rdList=new ArrayList<>();
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.GET_DETAILS_FROM_ROOMDETAILS);
		ps.setString(1, roomDetailsBean.getHotelId());
		ResultSet rs= ps.executeQuery();
		int flag=1;
		while(rs.next()){
			RoomDetailsBean rdBean=new RoomDetailsBean();
			rdBean.setHotelId(rs.getString(1));
			rdBean.setRoomId(rs.getString(2));
			rdBean.setRoomNo(rs.getString(3));
			rdBean.setRoomType(rs.getString(4));
			rdBean.setPerNightRate(rs.getDouble(5));
			rdBean.setAvailability(rs.getString(6).equals("yes")?true:false);
			rdList.add(rdBean);
			flag++;
		}
		if(flag==0)
			return null;
		return rdList;
	}

	@Override
	public ArrayList<BookingDetailsBean> viewBookingListOfSpecificHotels(String hotelId)throws HotelException, SQLException {
		ArrayList<BookingDetailsBean> bdList=new ArrayList<>();
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.VIEW_BOOKING_LIST_OF_SPECIFIC_HOTEL);
		ps.setString(1, hotelId);
		ResultSet rs=ps.executeQuery();
		int flag=0;
		while(rs.next()) {
			BookingDetailsBean bdBean=new BookingDetailsBean();
			bdBean.setBookingId(rs.getString(1));
			bdBean.setRoomId(rs.getString(2));
			bdBean.setUserId(rs.getString(3));
			bdBean.setBookedFrom(rs.getDate(4).toLocalDate());
			bdBean.setBookedTo(rs.getDate(5).toLocalDate());
			bdBean.setNoOfAdults(rs.getInt(6));
			bdBean.setNoOfChildren(rs.getInt(7));
			bdBean.setAmount(rs.getDouble(8));
			bdList.add(bdBean);
			flag++;
		}
		if(flag==0)
			return null;
		return bdList;
	}

	@Override
	public ArrayList<BookingDetailsBean> viewGuestListOfSpecificHotels(String hotelId)
			throws HotelException, SQLException {
		ArrayList<BookingDetailsBean> bdList=new ArrayList<>();
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.VIEW_GUEST_LIST_OF_SPECIFIC_HOTEL);
		ps.setString(1, hotelId);
		ResultSet rs=ps.executeQuery();
		int flag=0;
		while(rs.next()) {
			BookingDetailsBean bdBean=new BookingDetailsBean();
			bdBean.setRoomId(rs.getString(1));
			bdBean.setUserId(rs.getString(2));
			bdBean.setUserName(rs.getString(3));
			bdList.add(bdBean);
			flag++;
		}
		if(flag==0)
			return null;
		return bdList;
	}

	@Override
	public ArrayList<BookingDetailsBean> viewBookingsForSpecificDate(
			LocalDate bookingDate) throws HotelException, SQLException {
		ArrayList<BookingDetailsBean> bdList=new ArrayList<>();
		Connection conn=DBConnection.getInstance().getConnection();
		PreparedStatement ps= conn.prepareStatement(QueryMapper.VIEW_BOOKING_BY_DATE);
		ps.setDate(1, Date.valueOf(bookingDate));
		ps.setDate(2, Date.valueOf(bookingDate));
		ResultSet rs=ps.executeQuery();
		int flag=0;
		while(rs.next()) {
			BookingDetailsBean bdBean=new BookingDetailsBean();
			bdBean.setBookingId(rs.getString(1));
			bdBean.setRoomId(rs.getString(2));
			bdBean.setUserId(rs.getString(3));
			bdBean.setBookedFrom(rs.getDate(4).toLocalDate());
			bdBean.setBookedTo(rs.getDate(5).toLocalDate());
			bdBean.setNoOfAdults(rs.getInt(6));
			bdBean.setNoOfChildren(rs.getInt(7));
			bdBean.setAmount(rs.getDouble(8));
			bdList.add(bdBean);
			flag++;
		}
		if(flag==0) {
			return null;
		}
		return bdList;
	}
	
	@Override
	public RoomDetailsBean getRoomDetailsBeanById(String id)throws HotelException, SQLException{
			Connection conn=DBConnection.getInstance().getConnection();
			PreparedStatement ps= conn.prepareStatement("select * from ROOMDETAILS where availability like 'yes' and room_id = ?");
			ps.setString(1, id);
			ResultSet rs= ps.executeQuery();
			RoomDetailsBean rbean=null;
			if(rs.next()){
				rbean=new RoomDetailsBean();
				rbean.setHotelId(rs.getString(1));
				rbean.setRoomId(rs.getString(2));
				rbean.setRoomNo(rs.getString(3));
				rbean.setRoomType(rs.getString(4));
				rbean.setPerNightRate(rs.getDouble(5));
				rbean.setAvailability(rs.getString(6).equals("yes")?true:false);
			}
		return rbean;
	}

}
