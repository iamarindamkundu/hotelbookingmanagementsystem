/************************************************************************************************************
 * File:			HotelBookingAdminDAOImpl.java
 * 
 * Package:			com.cg.miniproject.hdms.dao
 * 
 * Description:		DAO class of Hotel Booking Management System for Admin user.
 * 					This class implements IHotelBookingAdminDAO and provides implementation
 * 					of all abstract methods.
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
package com.cg.miniproject.hbms.model;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.miniproject.hbms.bean.BookingDetail;
import com.cg.miniproject.hbms.bean.Hotel;
import com.cg.miniproject.hbms.bean.RoomDetail;
import com.cg.miniproject.hbms.bean.RoomType;
import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.exception.HotelManagementException;
import com.cg.miniproject.hbms.exception.LoginException;
import com.cg.miniproject.hbms.exception.RoomManagementException;
import com.cg.miniproject.hbms.util.PasswordHashing;

@Repository("HotelBookingAdminDAOImpl")
public class HotelBookingAdminDAOImpl implements IHotelBookingAdminDAO {

	// static Logger myLogger = Logger.getLogger(HotelBookingAdminDAOImpl.class
	// .getName());

	@PersistenceContext
	private EntityManager entityManager;

	public HotelBookingAdminDAOImpl() {
		super();
	}

	public HotelBookingAdminDAOImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public User login(User user) throws BookingException, LoginException {

		// myLogger.debug("Login Attempted by " + user.getRole()
		// + " with userId: " + user.getUserId());

		boolean loginSuccessful = false;

		// myLogger.debug("Trying to connect with database....");

		List<User> users = null;

		try {

			TypedQuery<User> query = entityManager.createNamedQuery("GetAllUsersWithSpecificRole",
					User.class);

			query.setParameter("USERROLE", user.getRole());

			users = query.getResultList();

			if (users.isEmpty()) {
				throw new LoginException("Database access problem occured.");
			}

			int userId;
			String password;

			for (User tempUser : users) {
				userId = tempUser.getUserId();
				password = tempUser.getPassword();

				String hashedPassword = PasswordHashing.generateHash(user.getPassword());

				if (userId == user.getUserId() && password.equals(hashedPassword)) {
					loginSuccessful = true;
					user = tempUser;
					break;
				} else if (userId == user.getUserId()) {

					throw new LoginException("You have entered wrong password.");
				} else {
					// myLogger.debug("Login attempted by " + user.getRole()
					// + " with userId: " + user.getUserId()
					// + " is failed.");

					throw new LoginException("Wrong UserId or Password. Please Try Again.");
				}
			}
		} catch (Exception e) {

			throw new LoginException(e.getMessage());
		}

		/*
		 * // myLogger.debug("Login attempted by " + user.getRole() // +
		 * " with userId: " + user.getUserId() // + " is failed."); //
		 * myLogger.debug("Login attempted by " + user.getRole() // +
		 * " with userId: " + user.getUserId() // + " is failed."); //
		 * myLogger.error("Login failed..." + "Reason: " + e.getMessage()); //
		 * myLogger.debug("Login is successful by " + user.getRole() // +
		 * " with userId: " + user.getUserId());
		 */
		return user;
	}

	@Override
	public String addAHotel(Hotel hotel) throws HotelManagementException {

		// myLogger.debug("Attempt to add a new hotel....");

		// myLogger.debug("Trying to connect with database....");

		try {
			entityManager.persist(hotel);
		} catch (Exception e) {
			throw new HotelManagementException("Unable to add new hotel details.");
		}

		/*
		 * // myLogger.debug("Adding a new hotel details failed....");
		 * 
		 * // myLogger.debug("Adding new hotel with hotel id: " + //
		 * hotel.getHotelId() // + " is successful.");
		 * 
		 */

		return String.valueOf(hotel.getHotelId());
	}

	@Override
	public Hotel deleteAHotel(int hotelId) throws HotelManagementException {

		// myLogger.debug("Attempting to delete hotel details....");

		Hotel hotel = null;

		try {
			hotel = entityManager.find(Hotel.class, hotelId);

			if (hotel == null) {
				throw new HotelManagementException("No hotel found with hotel id:" + hotelId);
			}

			entityManager.remove(hotel);

			Query query = entityManager
					.createNamedQuery("DeleteRoomOfSpecifiedHotel");
			query.setParameter("ROOMDETAIL_HOTELID", hotelId);
		} catch (Exception e) {
			throw new HotelManagementException("Deletion is not successful.");
		}

		// myLogger.debug("Trying to connect with database....");

		return hotel;
	}

	@Override
	public Hotel modifyHotel(Hotel hotel) throws HotelManagementException {
		Hotel tempHotel = null;

		// myLogger.debug("Trying to connect with database....");

		try {
			entityManager.merge(hotel);

			tempHotel = hotel;
		} catch (Exception e) {
			
			throw new HotelManagementException("Hotel details updatation failed.");
		}

		return tempHotel;
	}

	@Override
	public String addARoom(RoomDetail roomDetail) throws RoomManagementException, BookingException {

		// myLogger.debug("Adding new room details....");

		// myLogger.debug("Trying to connect with database....");

		try {

			isHotelExist(roomDetail);

			isRoomExist(roomDetail);

			entityManager.persist(roomDetail);
		} catch (Exception e) {
			throw new RoomManagementException("Cannot add new room details.");
		}

		/*
		 * // myLogger.debug("Room details successfully added with id: " +
		 * roomId);
		 * 
		 */

		return String.valueOf(roomDetail.getRoomId());
	}

	private void isRoomExist(RoomDetail roomDetail) throws RoomManagementException {

		try {
			TypedQuery<RoomDetail> query = entityManager.createNamedQuery(
					"IsRoomExist",
					RoomDetail.class);

			query.setParameter("ROOMDETAIL_ROOMNO", roomDetail.getRoomNo());
			query.setParameter("ROOMDETAIL_HOTELID", roomDetail.getHotelId());

			if (query.getResultList().size() > 0) {
				throw new RoomManagementException("Room Already Exist in this hotel.");
			}
		} catch (Exception e) {
			throw new RoomManagementException(e.getMessage());
		}

	}

	private boolean isHotelExist(RoomDetail roomDetail) throws HotelManagementException {

		try {
			Hotel hotel = entityManager.find(Hotel.class, roomDetail.getHotelId());

			if (hotel == null) {
				throw new HotelManagementException("No Hotel with hotel id: " + roomDetail.getHotelId());
			}
		} catch (Exception e) {
			throw new HotelManagementException(e.getMessage());
		}

		return true;
	}

	@Override
	public RoomDetail deleteARoom(int roomId) throws RoomManagementException {
		RoomDetail room = null;

		// myLogger.debug("Trying to delete room details with room id: " +
		// roomId);
		//
		// myLogger.debug("Trying to connect with database....");

		try {
			room = entityManager.find(RoomDetail.class, roomId);

			if (room == null) {
				throw new RoomManagementException("No room found with room id:" + roomId);
			}

			entityManager.remove(room);

		} catch (Exception e) {

			throw new RoomManagementException(e.getMessage());
		}

		/*
		 * // myLogger.debug("Room details of room id: " + roomId + " has //
		 * successfully deleted.");
		 * 
		 */

		return room;
	}

	@Override
	public RoomDetail modifyRoom(RoomDetail roomDetail) throws RoomManagementException {
		RoomDetail room = null;

		// myLogger.debug("Trying to modify room details for room id: " +
		// roomDetail.getRoomId());

		// myLogger.debug("Trying to connect with database....");

		try {
			entityManager.merge(roomDetail);

			room = roomDetail;
		} catch (Exception e) {
			throw new RoomManagementException("Room details updatation failed.");
		}

		/*
		 * // myLogger.error("Modification of room details for room id: " + //
		 * roomDetail.getRoomId() + " failed." + "\n Reason:" + //
		 * e.getMessage());
		 * 
		 * // myLogger.debug("Successfully updated room details for room id: " +
		 * // roomDetail.getHotelId());
		 * 
		 */

		return room;
	}

	@Override
	public Hotel getAHotel(int hotelId) throws HotelManagementException {
		Hotel hotel = null;

		// myLogger.debug("Attempting to retrieve hotel details with id: " +
		// hotelId);

		// myLogger.debug("Trying to connect with database....");

		try {
			hotel = entityManager.find(Hotel.class, hotelId);

			if (hotel == null)
				throw new HotelManagementException("No record found with hotel id: " + hotelId);
		} catch (Exception e) {

			// myLogger.error("Failed to retrieve hotel details for hotel id: "
			// + hotelId + "\n Reason:" + e.getMessage());

			throw new HotelManagementException(e.getMessage());
		}

		/*
		 * // myLogger.error("Failed to retrieve hotel details for hotel id: "
		 * // + hotelId + "\n Reason:" + e.getMessage());
		 * 
		 * myLogger.debug("Successfully retrieve hotel details for hotel id: " +
		 * // hotelId);
		 * 
		 */

		return hotel;
	}

	@Override
	public RoomDetail getARoom(int roomId) throws HotelManagementException {

		// myLogger.debug("Attempting to retrieve room details with id: " +
		// roomId);

		RoomDetail roomDetail = null;

		// myLogger.debug("Trying to connect with database....");

		try {
			roomDetail = entityManager.find(RoomDetail.class, roomId);

			if (roomDetail == null)
				throw new HotelManagementException("No record found with Room id: " + roomId);
		} catch (Exception e) {
			throw new HotelManagementException(e.getMessage());
		}

		/*
		 * // myLogger.error("Retrieving roomdetails from database //
		 * failed....");
		 * 
		 * // myLogger.debug("Retrieving roomdetails with roomid: " + roomId //
		 * + " is successful.");
		 * 
		 */

		return roomDetail;
	}

	@Override
	public List<Hotel> getHotelList() throws HotelManagementException {

		List<Hotel> hotels = null;

		// myLogger.debug("Trying to retrieve hotel list....");

		// myLogger.debug("Trying to connect with database....");

		try {
			TypedQuery<Hotel> query = entityManager.createNamedQuery("DisplayAllHotel", Hotel.class);

			hotels = query.getResultList();

			if (hotels.isEmpty())
				throw new HotelManagementException("No hotel data found to be displayed.");
		} catch (Exception e) {

			// myLogger.error("Failed to retrieve hotel list...." + "\nReason: "
			// + e.getMessage());

			throw new HotelManagementException(e.getMessage());
		}

		/*
		 * // myLogger.error("Failed to retrieve hotel list...." + "\nReason: "
		 * // + e.getMessage()); //
		 * myLogger.debug("Successfully retrieved hotel list....");
		 * 
		 */

		return hotels;
	}

	@Override
	public List<BookingDetail> bookingsOfSpecifiedHotel(int hotelId) throws HotelManagementException {
		List<BookingDetail> bookings = null;

		// myLogger.debug("Admin tried to generate report of BOOKINGS OF
		// SPECIFIED HOTEL....");

		// myLogger.debug("Trying to connect with database....");

		try {
			TypedQuery<BookingDetail> query = entityManager.createNamedQuery(
					"BookingsOfSpecifiedHotel",
					BookingDetail.class);
			query.setParameter("ROOMDETAIL_HOTELID", hotelId);

			bookings = query.getResultList();

			if (bookings.isEmpty()) {
				throw new HotelManagementException("No booking details found to be displayed.");
			}
		} catch (Exception e) {
			throw new HotelManagementException(e.getMessage());
		}

		/*
		 * // myLogger.error("Generation of BOOKINGS OF SPECIFIED HOTEL has //
		 * failed....");
		 * 
		 * // myLogger.debug("Generation of BOOKINGS OF SPECIFIED HOTEL is //
		 * successful....");
		 * 
		 */

		return bookings;
	}

	@Override
	public List<User> guestListOfSpecifiedHotel(int hotelId) throws HotelManagementException {
		List<User> users = null;

		// myLogger.debug("Admin tried to generate report of GUEST LIST OF
		// SPECIFIED HOTEL....");

		// myLogger.debug("Trying to connect with database....");

		try {
			TypedQuery<User> query = entityManager.createNamedQuery(
					"GuestListOfSpecifiedHotel",
					User.class);
			query.setParameter("ROOMDETAIL_HOTELID", hotelId);

			users = query.getResultList();

			if (users.isEmpty())
				throw new HotelManagementException("No user details found to be display.");
		} catch (Exception e) {
			throw new HotelManagementException(e.getMessage());
		}

		/*
		 * 
		 * // myLogger.error("Generation of GUEST LIST OF SPECIFIED HOTEL has //
		 * failed....");
		 * 
		 * 
		 * // myLogger.debug("Generation of LIST OF SPECIFIED HOTEL is //
		 * successful....");
		 * 
		 */

		return users;
	}

	@Override
	public List<RoomDetail> guestRoomDetailOfSpecifiedHotel(int hotelId) throws HotelManagementException {
		List<RoomDetail> roomDetails = null;

		try {
			TypedQuery<RoomDetail> query = entityManager.createNamedQuery(
					"GuestRoomDetailOfSpecifiedHotel",
					RoomDetail.class);
			query.setParameter("ROOMDETAIL_HOTELID", hotelId);

			roomDetails = query.getResultList();

			if (roomDetails.isEmpty())
				throw new HotelManagementException("No room details found with hotelId: " + hotelId);
		} catch (Exception e) {

			throw new HotelManagementException(e.getMessage());
		}

		return roomDetails;
	}

	@Override
	public List<BookingDetail> bookingOfSpecifiedDate(Date date) throws HotelManagementException {
		List<BookingDetail> bookings = null;

		// myLogger.debug("Admin tried to generate report of BOOKINGS OF
		// SPECIFIED DATE....");

		// myLogger.debug("Trying to connect with database....");

		try {
			TypedQuery<BookingDetail> query = entityManager.createNamedQuery(
					"BookingOfSpecifiedDate",
					BookingDetail.class);

			query.setParameter("BOOKINGDETAIL_BOOKEDFROM", date);

			bookings = query.getResultList();

			if (bookings.isEmpty())
				throw new HotelManagementException("No booking details found on date: " + date);
		} catch (Exception e) {
			throw new HotelManagementException(e.getMessage());
		}

		/*
		 * // myLogger.error("Generation of bookings of specified date has //
		 * failed....");
		 * 
		 * // myLogger.debug("Generation of booking of specified data is //
		 * successful....");
		 * 
		 */

		return bookings;
	}

	@Override
	public User getAUser(int userId) throws HotelManagementException {
		User user = null;
		try {
			user = entityManager.find(User.class, userId);

			if (user == null)
				throw new HotelManagementException("No user found with user id:" + userId);
		} catch (Exception e) {
			throw new HotelManagementException(e.getMessage());
		}
		return user;
	}
}
