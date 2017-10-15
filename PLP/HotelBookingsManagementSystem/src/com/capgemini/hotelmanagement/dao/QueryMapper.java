package com.capgemini.hotelmanagement.dao;

public interface QueryMapper {
	public static final String INSERT_INTO_USERS="insert into Users values(Users_seq.nextVal,?,?,?,?,?,?,?)";
	public static final String USERS_SEQ_CUR_VAL="select Users_seq.currval from dual";
	public static final String GET_PASSWORD_FROM_USERS="select password from users where user_id=?";
	public static final String GET_DETAILS_FROM_ROOMDETAILS="select * from ROOMDETAILS where hotel_Id = ?";
	public static final String UPDATE_ROOM_AVAILABILITY="update ROOMDETAILS set availability='no' where room_id=?";
	public static final String INSERT_INTO_BOOKINGDETAILS="insert into BOOKINGDETAILS values(BOOKINGDETAILS_seq.nextval,?,?,?,?,?,?,?)";
	public static final String BOOKINGDETAILS_SEQ_CURRVAL="select BOOKINGDETAILS_seq.currval from dual";
	public static final String GET_DETAILS_FROM_BOOKING_DETAILS="select * from BOOKINGDETAILS where booking_id=?";
	public static final String INSERT_INTO_HOTEL="insert into Hotel values(Hotel_seq.nextVal,?,?,?,?,?,?,?,?,?,?)";
	public static final String DELETE_FROM_HOTEL="delete from Hotel where hotel_id=?";
	public static final String DELETE_FROM_ROOMDETAILS_BY_HOTELID="delete from RoomDetails where hotel_id=?";
	public static final String GET_ALL_DATA_FROM_HOTEL="select * from Hotel";
	public static final String INSERT_INTO_ROOMDETAILS="insert into ROOMDETAILS values (?,RoomDetails_seq.nextval,?,?,?,'yes')";
	public static final String ROOMDETAILS_SEQ_CURRVAL="select RoomDetails_seq.currval from dual";
	public static final String DELETE_FROM_BOOKINGDETAILS="delete from BOOKINGDETAILS where room_id = ?";
	public static final String DELETE_FROM_ROOMDETAILS_BY_ROOMID="delete from ROOMDETAILS where room_id=?";
	public static final String VIEW_BOOKING_LIST_OF_SPECIFIC_HOTEL="select booking_id,room_id,user_id,booked_from,booked_to,no_of_adults,no_of_children,amount from hotel natural join roomdetails natural join BookingDetails where hotel_id=?";
	public static final String VIEW_GUEST_LIST_OF_SPECIFIC_HOTEL="select room_id,user_id,user_name from BOOKINGDETAILS natural join users natural join ROOMDETAILS where hotel_id=?";
	public static final String VIEW_BOOKING_BY_DATE="select * from BOOKINGDETAILS where booked_from <= ? and booked_to >= ?";
	public static final String VIEW_USER_AVAILABILITY="Select user_id from users where user_id=?";
	public static final String GET_ROOM_DETAILS="select room_id from roomdetails where hotel_id=?";
}
