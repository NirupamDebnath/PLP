package com.capgemini.hotemanagement.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.capgemini.hotelmanagement.bean.BookingDetailsBean;
import com.capgemini.hotelmanagement.bean.HotelBean;
import com.capgemini.hotelmanagement.bean.RoomDetailsBean;
import com.capgemini.hotelmanagement.bean.UsersBean;
import com.capgemini.hotelmanagement.exception.HotelException;
import com.capgemini.hotelmanagement.service.HotelService;

/**
 * Author : Project Group 4 
 * Class Name : Controller
 * Package : com.capgemini.hotemanagement.ui 
 * Date : October 4, 2017
 */
public class Controller {
	static Logger logger=Logger.getRootLogger();
	static UsersBean userBean = null;
	static HotelService hotelservice = new HotelService();
	static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	static int choice;

	public static void main(String[] args) {
		PropertyConfigurator.configure("resources//log4j.properties");
		userBean = new UsersBean();

		outer: while (true) {
			System.out.println("*****************************************");
			System.out.println("*  Hotel Bookings Management System     *");
			System.out.println("*****************************************");
			System.out.println("Enter your choice");
			System.out.println("1.Admin\n"+"2.Employee\n"+"3.Customer\n"+"4.Exit");
			System.out.println("Enter your choice:");
			try {
				choice = Integer.parseInt(bufferedReader.readLine());
			} catch (NumberFormatException | IOException e) {
				System.err.println(e.getMessage());
				logger.error(e.getMessage());
			}
			if (choice < 1 || choice > 4)
				System.err.println("Enter valid choice !!");
			switch (choice) {
			case 1:
				userBean.setRole("admin");
				admin();
				break;
			case 2:
				userBean.setRole("employee");
				try {
					employee();
				} catch (NumberFormatException | HotelException | SQLException e) {
					System.err.println(e.getMessage());
					logger.error(e.getMessage());
				}
				break;
			case 3:
				userBean.setRole("customer");
				try {
					customer();
				} catch (NumberFormatException | HotelException | SQLException e) {
					System.err.println(e.getMessage());
					logger.error(e.getMessage());
				}
				break;
			case 4:
				break outer;
			}
		}
	}

