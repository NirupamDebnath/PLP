package com.capgemini.hotemanagement.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.capgemini.hotelmanagement.bean.BookingDetailsBean;
import com.capgemini.hotelmanagement.bean.HotelBean;
import com.capgemini.hotelmanagement.bean.RoomDetailsBean;
import com.capgemini.hotelmanagement.bean.UsersBean;
import com.capgemini.hotelmanagement.exception.HotelException;
import com.capgemini.hotelmanagement.service.HotelService;

public class Controller {
	
	
	static UsersBean ub=null;
	static HotelService hotelservice=new HotelService();
	static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	static int ch;
	public static void main(String[] args) throws IOException, NumberFormatException, HotelException, SQLException {
	
		
	ub= new UsersBean();

		outer: while(true){
			System.out.println("*****************************************");
			System.out.println("*  Hotel Bookings Management System     *");
			System.out.println("*****************************************");
			System.out.println("Enter your choice");
			System.out.println("1.Admin\n"
					+ "2.Employee\n"
					+ "3.User\n"
					+ "4.Exit");
			System.out.println("Enter your choice:");
			ch = Integer.parseInt(bf.readLine());
			switch(ch){
				case 1:
					ub.setRole("admin");
					admin();
					break;
				case 2:
					ub.setRole("employee");
					employee();
					break;
				case 3:
					ub.setRole("customer");
					customer();
					break;
				case 4:
					break outer;
			}
		}
	}
	
