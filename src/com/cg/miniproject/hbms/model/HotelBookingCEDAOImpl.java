/************************************************************************************************************
 * File:			HotelBookingCEDAOImpl.java
 * 
 * Package:			com.cg.miniproject.hdms.dao
 * 
 * Description:		DAO class of Hotel Booking Management System for Customer & Employee user.
 * 					This class implements IHotelBooingCEDAO and provides implementation to all
 * 					abstract methods.
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
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.miniproject.hbms.bean.BookingDetail;
import com.cg.miniproject.hbms.bean.Hotel;
import com.cg.miniproject.hbms.bean.RoomDetail;
import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.exception.HotelManagementException;
import com.cg.miniproject.hbms.exception.LoginException;
import com.cg.miniproject.hbms.util.MailMail;
import com.cg.miniproject.hbms.util.PasswordHashing;

@Repository("HotelBookingCEDAOImpl")
public class HotelBookingCEDAOImpl implements IHotelBookingCEDAO {

//	static Logger myLogger = Logger.getLogger(HotelBookingCEDAOImpl.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MailMail mail;

	private String otp;

	public HotelBookingCEDAOImpl() {
		super();
	}

	public HotelBookingCEDAOImpl(EntityManager entityManager, MailMail mail) {
		super();
		this.entityManager = entityManager;
		this.mail = mail;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public MailMail getMail() {
		return mail;
	}

	public void setMail(MailMail mail) {
		this.mail = mail;
	}

	@Override
	public User registerUser(User user) throws BookingException {

//		 myLogger.debug("Attempting to register new user....");

		User user1 = null;

//		 myLogger.debug("Trying to connection with database....");

		List<User> users = null;
		try {
			TypedQuery<User> query = entityManager.createQuery(
					"select user from User user where user.mobileNo=:USERMOBILENO and user.role=:USERROLE", User.class);

			query.setParameter("USERMOBILENO", user.getMobileNo());
			query.setParameter("USERROLE", user.getRole());
			users = query.getResultList();

			if (users.size() > 0) {
				throw new BookingException("You are already registered. Please try to login.");
			}

			user.setPassword(PasswordHashing.generateHash(user.getPassword()));

			entityManager.persist(user);

			user1 = user;
		} catch (Exception e) {

			throw new BookingException(e.getMessage());
		}

		return user1;
	}

	@Override
	public User login(User user) throws BookingException, LoginException {

		// myLogger.debug("Attempting to login by " + user.getRole() + " with
		// user id: " + user.getUserId());

		boolean loginSuccessful = false;

		// myLogger.debug("Trying to connection with database....");

		List<User> users = null;

		try {
			TypedQuery<User> query = entityManager.createQuery("SELECT user FROM User user WHERE user.role=:USERROLE",
					User.class);
			query.setParameter("USERROLE", user.getRole());
			users = query.getResultList();

			if (users.isEmpty()) {
				throw new LoginException("Database access problem occured");
			}

			int userId;
			String password;

			for (User tempUser : users) {
				userId = tempUser.getUserId();
				password = tempUser.getPassword();

				String hashedPassword = PasswordHashing.generateHash(user.getPassword());

				if (userId == user.getUserId() && password.equals(hashedPassword)) {
					loginSuccessful = true;

					return tempUser;
				} else if (userId == user.getUserId()) {
					throw new LoginException("You have entered wrong password.");
				}

			}
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}
		if (loginSuccessful == false) {
			throw new LoginException("Wrong UserId or Password. Please Try Again.");
		}

		// // myLogger.error("Login has failed..." + "\nReason: " +
		// // e.getMessage());
		//
		// throw new LoginException(e.getMessage());
		// }
		//
		// // myLogger.debug("Login is successful by " + user.getRole() + " with
		// // userId: " + user.getUserId());

		return user;
	}

	@Override
	public List<Hotel> searchHotels(String hotelName) throws BookingException {

		// myLogger.debug("Attempting to search a hotel with hotel name: " +
		// hotelName);

		List<Hotel> hotels = null;

		// myLogger.debug("Trying to connection with database....");

		try {
			TypedQuery<Hotel> query = entityManager
					.createQuery("SELECT hotel FROM Hotel hotel WHERE hotel.hotelName=:HOTELNAME", Hotel.class);
			query.setParameter("HOTELNAME", hotelName);
			hotels = query.getResultList();

			if (hotels.isEmpty()) {
				throw new BookingException("No such hotel with name as " + hotelName);
			}
		} catch (Exception e) {

			// myLogger.error("Fail to search a hotel with hotel name: " +
			// hotelName);

			throw new BookingException(e.getMessage());
		}

		/*
		 * // myLogger.error("Fail to search a hotel with hotel name: " + //
		 * hotelName);
		 * 
		 * 
		 * //
		 * myLogger.debug("Successfully retrieve hotel data with hotel name: " +
		 * // hotelName);
		 */
		return hotels;
	}

	@Override
	public int bookAHotel(BookingDetail bookingDetail) throws BookingException {

		// myLogger.debug("Attempting to book a hotel....");

		// myLogger.debug("Trying to connection with database....");

		try {
			entityManager.persist(bookingDetail);

			changeAvailableStatus(bookingDetail.getRoomId());

		} catch (Exception e) {
			throw new BookingException(e.getMessage());
		}

		// // myLogger.error("Failed to book a hotel....");

		// myLogger.debug("Successfully booked a hotel with booking id: " +
		// bookingId);

		return bookingDetail.getBookingId();
	}

	private void changeAvailableStatus(int roomId) throws BookingException {

		try {
			RoomDetail roomDetail = entityManager.find(RoomDetail.class, roomId);

			roomDetail.setAvailablity("False");

			entityManager.merge(roomDetail);
		} catch (Exception e) {
			throw new BookingException("Unable to change room status.");
		}

	}

	@Override
	public BookingDetail viewBookingStatus(int bookingId) throws BookingException {

		// myLogger.debug("Attempting to view booking status with booking id: "
		// + bookingId);

		BookingDetail bookingDetail = null;

		// myLogger.debug("Trying to connection with database....");

		try {
			bookingDetail = entityManager.find(BookingDetail.class, bookingId);

			if (bookingDetail == null)
				throw new BookingException("No booking details found with booking id: " + bookingId);
		} catch (Exception e) {

			// myLogger.debug("Failed to retrieve booking details for booking
			// id: " + bookingId);

			throw new BookingException(e.getMessage());
		}
		// myLogger.debug("Failed to retrieve booking details for booking //
		/*
		 * id: " + bookingId);
		 *
		 * // myLogger.debug("Successfully retrieve booking details for booking
		 * id: // " + bookingId);
		 * 
		 */

		return bookingDetail;
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

	@Override
	public Hotel getAHotel(int hotelId) throws HotelManagementException {

		// myLogger.debug("Attempting to retrieve hotel details for the hotel
		// with hotel id: " + hotelId);

		Hotel hotel = null;

		// myLogger.debug("Trying to connection with database....");

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
		 * // myLogger.error("Failed to retrieve hotel details with hotel id: "
		 * // + hotelId);
		 * 
		 * //
		 * myLogger.debug("Successfully retrieve hotel details for hotel id: " +
		 * // hotelId);
		 * 
		 */

		return hotel;
	}

	@Override
	public RoomDetail getARoom(int roomId) throws HotelManagementException {

		// myLogger.debug("Attempting to retrieve room details from
		// database....");

		RoomDetail roomDetail = null;

		// myLogger.debug("Trying to connection with database....");

		try {
			roomDetail = entityManager.find(RoomDetail.class, roomId);

			if (roomDetail == null)
				throw new HotelManagementException("No record found with Room id: " + roomId);
		} catch (Exception e) {
			throw new HotelManagementException(e.getMessage());
		}

		/*
		 * // myLogger.error("Failed to retrieve room details....");
		 * 
		 * 
		 * // myLogger.debug("Room detail has retrieve success for the room with
		 * // id: " + room.getRoomId());
		 * 
		 */

		return roomDetail;
	}

	@Override
	public List<RoomDetail> getAllRooms(int hotelId) throws BookingException {

		// myLogger.debug("Attempting to generate all rooms list....");

		List<RoomDetail> rooms = null;

		try {
			TypedQuery<RoomDetail> query = entityManager.createQuery(
					"SELECT roomDetail FROM RoomDetail roomDetail WHERE roomDetail.hotelId=:ROOMDETAIL_HOTELID and roomDetail.availablity=:ROOMDETAIL_AVAILABILITY",
					RoomDetail.class);
			query.setParameter("ROOMDETAIL_HOTELID", hotelId);
			query.setParameter("ROOMDETAIL_AVAILABILITY", "True");
			rooms = query.getResultList();

			if (rooms.isEmpty())
				throw new BookingException("No room details found for the hotel_id: " + hotelId);
		} catch (Exception e) {

			// myLogger.error("Generation of all rooms list is failed...");

			throw new BookingException(e.getMessage());
		}
		// myLogger.debug("Trying to connection with database....");

		/*
		 * // myLogger.error("Generation of all rooms list is failed...");
		 * 
		 * 
		 * // myLogger.debug("Generation of all rooms list is successful....");
		 * 
		 */

		return rooms;
	}

	@Override
	public List<Hotel> getHotelList() throws BookingException {
		List<Hotel> hotels = null;

		// myLogger.debug("Trying to connect with database....");

		try {
			TypedQuery<Hotel> query = entityManager.createQuery("SELECT hotel FROM Hotel hotel", Hotel.class);

			hotels = query.getResultList();

			if (hotels.isEmpty())
				throw new HotelManagementException("No hotel data found to be displayed.");
		} catch (Exception e) {

			// myLogger.error("Failed to retrieve hotel list...." + "\nReason: "
			// + e.getMessage());

			throw new BookingException(e.getMessage());
		}

		return hotels;
	}

	@Override
	public void sendPassword(User user) throws BookingException {

		User tempUser = null;

		try {
			tempUser = entityManager.find(User.class, user.getUserId());

			if (tempUser == null) {
				throw new BookingException("User Id: " + user.getUserId()
						+ " does not match. Maybe you are not registered or you have entered wrong password.");
			}

			if (!tempUser.getEmail().equalsIgnoreCase(user.getEmail())) {
				throw new BookingException(
						"User email id: " + user.getEmail() + " is not registered. Please enter registered email id.");
			}

			String randomPassword = generateRandomPassword();

			otp = randomPassword;

			String sender = "itsmrkundu@gmail.com";

			String receiver = user.getEmail();

			String msgBody = "" + otp;

			mail.sendMail(sender, receiver, "ALERT: You attempted to change your password.", "Your OTP::" + msgBody);

		} catch (Exception e) {
			throw new BookingException("Connection Problem or port is busy....");
		}

	}

	private String generateRandomPassword() {

		String Capital_chars = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		String Small_chars = "abcdefghijkmnpqrstuvwxyz";
		String numbers = "123456789";

		String values = Capital_chars + Small_chars + numbers;// + symbols;

		Random rndm_method = new Random();

		char[] tempOtp = new char[8];

		for (int i = 0; i < 8; i++) {

			tempOtp[i] = values.charAt(rndm_method.nextInt(values.length()));

		}
		return new String(tempOtp);
	}

	@Override
	public void changePassword(User user, String otp) throws BookingException {
		User tempUser = null;

		try {

			if (!this.otp.equals(otp))
				throw new BookingException("Invalid OTP.");

			tempUser = entityManager.find(User.class, user.getUserId());

			tempUser.setPassword(PasswordHashing.generateHash(user.getPassword()));
			entityManager.merge(tempUser);
		} catch (Exception e) {
			throw new BookingException(e.getMessage());
		}
	}

	@Override
	public void emailBookingStatus(User user, String bookingStatus) throws BookingException {

		String sender = "itsmrkundu@gmail.com";
		try {
			mail.sendMail(sender, user.getEmail(), "Cheap Stay Booking Status", bookingStatus);
		} catch (Exception e) {
			throw new BookingException("Connection Problem or port is busy....");
		}
	}
}
