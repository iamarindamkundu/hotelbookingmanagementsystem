/************************************************************************************************************
 * File:			IHotelBookingAdminDAO.java
 * 
 * Package:			com.cg.miniproject.hdms.dao
 * 
 * Description:		DAO interface of Hotel Booking Management System for Admin user.
 * 					This interface provides abstract method for Admin user. 
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

public interface IHotelBookingAdminDAO {
	// ======================================operations specially done by admin
	public User login(User user) throws BookingException, LoginException;

	// ############ hotel management ##############

	public String addAHotel(Hotel hotel) throws HotelManagementException;

	public Hotel deleteAHotel(int hotelId) throws HotelManagementException;

	public Hotel modifyHotel(Hotel hotel) throws HotelManagementException;

	// ########### room management ################

	public String addARoom(RoomDetail roomDetail) throws RoomManagementException, BookingException;

	public RoomDetail deleteARoom(int roomId) throws RoomManagementException;

	public RoomDetail modifyRoom(RoomDetail roomDetail) throws RoomManagementException;

	// $$$$$$$$$$$$ extra functions $$$$$$$$$$$$$$

	public Hotel getAHotel(int hotelId) throws HotelManagementException;

	public RoomDetail getARoom(int roomId) throws HotelManagementException;

	public List<Hotel> getHotelList() throws HotelManagementException;
	
	public List<BookingDetail> bookingsOfSpecifiedHotel(int hotelId) throws HotelManagementException;
	
	public List<User> guestListOfSpecifiedHotel(int hotelId) throws HotelManagementException;
	
	public List<RoomDetail> guestRoomDetailOfSpecifiedHotel(int hotelId) throws HotelManagementException;
	
	public List<BookingDetail> bookingOfSpecifiedDate(Date date) throws HotelManagementException;
	
	public User getAUser(int userId) throws HotelManagementException;
}