	public static void admin() {
		PropertyConfigurator.configure("resources//log4j.properties");
		System.out.println("Enter password: ");
		try {
			String password=bufferedReader.readLine();
			userBean.setPassword(password);
			userBean.setUserName("admin");
			userBean.setUserId("1001");
			if (hotelservice.logIn(userBean)) {
				System.out.println("\n******Logged in Successfully!!!!*******\n");
				outer: while (true) {
					System.out.println("1.To View Room Availability\n"
							+ "2.Make Booking\n" + "3.View Booking Status\n"
							+ "4.Add Hotels\n" + "5.Delete Hotels\n"
							+ "6.View Hotels\n" + "7.Add Rooms\n"
							+ "8.Delete Rooms\n" + "9.View Rooms\n"
							+ "10.View List of Hotels\n"
							+ "11.View Bookings of Specific Hotel\n"
							+ "12.View Guest List of Specific Hotels\n"
							+ "13.View Bookings of Specific Date\n"
							+ "14.Logout");
					System.out.println("Enter your choice:");
					int choice=Integer.parseInt(bufferedReader.readLine());
					switch (choice) {
					case 1:
						String hotelId = null;
						validateHotelId: while (true) {
							System.out.println("Enter Hotel Id:");
							hotelId = bufferedReader.readLine();
							if (!hotelservice.hotelIdValidator(hotelId)) {
								System.err.println("Enter a valid hotel id !!");
								continue validateHotelId;
							}
							break validateHotelId;
						}
						ArrayList<RoomDetailsBean> roomDetailsList = hotelservice.checkRoomAvailabiliy(hotelId);
						if (roomDetailsList == null) {
							System.err.println("No room to show !!");
						} else {
							System.out
									.println("Hotel Id \t Room Id \t Room No \t Room Type \t\t\t Per Night Rate \t Availability");
							for (RoomDetailsBean roomDetailsBean : roomDetailsList) {
								System.out.println(roomDetailsBean.getHotelId()
										+ " \t\t "
										+ roomDetailsBean.getRoomId()
										+ " \t\t "
										+ roomDetailsBean.getRoomNo()
										+ " \t\t "
										+ roomDetailsBean.getRoomType()
										+ " \t\t "
										+ roomDetailsBean.getPerNightRate()
										+ " \t\t "
										+ roomDetailsBean.isAvailability());
							}
							System.out.println();
						}
						break;

					case 2:
						System.out.println("Enter user Id for whom you want to book room: ");
						String userId=bufferedReader.readLine();
						userBean.setUserId(userId);
						makeBooking();
						break;
					case 3:
						String bookingId = null;
						bookingIdValidator: while (true) {
							System.out.println("Enter booking id: ");
							bookingId = bufferedReader.readLine();
							if (!hotelservice.bookingIdValidator(bookingId)) {
								System.err.println("Enter valid booking Id which should be of four digit only !!");
								continue bookingIdValidator;
							}
							break bookingIdValidator;
						}
						BookingDetailsBean bookingDetailsBean = hotelservice.viewBookingStatus(bookingId);
						if (bookingDetailsBean == null) {
							System.err.println("No bookings available for this booking Id !!");
						} else {
							System.out.println("Booking Id \t Room Id \t User Id \t CheckIn Date \t Check Out Date \t No Of Adults \t No Of Children \t Amount");
							System.out.println(bookingDetailsBean.getBookingId()
									+ " \t\t "
									+ bookingDetailsBean.getRoomId()
									+ " \t\t "
									+ bookingDetailsBean.getUserId()
									+ " \t\t "
									+ bookingDetailsBean.getBookedFrom()
									+ " \t "
									+ bookingDetailsBean.getBookedTo()
									+ " \t\t "
									+ bookingDetailsBean.getNoOfAdults()
									+ " \t\t "
									+ bookingDetailsBean.getNoOfChildren()
									+ " \t\t\t "
									+ bookingDetailsBean.getAmount());
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
						System.err.println("Enter valid choice !!");
						continue outer;
					}
				}
			} else {
				System.err.println("Invalid Username and password !!");
			}
		} catch (IOException | SQLException | HotelException e) {
			System.err.println(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	public static String register() throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		userBean = new UsersBean();
		boolean check;
		String password=null;
		String userName=null;
		String mobileNo=null;
		String phoneNo=null;
		String address=null;
		String email=null;
		try {
			System.out.println("*****************************************");
			System.out.println("*       Enter data for Customer:        *");
			System.out.println("*****************************************");
			check = false;
			while (check == false) {
				System.out.println("Enter password for new User: ");
				password=bufferedReader.readLine();
				if(hotelservice.userPasswordValidator(password))
				check=true;
				else
				System.err.println("Enter Valid Password !!");
			}
			userBean.setPassword(password);
			userBean.setRole("customer");
		
			check = false;
			while (check == false) {
				System.out.println("Enter User Name: ");
				userName=bufferedReader.readLine();
				if(hotelservice.stringValidator(userName))
				check=true;
				else
				System.err.println("Enter Valid User Name !!");
			}
			userBean.setUserName(userName);
		
			check = false;
			while (check == false) {
				System.out.println("Enter mobile no: ");
				mobileNo=bufferedReader.readLine();
				if(hotelservice.phoneNumberValidator(mobileNo))
				check=true;
				else
				System.err.println("Enter Valid Mobile Number !!");
			}
			userBean.setMobileNo(mobileNo);
	
			check = false;
			while (check == false) {
				System.out.println("Enter phone no: ");
				phoneNo=bufferedReader.readLine();
				if(hotelservice.phoneNumberValidator(phoneNo))
				check=true;
				else
				System.err.println("Enter Valid Phone Number !!");
			}
			userBean.setPhone(phoneNo);
			
			check = false;
			while (check == false) {
				System.out.println("Enter address: ");
				address=bufferedReader.readLine();
				if(hotelservice.stringValidator(address))
				check=true;
				else
				System.err.println("Enter Valid Address !!");
			}
			userBean.setAddress(address);
		
			check = false;
			while (check == false) {
				System.out.println("Enter Email Id: ");
				email=bufferedReader.readLine();
				if(hotelservice.emailValidator(email))
				check=true;
				else
				System.err.println("Enter Valid Email Id !!");
			}
			userBean.setEmail(email);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			logger.error(e.getMessage());
		}

		return hotelservice.register(userBean);
	}

	public static void addHotels() throws HotelException, SQLException {
		HotelBean hotelBean = new HotelBean();
		boolean check;
		try {
			String city=null; 
			String hotelName=null;
			String address=null;
			String description=null;
			String phoneNo1=null;
			String phoneNo2=null;
			String email=null;
			String fax=null;
			int rating=0;
			
			System.out.println("*****************************************");
			System.out.println("*        Enter Hotel Details:           *");
			System.out.println("*****************************************");
			
			check = false;
			while (check == false) {
				System.out.println("Enter City: ");
				city = bufferedReader.readLine();
				if(hotelservice.stringValidator(city))
				check=true;
				else
				System.err.println("Enter Valid City Name !!");
			}
			hotelBean.setCity(city);
			
			check = false;
			while (check == false) {
				System.out.println("Enter Hotel Name: ");
				hotelName=bufferedReader.readLine();
				if(hotelservice.stringValidator(hotelName))
				check=true;
				else
				System.err.println("Enter Valid Hotel Name !!");
			}
			hotelBean.setHotelName(hotelName);
			
			check = false;
			while (check == false) {
				System.out.println("Enter Address: ");
				address=bufferedReader.readLine();
				if(hotelservice.stringValidator(address))
				check=true;
				else
				System.err.println("Enter Valid Address !!");
			}
			hotelBean.setAddress(address);
			
			check = false;
			while (check == false) {
				System.out.println("Enter Description: ");
				description=bufferedReader.readLine();
				if(hotelservice.stringValidator(description))
				check=true;
				else
				System.err.println("Enter Valid Description !!");
			}
			hotelBean.setDescription(description);
		
			System.out.println("Enter Average Rate Per Night: ");
			hotelBean.setAvgRatePerNight(Double.parseDouble(bufferedReader.readLine()));
			
			check = false;
			while (check == false) {
				System.out.println("Enter Phone No1: ");
				phoneNo1=bufferedReader.readLine();
				if(hotelservice.phoneNumberValidator(phoneNo1))
				check=true;
				else
				System.err.println("Enter Valid Phone Number !!");
			}
			hotelBean.setPhoneNo1(phoneNo1);
			
			check = false;
			while (check == false) {
				System.out.println("Enter Phone No2: ");
				phoneNo2=bufferedReader.readLine();
				if(hotelservice.phoneNumberValidator(phoneNo2))
				check=true;
				else
				System.err.println("Enter Valid Phone Number !!");
			}
			hotelBean.setPhoneNo1(phoneNo2);
		
			check = false;
			while (check == false) {
				System.out.println("Enter Rating (in between 1 & 5): ");
				rating=(Integer.parseInt(bufferedReader.readLine()));
				if(rating>=1&&rating<=5)
				check=true;
				else
				System.err.println("Enter valid rating !!");
			}
			hotelBean.setRating(rating);
			
			check = false;
			while (check == false) {
				System.out.println("Enter Email: ");
				email=bufferedReader.readLine();
				if(hotelservice.emailValidator(email))
				check=true;
				else
				System.err.println("Enter Valid Email Id !!");
			}
			hotelBean.setEmail(email);
			
			check = false;
			while (check == false) {
				System.out.println("Enter Fax: ");
				fax=bufferedReader.readLine();
				if(hotelservice.faxValidator(fax))
				check=true;
				else
				System.err.println("Enter Valid Fax Number !!");
			}
			hotelBean.setFax(fax);
		} 
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
		hotelservice.addHotels(hotelBean);
	}

	public static void viewHotel() throws HotelException, SQLException {
		ArrayList<HotelBean> hotelList = hotelservice.viewHotels();
		System.out.println("*****************************************");
		System.out.println("*           Hotel Details:              *");
		System.out.println("*****************************************");
		if (hotelList == null) {
			System.err.println("No such hotel details found !!");
		} else {
			System.out.println("Hotel Id \t City \t\t Hotel Name \t Address \t Description \t Average Rate Per Night \t PhoneNo1 \t PhoneNo2 \t Rating \t Email \t\t\t Fax");
			for (HotelBean hotelBean : hotelList) {
				System.out.println(hotelBean.getHotelId() + " \t\t "
						+ hotelBean.getCity() + " \t "
						+ hotelBean.getHotelName() + " \t\t "
						+ hotelBean.getAddress() + " \t "
						+ hotelBean.getDescription() + " \t "
						+ hotelBean.getAvgRatePerNight() + " \t\t\t "
						+ hotelBean.getPhoneNo1() + " \t "
						+ hotelBean.getPhoneNo2() + " \t "
						+ hotelBean.getRating() + " \t\t "
						+ hotelBean.getEmail() + " \t " + hotelBean.getFax());
			}
		}
	}

	public static void deleteHotel() throws HotelException, SQLException {
		System.out.println("\nEnter Hotel Id: ");
		String hotelId;
		try {
			hotelId = bufferedReader.readLine();
			HotelBean hotelBean = new HotelBean();
			hotelBean.setHotelId(hotelId);
			hotelservice.deleteHotels(hotelBean);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void customer() throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");

		outer: while (true) {
			System.out.println("1.Register\n" + "2.Login\n" + "3.Exit");
			System.out.println("Enter your choice: ");
			int choice;
			boolean check;
			String userId=null;
			String password=null;
			try {
				choice = Integer.parseInt(bufferedReader.readLine());
				switch (choice) {
				case 1:
					userId = register();
					System.out.println("Your user id is : " + userId);
					break;
				case 2:
					check = false;
					while (check == false) {
						System.out.println("Enter your id: ");
						userId=bufferedReader.readLine();
						if(hotelservice.userIdValidator(userId))
						check=true;
						else
						System.err.println("Enter Valid User Id !!");
					}
					userBean.setUserId(userId);
					
					check = false;
					while (check == false) {
						System.out.println("Enter password: ");
						password=bufferedReader.readLine();
						if(hotelservice.userPasswordValidator(password))
						check=true;
						else
						System.err.println("Enter Valid Password !!");
					}
					userBean.setPassword(password);
					
					if (hotelservice.logIn(userBean)) {
						customerOptionView();
					} else {
						System.err.println("Invalid Username and password !!");
					}
					break;
				case 3:
					break outer;
				default:
					System.err.println("Enter valid choice !!");
					continue outer;
				}
			} catch (NumberFormatException | IOException e) {
				System.err.println(e.getMessage());
				logger.error(e.getMessage());
			}

		}
	}

	public static void employee() throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		outer: while (true) {
			System.out.println("1.Login\n" + "2.Exit");
			System.out.println("Enter your choice: ");
			int choice;
			try {
				choice = Integer.parseInt(bufferedReader.readLine());
				switch (choice) {
				case 1:
					System.out.println("Enter your id: ");
					String userId=bufferedReader.readLine();
					userBean.setUserId(userId);
					System.out.println("Enter password: ");
					String password=bufferedReader.readLine();
					userBean.setPassword(password);
					if (hotelservice.logIn(userBean)) {
						customerOptionView();
					} else {
						System.err.println("Invalid Username and password !!");
					}
					break;
				case 2:
					break outer;
				default:
					System.err.println("Enter valid choice !!");
					continue outer;
				}
			} catch (NumberFormatException | IOException e) {
				System.err.println(e.getMessage());
				logger.error(e.getMessage());
			}

		}
	}

	public static void customerOptionView() throws HotelException, SQLException {
		PropertyConfigurator.configure("resources//log4j.properties");
		
		outer: while (true) {
			System.out.println("1.Check Room Availability\n"
					+ "2.Make Booking\n" + "3.View Booking Status\n"
					+ "4.Log out");
			System.out.println("Enter your choice: ");
			int choice;
			try {
				choice = Integer.parseInt(bufferedReader.readLine());
				switch (choice) {
				case 1:
					checkRoomAvailability();
					break;

				case 2:
					makeBooking();
					break;

				case 3:
					System.out.println("Enter booking id: ");
					String bookingId = bufferedReader.readLine();
					BookingDetailsBean bookingDetailsBean = hotelservice.viewBookingStatus(bookingId);
					if (bookingDetailsBean == null) {
						System.err.println("No booking details available for this id !!");
					} else {
						System.out.println("Booking Id \t Room Id \t User Id \t CheckIn Date \t Check Out Date \t No Of Adults \t No Of Children \t Amount");
						System.out.println(bookingDetailsBean.getBookingId()
								+ " \t\t " + bookingDetailsBean.getRoomId()
								+ " \t\t " + bookingDetailsBean.getUserId()
								+ " \t\t " + bookingDetailsBean.getBookedFrom()
								+ " \t " + bookingDetailsBean.getBookedTo()
								+ " \t\t " + bookingDetailsBean.getNoOfAdults()
								+ " \t\t "+ bookingDetailsBean.getNoOfChildren()
								+ " \t\t\t " + bookingDetailsBean.getAmount());
					}
					break;
				case 4:
					break outer;

				default:
					System.err.println("Enter valid choice !!");
					continue outer;
				}
			} catch (NumberFormatException | IOException e) {
				System.err.println(e.getMessage());
				logger.error(e.getMessage());
			}

		}
	}

	public static void checkRoomAvailability() throws HotelException,
			SQLException {
		viewHotel();
		System.out.println("Enter Hotel Id: ");
		String hotelId;
		try {
			hotelId = bufferedReader.readLine();
			ArrayList<RoomDetailsBean> roomDetailsList = hotelservice.checkRoomAvailabiliy(hotelId);
			if (roomDetailsList == null) {
				System.err.println("No rooms available to show !!");
			} else {
				System.out.println("Hotel Id \t Room Id \t Room No \t Room Type \t Per Night Rate \t Availability");
				for (RoomDetailsBean roomDetailsBean : roomDetailsList) {
					System.out.println(roomDetailsBean.getHotelId() +" \t\t "
							+ roomDetailsBean.getRoomId() + " \t\t "
							+ roomDetailsBean.getRoomNo() + " \t\t "
							+ roomDetailsBean.getRoomType() + " \t\t "
							+ roomDetailsBean.getPerNightRate() + " \t\t "
							+ roomDetailsBean.isAvailability());
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void makeBooking() throws HotelException, SQLException {
		System.out.println("Enter room id :");
		RoomDetailsBean roomDetailsBean;
		try {
			String roomId=bufferedReader.readLine();
			roomDetailsBean = hotelservice.getRoomDetailsBeanById(roomId);
			if (roomDetailsBean != null) {
				BookingDetailsBean bookingDetailsBean = new BookingDetailsBean();

				bookingDetailsBean.setRoomId(roomDetailsBean.getRoomId());
				bookingDetailsBean.setUserId(userBean.getUserId());
				LocalDate checkInDate = null;
				LocalDate checkOutDate = null;
				outer: while (true) {
					try {
						System.out.println("Enter check in date(yyyy-MM-dd)");
						String checkIn = bufferedReader.readLine();
						checkInDate = LocalDate.parse(checkIn);
						bookingDetailsBean.setBookedFrom(checkInDate);

						System.out.println("Enter check out date(yyyy-MM-dd)");
						String checkOut = bufferedReader.readLine();
						checkOutDate = LocalDate.parse(checkOut);
						bookingDetailsBean.setBookedTo(checkOutDate);

					} catch (DateTimeParseException e) {
						System.out.println("Wrong date format entered");
						continue outer;
					}
					break outer;
				}
				System.out.println("Enter no of adults: ");
				int noOfAdults=Integer.parseInt(bufferedReader.readLine());
				bookingDetailsBean.setNoOfAdults(noOfAdults);
				System.out.println("Enter no of Children(Below 14yrs: )");
				int noOfChildren=Integer.parseInt(bufferedReader.readLine());
				bookingDetailsBean.setNoOfChildren(noOfChildren);
				Period period = Period.between(checkInDate, checkOutDate);
				bookingDetailsBean.setAmount(roomDetailsBean.getPerNightRate()*period.getDays());

				int bookingId = hotelservice.makeBooking(bookingDetailsBean);
				System.out.println("Your booking id is : " + bookingId);
			} else {
				System.err.println("Room is not available !!");
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void addRooms() throws HotelException, SQLException {
		RoomDetailsBean roomDetailsBean = new RoomDetailsBean();
		System.out.println("Enter hotel id for which you want to add room: ");
		try {
			String hotelId=bufferedReader.readLine();
			roomDetailsBean.setHotelId(hotelId);
			System.out.println("Enter room no: ");
			String roomNo=bufferedReader.readLine();
			roomDetailsBean.setRoomNo(roomNo);
			System.out.println("Enter room type ('Standard non A/C room' / 'Standard A/C room' / 'Executive A/C room' / 'Deluxe A/C room')");
			String roomType=bufferedReader.readLine();
			roomDetailsBean.setRoomType(roomType);
			System.out.println("Enter rate per night");
			double ratePerNight=Double.parseDouble(bufferedReader.readLine());
			roomDetailsBean.setPerNightRate(ratePerNight);
			roomDetailsBean.setAvailability(true);

			String roomId = hotelservice.addRooms(roomDetailsBean);
			System.out.println("Room Id is " + roomId);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void deleteRooms() throws HotelException, SQLException {
		System.out.println("Enter room Id:");
		String roomId;
		try {
			roomId = bufferedReader.readLine();
			RoomDetailsBean roomDetailsBean = new RoomDetailsBean();
			roomDetailsBean.setRoomId(roomId);
			hotelservice.deleteRooms(roomDetailsBean);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	public static void viewRooms() throws HotelException, SQLException {
		RoomDetailsBean roomDetailsBean = new RoomDetailsBean();
		viewHotel();
		System.out.println("Enter hotel id for which you want to search rooms: ");
		try {
			String hotelId=bufferedReader.readLine();
			roomDetailsBean.setHotelId(hotelId);
			ArrayList<RoomDetailsBean> hotelList = hotelservice.viewRooms(roomDetailsBean);
			System.out.println("*****************************************");
			System.out.println("*           Room Details:               *");
			System.out.println("*****************************************");
			if (hotelList == null) {
				System.err.println("No rooms available !!");
			} else {
				System.out.println("Hotel Id \t Room Id \t Room No \t Room Type \t Per Night Rate \t Availability");
				for (RoomDetailsBean roomDetailsBeanTemp : hotelList) {
					System.out.println(roomDetailsBeanTemp.getHotelId() + " \t\t "
							+ roomDetailsBeanTemp.getRoomId() + " \t\t "
							+ roomDetailsBeanTemp.getRoomNo() + " \t\t "
							+ roomDetailsBeanTemp.getRoomType() + " \t\t "
							+ roomDetailsBeanTemp.getPerNightRate() + " \t\t "
							+ roomDetailsBeanTemp.isAvailability());
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void bookingOfSpecificHotel() throws HotelException,
			SQLException {
		System.out.println("Enter hotel id");
		String hotelId;
		try {
			hotelId = bufferedReader.readLine();
			ArrayList<BookingDetailsBean> bookingDetailsList = hotelservice.viewBookingListOfSpecificHotels(hotelId);
			if (bookingDetailsList == null) {
				System.err.println("No such booking available !!");
			} else {
				System.out.println("Booking Id \t Room Id \t User Id \t CheckIn Date \t Check Out Date \t No Of Adults \t No Of Children \t Amount");
				for (BookingDetailsBean bookingDetailsBean : bookingDetailsList) {
					System.out.println(bookingDetailsBean.getBookingId() + " \t\t "
							+ bookingDetailsBean.getRoomId() + " \t\t "
							+ bookingDetailsBean.getUserId() + " \t\t "
							+ bookingDetailsBean.getBookedFrom() + " \t "
							+ bookingDetailsBean.getBookedTo() + " \t\t "
							+ bookingDetailsBean.getNoOfAdults() + " \t\t "
							+ bookingDetailsBean.getNoOfChildren() + " \t\t\t "
							+ bookingDetailsBean.getAmount());
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void viewHotelList() throws HotelException, SQLException {
		ArrayList<HotelBean> hotelList = hotelservice.viewHotels();
		System.out.println("*****************************************");
		System.out.println("*           Hotel Details:              *");
		System.out.println("*****************************************");
		if (hotelList == null) {
			System.err.println("No hotels to show !!");
		} else {
			System.out.println("Hotel Id \t City \t Hotel Name");
			for (HotelBean hotelBean : hotelList) {
				System.out.println(hotelBean.getHotelId() + " \t "
								+ hotelBean.getCity() + " \t "
								+ hotelBean.getHotelName());
			}
		}
	}

	public static void viewGuestListOfHotel() throws HotelException,
			SQLException {
		System.out.println("Enter hotel id");
		String hotelId;
		try {
			hotelId = bufferedReader.readLine();
			ArrayList<BookingDetailsBean> bookingDetailsList = hotelservice.viewGuestListOfSpecificHotels(hotelId);
			if (bookingDetailsList == null) {
				System.err.println("No guest to show !!");
			} else {
				System.out.println("Room Id \t User Id \t User Name");
				for (BookingDetailsBean bookingDetailsBean : bookingDetailsList) {
					System.out.println(bookingDetailsBean.getRoomId() + " \t "
							+ bookingDetailsBean.getUserId() + " \t "
							+ bookingDetailsBean.getUserName());
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void viewBookingOfSpecificDate() throws HotelException,
			SQLException {
		LocalDate searchLocalDate = null;
		outer: while (true) {
			try {
				System.out.println("Enter date in (yyyy-mm-dd) that you want to check: ");
				String searchDate = bufferedReader.readLine();
				searchLocalDate = LocalDate.parse(searchDate);
			} catch (DateTimeParseException | IOException e) {
				System.err.println("Wrong date format inserted. Try again !!");
				continue outer;
			}
			break outer;
		}
		ArrayList<BookingDetailsBean> bookingDetailsList = hotelservice.viewBookingsForSpecificDate(searchLocalDate);
		if (bookingDetailsList == null) {
			System.err.println("No such booking available !!");
		} else {
			System.out.println("Booking Id \t Room Id \t User Id \t CheckIn Date \t Check Out Date \t No Of Adults \t No Of Children \t Amount");
			for (BookingDetailsBean bookingDetailsBean : bookingDetailsList) {
				System.out.println(bookingDetailsBean.getBookingId() + " \t\t "
						+ bookingDetailsBean.getRoomId() + " \t\t "
						+ bookingDetailsBean.getUserId() + " \t\t "
						+ bookingDetailsBean.getBookedFrom() + " \t "
						+ bookingDetailsBean.getBookedTo() + " \t\t "
						+ bookingDetailsBean.getNoOfAdults() + " \t\t "
						+ bookingDetailsBean.getNoOfChildren() + " \t\t\t "
						+ bookingDetailsBean.getAmount());
			}
		}
	}
}