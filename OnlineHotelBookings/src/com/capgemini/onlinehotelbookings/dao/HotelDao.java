package com.capgemini.onlinehotelbookings.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.capgemini.onlinehotelbookings.bean.BookingDetailsBean;
import com.capgemini.onlinehotelbookings.bean.HotelBean;
import com.capgemini.onlinehotelbookings.bean.RoomDetailsBean;
import com.capgemini.onlinehotelbookings.bean.UsersBean;

@Repository
@Transactional
public class HotelDao implements IHotelDao {

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public int register(UsersBean usersBean)
	{
		entityManager.persist(usersBean);
		Query query=entityManager.createNativeQuery("select users_seq.currval from dual");
		BigDecimal bigDecimal=(BigDecimal)query.getSingleResult();
		return bigDecimal.intValue();
	}
	
	@Override
	public int makeBooking(BookingDetailsBean bookingDetailsBean)
	{	
		RoomDetailsBean roomDetailsBean= entityManager.find(RoomDetailsBean.class, bookingDetailsBean.getRoomId());
		roomDetailsBean.setAvailability("no");
		System.out.println(roomDetailsBean.getRoomId());
		System.out.println(roomDetailsBean.getAvailability());

		DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate startDate=LocalDate.parse(bookingDetailsBean.getBookedFrom(),dateTimeFormatter);
		LocalDate endDate=LocalDate.parse(bookingDetailsBean.getBookedTo(),dateTimeFormatter);
		Period p=startDate.until(endDate);
		
		double amount=p.getDays()*roomDetailsBean.getPerNightRate();
		
		if(amount==0.0)
			amount=roomDetailsBean.getPerNightRate();
		
		bookingDetailsBean.setAmount(amount);
		
		entityManager.persist(roomDetailsBean);
		entityManager.persist(bookingDetailsBean);
		Query query=entityManager.createNativeQuery("select RoomDetails_seq.currval from dual");
		BigDecimal bigDecimal=(BigDecimal)query.getSingleResult();
		return bigDecimal.intValue();
	}
	
	@Override
	public boolean logIn(Integer userId,String password)
	{
		UsersBean usersBean=entityManager.find(UsersBean.class, userId);
		if(usersBean!=null){
			if(usersBean.getPassword().equals(password))
				return true;
		}
		return false;
	}
	
	@Override
	public List<HotelBean> getHotelDetails()
	{
		TypedQuery<HotelBean> query = entityManager.createQuery("SELECT e FROM hotel e", HotelBean.class);
		return query.getResultList();
	}
	
	@Override
	public List<RoomDetailsBean> getRoomDetails(String hotelId)
	{
		TypedQuery<RoomDetailsBean> query = entityManager.createQuery("SELECT e FROM RoomDetails e where e.availability='yes' and e.hotelId=:hotelId", RoomDetailsBean.class);
		query.setParameter("hotelId", hotelId);
		return query.getResultList();
	}

	@Override
	public BookingDetailsBean getBookingDetails(int bookingId) {
		BookingDetailsBean bookingDetailsBean= entityManager.find(BookingDetailsBean.class,bookingId);
		return bookingDetailsBean;
	}
}
