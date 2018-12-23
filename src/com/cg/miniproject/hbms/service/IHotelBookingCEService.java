/************************************************************************************************************
 * File:			IHotelBookingCEService.java
 * 
 * Package:			com.cg.miniproject.hdms.service
 * 
 * Description:		Service interface of Hotel Booking Management System for Customer & Employee user.
 * 					This interface has abstract methods for Customer & Employee user which should be
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

import java.util.List;

import com.cg.miniproject.hbms.bean.BookingDetail;
import com.cg.miniproject.hbms.bean.Hotel;
import com.cg.miniproject.hbms.bean.RoomDetail;
import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.exception.HotelManagementException;
import com.cg.miniproject.hbms.exception.LoginException;

public interface IHotelBookingCEService {

	public User login(User user) throws BookingException, LoginException;

	public List<Hotel> searchHotels(String hotelName) throws BookingException;

	public int bookAHotel(BookingDetail bookingDetail) throws BookingException;

	public BookingDetail viewBookingStatus(int bookingId) throws BookingException;

	public List<RoomDetail> getAllRooms(int hotelId) throws BookingException;

	RoomDetail getARoom(int roomId) throws HotelManagementException;

	Hotel getAHotel(int hotelId) throws HotelManagementException;

	User registerUser(User user) throws BookingException;

	public List<Hotel> getHotelList() throws BookingException;

	public void sendPassword(User user) throws BookingException;

	public void changePassword(User user, String otp) throws BookingException;

	public User getAUser(int userId) throws HotelManagementException;

	public void emailBookingStatus(User user, String bookingStatus) throws BookingException;

}