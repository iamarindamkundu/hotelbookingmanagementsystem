/********************************************************************************************************
 * File:			HotelBookingAdminServiceImpl.java
 * 
 * Package:			com.cg.miniproject.hdms.service
 * 
 * Description:		Service class of Hotel Booking Management System for Admin user.
 * 					This class implements IHotelBookingAdminService and provides implementation of all
 * 					abstract methods
 * 
 * Version:			1.1
 * 
 * Modifications:
 * Author:				|	Date:			|	Change Description:
 * -------------------------------------------------------
 *  Arindam Kundu		|	03/10/2017		|		
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 * ***********************************************************************************************************/
package com.cg.miniproject.hbms.service;

import java.util.Date;
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
import com.cg.miniproject.hbms.exception.RoomManagementException;
import com.cg.miniproject.hbms.model.IHotelBookingAdminDAO;

@Component("HotelBookingAdminServiceImpl")
@Transactional
public class HotelBookingAdminServiceImpl implements IHotelBookingAdminService {

	@Autowired
	private IHotelBookingAdminDAO hotelBookingAdminDAO;

	public HotelBookingAdminServiceImpl() {
		
	}

	public HotelBookingAdminServiceImpl(IHotelBookingAdminDAO hotelBookingAdminDAO) {
		super();
		this.hotelBookingAdminDAO = hotelBookingAdminDAO;
	}

	public IHotelBookingAdminDAO getHotelBookingAdminDAO() {
		return hotelBookingAdminDAO;
	}

	public void setHotelBookingAdminDAO(IHotelBookingAdminDAO hotelBookingAdminDAO) {
		this.hotelBookingAdminDAO = hotelBookingAdminDAO;
	}

	@Override
	public User login(User user) throws HotelManagementException, BookingException, LoginException {
		return hotelBookingAdminDAO.login(user);
	}

	@Override
	public String addAHotel(Hotel hotel) throws HotelManagementException {
		return hotelBookingAdminDAO.addAHotel(hotel);
	}

	@Override
	public Hotel deleteAHotel(int hotelId) throws HotelManagementException {
		return hotelBookingAdminDAO.deleteAHotel(hotelId);
	}

	@Override
	public Hotel modifyHotel(Hotel hotel) throws HotelManagementException {
		return hotelBookingAdminDAO.modifyHotel(hotel);
	}

	@Override
	public String addARoom(RoomDetail roomDetail) throws RoomManagementException, BookingException {
		return hotelBookingAdminDAO.addARoom(roomDetail);
	}

	@Override
	public RoomDetail deleteARoom(int roomId) throws RoomManagementException {
		return hotelBookingAdminDAO.deleteARoom(roomId);
	}

	@Override
	public RoomDetail modifyRoom(RoomDetail roomDetail) throws RoomManagementException {
		return hotelBookingAdminDAO.modifyRoom(roomDetail);
	}

	@Override
	public Hotel getAHotel(int hotelId) throws HotelManagementException {
		return hotelBookingAdminDAO.getAHotel(hotelId);
	}

	@Override
	public RoomDetail getARoom(int roomId) throws HotelManagementException {
		return hotelBookingAdminDAO.getARoom(roomId);
	}

	@Override
	public List<Hotel> getHotelList() throws HotelManagementException {
		return hotelBookingAdminDAO.getHotelList();
	}

	@Override
	public List<BookingDetail> bookingsOfSpecifiedHotel(int hotelId) throws HotelManagementException {
		return hotelBookingAdminDAO.bookingsOfSpecifiedHotel(hotelId);
	}

	@Override
	public List<User> guestListOfSpecifiedHotel(int hotelId) throws HotelManagementException {
		return hotelBookingAdminDAO.guestListOfSpecifiedHotel(hotelId);
	}

	@Override
	public List<RoomDetail> guestRoomDetailOfSpecifiedHotel(int hotelId) throws HotelManagementException {
		return hotelBookingAdminDAO.guestRoomDetailOfSpecifiedHotel(hotelId);
	}

	@Override
	public List<BookingDetail> bookingOfSpecifiedDate(Date date) throws HotelManagementException {
		return hotelBookingAdminDAO.bookingOfSpecifiedDate(date);
	}

	@Override
	public User getAUser(int userId) throws HotelManagementException {
		return hotelBookingAdminDAO.getAUser(userId);
	}

}