	public static void admin(){
		System.out.println("Enter password: ");
		try {
			ub.setPassword(bf.readLine());
			ub.setUserName("admin");
			ub.setUserId("1001");
			if(hotelservice.logIn(ub)){
				System.out.println("\n******Logged id Successfully!!!!*******\n");
				outer: while(true){
					System.out.println("1.To View Room Availability\n"
							+ "2.Make Booking\n"
							+ "3.View Booking Status\n"
							+ "4.Add Hotels\n"
							+ "5.Delete Hotels\n"
							+ "6.View Hotels\n"
							+ "7.Add Rooms\n"
							+ "8.Delete Rooms\n"
							+ "9.View Rooms\n"
							+ "10.View List of Hotels\n"
							+ "11.View Bookings of Specific Hotel\n"
							+ "12.View Guest List of Specific Hotels\n"
							+ "13.View Bookings of Specific Date\n"
							+ "14.Logout\n"
							+ "Enter your choice");
					switch(Integer.parseInt(bf.readLine()))
					{
						case 1:
							System.out.println("Enter Hotel Id:");
							String hotelId=bf.readLine();
							ArrayList<RoomDetailsBean> rdList=hotelservice.checkRoomAvailabiliy(hotelId);
							if(rdList==null) {
								System.out.println("No room to show");
							}
							else {
								System.out.println("Hotel Id \t Room Id \t Room No \t Room Type \t Per Night Rate \t Availability");
								for (RoomDetailsBean rdb : rdList) {
									System.out.println(rdb.getHotelId()+" \t "+rdb.getRoomId()+" \t "+rdb.getRoomNo()
									+" \t "+rdb.getRoomType()+" \t "+rdb.getPerNightRate()+" \t "+rdb.isAvailability());
								}
							}
						break;
						
						case 2:
							System.out.println("Enter user Id for whom you want to book room: ");
							ub.setUserId(bf.readLine());
							makeBooking();
							break;
							
						case 3:
							System.out.println("Enter booking id: ");
							String bookingId=bf.readLine();
							BookingDetailsBean bdBean=hotelservice.viewBookingStatus(bookingId);
							if(bdBean==null) {
								System.out.println("No bookings for this booking Id");
							}else {
								System.out.println(bdBean.getBookingId()+"\t"+bdBean.getRoomId()
										+"\t"+bdBean.getUserId()+"\t"+bdBean.getBookedFrom()+"\t"+bdBean.getBookedTo()
										+"\t"+bdBean.getNoOfAdults()+"\t"+bdBean.getNoOfChildren()+"\t"+bdBean.getAmount());
							}
							break;
						case 4:
							addHotels();
							break;
							
						case 5:
							deleteHotel();
							break;
							
						case 6:
							viewHotel();
							break;
							
						case 7:
							addRooms();
							break;
						
						case 8:
							deleteRooms();
							break;
						
						case 9:
							viewRooms();
							break;
							
						case 10:
							viewHotelList();
							break;
							
						case 11:
							bookingOfSpecificHotel();
							break;
						
						case 12:
							viewGuestListOfHotel();
							break;
							
						case 13:
							viewBookingOfSpecificDate();
							break;
							
						case 14:
							break outer;
						default:
							continue outer;
					}
				}
			}
			else
			{
				System.out.println("Username and password mismatch");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HotelException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String register() throws IOException, HotelException, SQLException{
		ub=new UsersBean();
		System.out.println("Enter data for Customer");
		System.out.println("Enter password for new User: ");
		ub.setPassword(bf.readLine());
		ub.setRole("customer");
		System.out.println("Enter User Name: ");
		ub.setUserName(bf.readLine());
		System.out.println("Enter mobile no: ");
		ub.setMobileNo(bf.readLine());
		System.out.println("Enter phone no: ");
		ub.setPhone(bf.readLine());
		System.out.println("Enter address: ");
		ub.setAddress(bf.readLine());
		System.out.println("Enter Email Id: ");
		ub.setEmail(bf.readLine());
		
		return hotelservice.register(ub);
	}
	
	public static void addHotels() throws IOException, HotelException, SQLException
	{
		HotelBean hb=new HotelBean();
		System.out.println("Enter Hotel Details:");
		System.out.println("Enter City: ");
		hb.setCity(bf.readLine());
		System.out.println("Enter Hotel Name: ");
		hb.setHotelName(bf.readLine());
		System.out.println("Enter Address: ");
		hb.setAddress(bf.readLine());
		System.out.println("Enter Description: ");
		hb.setDescription(bf.readLine());
		System.out.println("Enter Average Rate Per Night: ");
		hb.setAvgRatePerNight(Double.parseDouble(bf.readLine()));
		System.out.println("Enter Phone No1: ");
		hb.setPhoneNo1(bf.readLine());
		System.out.println("Enter Phone No2: ");
		hb.setPhoneNo2(bf.readLine());
		System.out.println("Enter Rating: ");
		hb.setRating(Integer.parseInt(bf.readLine()));
		System.out.println("Enter Email: ");
		hb.setEmail(bf.readLine());
		System.out.println("Enter Fax: ");
		hb.setFax(bf.readLine());
		
		hotelservice.addHotels(hb);
	}
	
	public static void viewHotel() throws HotelException, SQLException
	{
		ArrayList<HotelBean> hList=hotelservice.viewHotels();
		System.out.println("Hotel Details:");
		if(hList==null) {
			System.out.println("No hotel details faound");
		}
		else{
			for (HotelBean hotelBean : hList) {
				System.out.println(hotelBean.getHotelId()+"\t"+hotelBean.getCity()+"\t"+hotelBean.getHotelName()
						+"\t"+hotelBean.getAddress()+"\t"+hotelBean.getDescription()+"\t"+hotelBean.getAvgRatePerNight()
						+"\t"+hotelBean.getPhoneNo1()+"\t"+hotelBean.getPhoneNo2()+"\t"+hotelBean.getRating()
						+"\t"+hotelBean.getEmail()+"\t"+hotelBean.getFax());
			}
		}
	}
	
	public static void deleteHotel() throws IOException, HotelException, SQLException
	{
		System.out.println("Enter Hotel Id: ");
		String hId=bf.readLine();
		HotelBean hb=new HotelBean();
		hb.setHotelId(hId);
		hotelservice.deleteHotels(hb);
	}
	
	public static void customer() throws NumberFormatException, IOException, HotelException, SQLException{
		
		outer: while(true){
			System.out.println("1.Register\n"
								+"2.Login\n"
								+ "3.Exit");
			System.out.println("Enter your choice: ");
			int ch=Integer.parseInt(bf.readLine());
			switch(ch)
			{
				case 1:
					String uId=register();
					System.out.println("Your user id(please keep a note of it) is : "+uId);
				break;
				case 2:
					System.out.println("Enter your id: ");
					ub.setUserId(bf.readLine());
					System.out.println("Enter password: ");
					ub.setPassword(bf.readLine());
					if(hotelservice.logIn(ub)){
						customerOptionView();
					}else{
						System.out.println("Invalid Username and password !");
					}
				break;
				case 3:
					break outer;
				default:
					continue outer;
			}
		}
	}
	
	public static void employee() throws NumberFormatException, IOException, HotelException, SQLException{
		outer: while(true){
			System.out.println("1.Login\n"
								+ "2.Exit");
			System.out.println("Enter your choice: ");
			int ch=Integer.parseInt(bf.readLine());
			switch(ch)
			{
				case 1:
					System.out.println("Enter your id: ");
					ub.setUserId(bf.readLine());
					System.out.println("Enter password: ");
					ub.setPassword(bf.readLine());
					if(hotelservice.logIn(ub)){
						customerOptionView();
					}else{
						System.out.println("Invalid Username and password !");
					}
				break;
				case 2:
					break outer;
				default:
					continue outer;
			}
		}
	}
	
	public static void customerOptionView() throws HotelException, SQLException, IOException{
		
		outer: while(true){
			System.out.println("1.check Room Availability\n"
					+ "2.Make Booking\n"
					+ "3.View Booking Status\n"
					+ "4.Log out");
			System.out.println("Enter your choice: ");
			int ch=Integer.parseInt(bf.readLine());
			switch(ch){
				case 1:
					checkRoomAvailability();
					break;
					
				case 2:
					makeBooking();
					break;
					
				case 3:
					System.out.println("Enter booking id: ");
					String bookingId=bf.readLine();
					BookingDetailsBean bdBean=hotelservice.viewBookingStatus(bookingId);
					if(bdBean==null) {
						System.out.println("No booking details available for this id");
					}
					else {
						System.out.println(bdBean.getBookingId()+"\t"+bdBean.getRoomId()
								+"\t"+bdBean.getUserId()+"\t"+bdBean.getBookedFrom()+"\t"+bdBean.getBookedTo()
								+"\t"+bdBean.getNoOfAdults()+"\t"+bdBean.getNoOfChildren()+"\t"+bdBean.getAmount());
					}
					break;
				case 4:
					break outer;
					
				default: 
					continue outer;
				}
		}
	}
	
	public static void checkRoomAvailability() throws HotelException, SQLException, IOException
	{
		viewHotel();
		System.out.println("Enter Hotel Id: ");
		String hId=bf.readLine();
		ArrayList<RoomDetailsBean> rdList=hotelservice.checkRoomAvailabiliy(hId);
		if(rdList==null) {
			System.out.println("No room to show");
		}
		else {
			System.out.println("Hotel Id \t Room Id \t Room No \t Room Type \t Per Night Rate \t Availability");
			for (RoomDetailsBean rdb : rdList) {
				System.out.println(rdb.getHotelId()+" \t "+rdb.getRoomId()+" \t "+rdb.getRoomNo()
						+" \t "+rdb.getRoomType()+" \t "+rdb.getPerNightRate()+" \t "+rdb.isAvailability());
			}
		}
	}
	
	public static void makeBooking() throws HotelException, IOException, SQLException
	{
		System.out.println("Enter room id :");
		RoomDetailsBean rdb=hotelservice.getRoomDetailsBeanById(bf.readLine());
		if(rdb!=null){
			BookingDetailsBean bdBean=new BookingDetailsBean();
			
			bdBean.setRoomId(rdb.getRoomId());
			bdBean.setUserId(ub.getUserId());
			LocalDate checkInDate=null;
			LocalDate checkOutDate=null;
			outer: while(true) {
				
				try {
					
					System.out.println("Enter check in date(yyyy-MM-dd)");
					String checkIn=bf.readLine();
					checkInDate=LocalDate.parse(checkIn);
					bdBean.setBookedFrom(checkInDate);
					
					System.out.println("Enter check out date(yyyy-MM-dd)");
					String checkOut=bf.readLine();
					checkOutDate=LocalDate.parse(checkOut);
					bdBean.setBookedTo(checkOutDate);
					
				}catch(DateTimeParseException e) {
					System.out.println("Wrong date format entered");
					continue outer;
				}
				
				break outer;
			}
			System.out.println("Enter no of adults: ");
			bdBean.setNoOfAdults(Integer.parseInt(bf.readLine()));
			System.out.println("Enter no of children(Bellow 14yrs: )");
			bdBean.setNoOfChildren(Integer.parseInt(bf.readLine()));
			Period period=Period.between(checkInDate, checkOutDate);
			bdBean.setAmount(rdb.getPerNightRate()*period.getDays());
			
			int booking_id=hotelservice.makeBooking(bdBean);
			System.out.println("Your booking id is : "+booking_id);
		}
		else{
			System.out.println("Room is not available");
		}
	}

	public static void addRooms() throws NumberFormatException, IOException, HotelException, SQLException
	{
		RoomDetailsBean rdBean= new RoomDetailsBean();
		System.out.println("Enter hotel id for which you want to add room: ");
		rdBean.setHotelId(bf.readLine());
		System.out.println("Enter room no: ");
		rdBean.setRoomNo(bf.readLine());
		System.out.println("Enter room type ('Standard non A/C room' / 'Standard A/C room' / 'Executive A/C room' / 'Deluxe A/C room')");
		rdBean.setRoomType(bf.readLine());
		System.out.println("Enter rate per night");
		rdBean.setPerNightRate(Double.parseDouble(bf.readLine()));
		rdBean.setAvailability(true);
		
		String roomId= hotelservice.addRooms(rdBean);
		System.out.println("Room Id is"+roomId);
	}

	public static void deleteRooms() throws IOException, HotelException, SQLException {
		System.out.println("Enter room Id:");
		String rId=bf.readLine();
		RoomDetailsBean roomDetailsBean=new RoomDetailsBean();
		roomDetailsBean.setRoomId(rId);
		hotelservice.deleteRooms(roomDetailsBean);
	}

	public static void viewRooms() throws HotelException, SQLException, IOException {
		RoomDetailsBean roomDetailsBean=new RoomDetailsBean();
		viewHotel();
		System.out.println("Enter hotel id for which you want to search rooms");
		roomDetailsBean.setHotelId(bf.readLine());
		ArrayList<RoomDetailsBean> hList=hotelservice.viewRooms(roomDetailsBean);
		System.out.println("Room Details:");
		if(hList==null) {
			System.out.println("No rooms available");
		}
		else {
			for (RoomDetailsBean rdBean : hList) {
				System.out.println(rdBean.getHotelId()+"\t"+rdBean.getRoomId()+"\t"+
						rdBean.getRoomNo()+"\t"+rdBean.getRoomType()+"\t"+
						rdBean.getPerNightRate()+"\t"+rdBean.isAvailability());
			}
		}
	}
	
	public static void bookingOfSpecificHotel() throws IOException, HotelException, SQLException {
		System.out.println("Enter hotel id");
		String hotelId=bf.readLine();
		ArrayList<BookingDetailsBean> bdList=hotelservice.viewBookingListOfSpecificHotels(hotelId);
		if(bdList==null) {
			System.out.println("No booking available");
		}else {
			for (BookingDetailsBean bdBean : bdList) {
				System.out.println(bdBean.getBookingId()+"\t"+bdBean.getRoomId()
				+"\t"+bdBean.getUserId()+"\t"+bdBean.getBookedFrom()+"\t"+bdBean.getBookedTo()
				+"\t"+bdBean.getNoOfAdults()+"\t"+bdBean.getNoOfChildren()+"\t"+bdBean.getAmount());
			}
		}
	}

	public static void viewHotelList() throws HotelException, SQLException {
		ArrayList<HotelBean> hList=hotelservice.viewHotels();
		System.out.println("Hotel Details:");
		if(hList==null) {
			
		}
		else {
			for (HotelBean hotelBean : hList) {
				System.out.println(hotelBean.getHotelId()+"\t"+hotelBean.getCity()+"\t"+hotelBean.getHotelName());
			}
		}
	}
	
	public static void viewGuestListOfHotel() throws IOException, HotelException, SQLException {
		System.out.println("Enter hotel id");
		String hotelId=bf.readLine();
		ArrayList<BookingDetailsBean> bdList=hotelservice.viewGuestListOfSpecificHotels(hotelId);
		if(bdList==null) {
			System.out.println("No guest to show");
		}
		else {
			for (BookingDetailsBean bdBean : bdList) {
				System.out.println(bdBean.getRoomId()+"\t"+
							bdBean.getUserId()+"\t"+
							bdBean.getUserName());
			}
		}
	}

	public static void viewBookingOfSpecificDate() throws IOException, HotelException, SQLException {
		LocalDate searchDateLD=null;
		outer: while(true) {
			try {
				System.out.println("Enter date that you want to check:(yyyy-MM-dd)");
				String searchDate=bf.readLine();
				searchDateLD=LocalDate.parse(searchDate);
			}catch(DateTimeParseException e) {
				System.out.println("Wrong date format inserted try again");
				continue outer;
			}
			break outer;
		}
		ArrayList<BookingDetailsBean> bdList=hotelservice.viewBookingsForSpecificDate(searchDateLD);
		if(bdList==null) {
			System.out.println("No booking available");
		}
		else {
			for (BookingDetailsBean bdBean : bdList) {
				System.out.println(bdBean.getBookingId()+"\t"+bdBean.getRoomId()
				+"\t"+bdBean.getUserId()+"\t"+bdBean.getBookedFrom()+"\t"+bdBean.getBookedTo()
				+"\t"+bdBean.getNoOfAdults()+"\t"+bdBean.getNoOfChildren()+"\t"+bdBean.getAmount());
			}
		}
	}
}
	
	