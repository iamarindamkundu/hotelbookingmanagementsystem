/************************************************************************************************************
 * File:			IHotelBookingAdminService.java
 * 
 * Package:			com.cg.miniproject.hdms.service
 * 
 * Description:		Service interface of Hotel Booking Management System for Admin user.
 * 					This interface has abstract methods for Admin user which should be
 * 					implemented by implementation class. 
 * 
 * Version:			1.1
 * 
 * Modifications:
 * Author:				|	Date:			|	Change Description:
 * -------------------------------------------------------
 *  Arindam Kundu		|	03/10/2017		|		.....
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 * ***********************************************************************************************************/
package com.cg.miniproject.hbms.service;

import java.util.Date;
import java.util.List;

import com.cg.miniproject.hbms.bean.BookingDetail;
import com.cg.miniproject.hbms.bean.Hotel;
import com.cg.miniproject.hbms.bean.RoomDetail;
import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.exception.HotelManagementException;
import com.cg.miniproject.hbms.exception.LoginException;
import com.cg.miniproject.hbms.exception.RoomManagementException;

public interface IHotelBookingAdminService {

	public User login(User user) throws HotelManagementException, BookingException, LoginException;

	public String addAHotel(Hotel hotel) throws HotelManagementException;

	public Hotel deleteAHotel(int hotelId) throws HotelManagementException;

	public Hotel modifyHotel(Hotel hotel) throws HotelManagementException;

	public String addARoom(RoomDetail roomDetail) throws RoomManagementException, BookingException;

	public RoomDetail deleteARoom(int roomId) throws RoomManagementException;

	public RoomDetail modifyRoom(RoomDetail roomDetail) throws RoomManagementException;

	public Hotel getAHotel(int hotelId) throws HotelManagementException;

	public RoomDetail getARoom(int roomId) throws HotelManagementException;

	public List<Hotel> getHotelList() throws HotelManagementException;

	public List<BookingDetail> bookingsOfSpecifiedHotel(int hotelId) throws HotelManagementException;

	public List<User> guestListOfSpecifiedHotel(int hotelId) throws HotelManagementException;

	public List<RoomDetail> guestRoomDetailOfSpecifiedHotel(int hotelId) throws HotelManagementException;

	public List<BookingDetail> bookingOfSpecifiedDate(Date date) throws HotelManagementException;

	public User getAUser(int userId) throws HotelManagementException;
}
