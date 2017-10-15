package com.capgemini.hotelmanagement.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.hotelmanagement.bean.BookingDetailsBean;
import com.capgemini.hotelmanagement.bean.HotelBean;
import com.capgemini.hotelmanagement.bean.RoomDetailsBean;
import com.capgemini.hotelmanagement.bean.UsersBean;
import com.capgemini.hotelmanagement.dao.HotelDAO;
import com.capgemini.hotelmanagement.exception.HotelException;

public class HotelDAOTest {

	static UsersBean usersBean;
	static HotelBean hotelBean;
	static BookingDetailsBean bookingDetailsBean;
	static RoomDetailsBean roomDetailsBean;
	static HotelDAO hotelDao;
	
	
	@BeforeClass
	public static void initialize() {
		hotelDao= new HotelDAO();
		hotelBean=new HotelBean();
		usersBean =new UsersBean();
		bookingDetailsBean=new BookingDetailsBean();
		 roomDetailsBean=new RoomDetailsBean(); 
	}
	
	@Test
	public void testRegister() throws HotelException, SQLException {
		
		usersBean.setPassword("12345");
		usersBean.setRole("customer");
		usersBean.setUserName("celes");
		usersBean.setMobileNo("9878760345");
		usersBean.setPhone("044255456");
		usersBean.setAddress("chennai");
		usersBean.setEmail("abc@gmail.com");
		String userId=hotelDao.register(usersBean);
		assertTrue(Integer.parseInt(userId)>=1000);
	}
	@Test
	public void testLogInUsersBean() throws HotelException, SQLException {
		usersBean.setUserId("1001");
		usersBean.setPassword("12345");
		boolean status=hotelDao.logIn(usersBean);
		assertTrue(status);
		usersBean.setUserId("1001");
		usersBean.setPassword("1234567wert");
		status=hotelDao.logIn(usersBean);
		assertFalse(status);
	}

	
	@Test
	public void testMakeBooking() throws HotelException, SQLException {
		bookingDetailsBean.setRoomId("6000");
		bookingDetailsBean.setUserId("1001");
		bookingDetailsBean.setBookedFrom(LocalDate.parse("2017-10-25"));
		bookingDetailsBean.setBookedTo(LocalDate.parse("2017-10-28"));
		bookingDetailsBean.setNoOfAdults(2);
		bookingDetailsBean.setNoOfChildren(2);
		bookingDetailsBean.setAmount(2500);
		int bookingId=hotelDao.makeBooking(bookingDetailsBean);
		assertTrue(bookingId>=2000);
	}
	

	@Test
	public void testAddHotels() throws HotelException, SQLException {
		hotelBean.setCity("chennai");
		hotelBean.setHotelName("taj");
		 hotelBean.setAddress("chennai");
		 hotelBean.setDescription("good");
		 hotelBean.setAvgRatePerNight(2300);
		 hotelBean.setPhoneNo1("044255670");
		hotelBean.setPhoneNo2("044255470");
		 hotelBean.setRating(4);
		 hotelBean.setEmail("abcd@gmail.com");
	hotelBean.setFax("abc");
	int state=hotelDao.addHotels(hotelBean);
	assertEquals(1, state);
	}
}

