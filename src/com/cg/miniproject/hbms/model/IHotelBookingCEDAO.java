/************************************************************************************************************
 * File:			IHotelBookingCEDAO.java
 * 
 * Package:			com.cg.miniproject.hdms.dao
 * 
 * Description:		DAO interface of Hotel Booking Management System for Customer & Employee user.
 * 					This interface provides abstract method for Customer & Employee user. 
 * 
 * Version:			1.1
 * 
 * Modifications:
 * Author:				|	Date:			|	Change Description:
 * -------------------------------------------------------
 *  Arindam Kundu		|	03/10/2017		|		.....
  *  Arindam Kundu		|	07/10/2017		|		........
 *  Arindam Kundu		|	25/10/2017		|		........
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 *  ....				|	....			|		........
 * ***********************************************************************************************************/
package com.cg.miniproject.hbms.model;

import java.util.List;

import com.cg.miniproject.hbms.bean.BookingDetail;
import com.cg.miniproject.hbms.bean.Hotel;
import com.cg.miniproject.hbms.bean.RoomDetail;
import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.exception.HotelManagementException;
import com.cg.miniproject.hbms.exception.LoginException;

public interface IHotelBookingCEDAO {

	public User registerUser(User user) throws BookingException;

	public User login(User user) throws BookingException, LoginException;

	public List<Hotel> searchHotels(String hotelName) throws BookingException;

	public int bookAHotel(BookingDetail bookingDetail) throws BookingException;

	public BookingDetail viewBookingStatus(int bookingId) throws BookingException;

	public Hotel getAHotel(int hotelId) throws HotelManagementException;

	public RoomDetail getARoom(int roomId) throws HotelManagementException;

	public List<RoomDetail> getAllRooms(int hotelId) throws BookingException;

	public List<Hotel> getHotelList() throws BookingException;

	public void sendPassword(User user) throws BookingException;

	public void changePassword(User user, String otp) throws BookingException;

	public User getAUser(int userId) throws HotelManagementException;

	public void emailBookingStatus(User user, String bookingStatus) throws BookingException;

}
