package com.capgemini.onlinehotelbookings.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.onlinehotelbookings.bean.BookingDetailsBean;
import com.capgemini.onlinehotelbookings.bean.HotelBean;
import com.capgemini.onlinehotelbookings.bean.RoomDetailsBean;
import com.capgemini.onlinehotelbookings.bean.UsersBean;
import com.capgemini.onlinehotelbookings.service.IHotelService;

@Controller
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	private IHotelService hotelService;
	
	@Autowired 
	 private HttpSession httpSession;
	
	@RequestMapping("/home")
	public String home(Model model){
		System.out.println("in home");
		model.addAttribute("user", new UsersBean());
		return "home";
	}
	
	@RequestMapping(value="/logIn" , method = RequestMethod.POST)
	public String logIn(Model model, @RequestParam("userId") Integer userId,@RequestParam("password") String password){
		if(hotelService.logIn(userId, password)){
				List<HotelBean> hotelList=hotelService.getHotelDetails();
				model.addAttribute("userId",userId);
				model.addAttribute("hotelList",hotelList);
				httpSession.setAttribute("userId", userId);
				return "successfulLogIn";
		}
		
		model.addAttribute("user", new UsersBean());
		model.addAttribute("msg", "Invalid Credentials Entered !!");
		return "home";
	}
	
	@RequestMapping("/viewBookingDetails")
	public String viewBookingDetails(Model model,@RequestParam("bookingId") int bookingId){
		model.addAttribute("bookingDetailsBean",hotelService.getBookingDetails(bookingId));
		return "bookingDetails";
	}
	@RequestMapping("/viewRooms")
	public String viewRooms(Model model,@RequestParam("hotelId") String hotelId)
	{
		httpSession.setAttribute("hotelId",hotelId);
		List<RoomDetailsBean> roomList=hotelService.getRoomDetails(hotelId);
		model.addAttribute("roomList",roomList);
		if(roomList.isEmpty()){
			model.addAttribute("msg","No room available");
		}
		return "viewRooms";
	}
	
	@RequestMapping("/bookRoom")
	public String bookRoom(Model model,@RequestParam("roomId") String roomId)
	{
		httpSession.setAttribute("roomId", roomId);
		model.addAttribute("bookRoom",new BookingDetailsBean());
		return "bookRoom";
	}
	
	@RequestMapping("/makeBooking")
	public String makeBooking(Model model,@ModelAttribute("bookRoom") BookingDetailsBean bookingDetailsBean,BindingResult br){
		if(br.hasErrors())
		{
			model.addAttribute("bookRoom",new BookingDetailsBean());
			return "bookRoom";
		}
		else
		{
			int bookingId=hotelService.makeBooking(bookingDetailsBean);
			model.addAttribute("bookingId",bookingId);
			return "successfulBooking";
		}
	}
	@RequestMapping("/storeUser")
	public String storeUser(@Valid @ModelAttribute("user") UsersBean usersBean,BindingResult br,Model model){
		String target=null;
		if(br.hasErrors())
		{
			target="home";
		}
		else
		{
			model.addAttribute("user", new UsersBean());
			int userId=hotelService.register(usersBean);
			model.addAttribute("msg", "Sign Up successful. your userId : "+userId+"! Now you can log in using credentials");
			target="home";
		}
		return target;
	}
}
