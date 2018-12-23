/****************************************************************************************************************
 * File:		HotelBookingCEServiceImpl.java
 * 
 * Package:		com.cg.miniproject.hdms.service
 * 
 * Description:	Service class of Hotel Booking Management System for Customer & Employee user.
 * 				This class implements IHotelBookingCEService and provides implementation of all
 * 				abstract methods
 * 
 * Version:		1.1
 * 
 * Modifications:
 * Author:				|	Date:			|	Change Description:
 * -------------------------------------------------------
 *  Arindam Kundu		|	03/10/2017		|	
 *  ....		|	....		|		........
 *  ....		|	....		|		........
 *  ....		|	....		|		........
 *  ....		|	....		|		........
 *  ....		|	....		|		........
 * *****************************************************************************************************************/
package com.cg.miniproject.hbms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cg.miniproject.hbms.bean.BookingDetail;
import com.cg.miniproject.hbms.bean.Hotel;
import com.cg.miniproject.hbms.bean.RoomDetail;
import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.exception.HotelManagementException;
import com.cg.miniproject.hbms.exception.LoginException;
import com.cg.miniproject.hbms.model.IHotelBookingCEDAO;

@Component("HotelBookingCEServiceImpl")
@Transactional
public class HotelBookingCEServiceImpl implements IHotelBookingCEService {

	@Autowired
	private IHotelBookingCEDAO hotelBookingCEDAO;

	public HotelBookingCEServiceImpl() {
		// hotelBookingCEDAO = new HotelBookingCEDAOImpl();
	}

	public HotelBookingCEServiceImpl(IHotelBookingCEDAO hotelBookingCEDAO) {
		super();
		this.hotelBookingCEDAO = hotelBookingCEDAO;
	}

	public IHotelBookingCEDAO getHotelBookingCEDAO() {
		return hotelBookingCEDAO;
	}

	public void setHotelBookingCEDAO(IHotelBookingCEDAO hotelBookingCEDAO) {
		this.hotelBookingCEDAO = hotelBookingCEDAO;
	}

	@Override
	public User registerUser(User user) throws BookingException {
		return hotelBookingCEDAO.registerUser(user);
	}

	@Override
	public Hotel getAHotel(int hotelId) throws HotelManagementException {
		return hotelBookingCEDAO.getAHotel(hotelId);
	}

	@Override
	public RoomDetail getARoom(int roomId) throws HotelManagementException {
		return hotelBookingCEDAO.getARoom(roomId);
	}

	@Override
	public User login(User user) throws BookingException, LoginException {
		return hotelBookingCEDAO.login(user);
	}

	@Override
	public List<Hotel> searchHotels(String hotelName) throws BookingException {
		return hotelBookingCEDAO.searchHotels(hotelName);
	}

	@Override
	public int bookAHotel(BookingDetail bookingDetail) throws BookingException {
		return hotelBookingCEDAO.bookAHotel(bookingDetail);
	}

	@Override
	public BookingDetail viewBookingStatus(int bookingId) throws BookingException {
		return hotelBookingCEDAO.viewBookingStatus(bookingId);
	}

	@Override
	public List<RoomDetail> getAllRooms(int hotelId) throws BookingException {
		return hotelBookingCEDAO.getAllRooms(hotelId);
	}

	@Override
	public List<Hotel> getHotelList() throws BookingException {
		return hotelBookingCEDAO.getHotelList();
	}

	@Override
	public void sendPassword(User user) throws BookingException {
		hotelBookingCEDAO.sendPassword(user);
	}

	@Override
	public void changePassword(User user, String otp) throws BookingException {
		hotelBookingCEDAO.changePassword(user, otp);
	}

	@Override
	public User getAUser(int userId) throws HotelManagementException {
		return hotelBookingCEDAO.getAUser(userId); 
	}

	@Override
	public void emailBookingStatus(User user, String bookingStatus) throws BookingException{
		hotelBookingCEDAO.emailBookingStatus(user, bookingStatus);
		
	}

}
