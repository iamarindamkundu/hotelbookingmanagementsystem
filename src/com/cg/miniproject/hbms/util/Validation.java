/************************************************************************************************************
 * File:			Validation.java
 * 
 * Package:			com.cg.miniproject.hdms.util
 * 
 * Description:		Utility class of Hotel Booking Management System. This class validates input data
 * 					and returns true or false according to valid or invalid input data respectively.
 * 
 * Version:			1.0
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
package com.cg.miniproject.hbms.util;

import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

public class Validation {

	public static boolean isValidName(String userName) {
		return Pattern.matches("[a-zA-Z ]{5,20}", userName);
	}

	public static boolean isValidMobileNo(String mobileNo) {
		return Pattern.matches("[789]{1}[0-9]{9}", mobileNo);
	}

	public static boolean isValidPhone(String phone) {
		return Pattern.matches("[234]{1}[0-9]{7}", phone);
	}

	public static boolean isValidEmail(String email) {
		return Pattern.matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", email);
	}

	public static boolean isValidCity(String city) {
		return Pattern.matches("[a-zA-Z ]{3,10}", city);
	}

	public static boolean isValidHotelName(String hotelName) {
		return Pattern.matches("[a-zA-Z ]{5,30}", hotelName);
	}

	public static boolean isValidDescription(String description) {
		
		return description.length()<=50?true:false;
	}

	public static boolean isValidRating(String rating) {
		return Pattern.matches("[a-zA-Z]{1,4}", rating);
	}

	public static boolean isValidRoomNo(String roomNo) {
		return Pattern.matches("[0-9]{1,5}", roomNo);
	}

	public static boolean isValidRatePerNight(double ratePerNight) {
		return Pattern.matches("[0-9]{1,10}.[0-9]{0,3}", String.valueOf(ratePerNight));
	}

	public static boolean isValidIds(String bookId) {
		return Pattern.matches("[0-9]{1,4}", String.valueOf(bookId));
	}

	public static boolean isValidNoOfPerson(int person) {
		return Pattern.matches("[1-4]{1}", String.valueOf(person));
	}

	public static boolean isValidDateFormat(String date) {
		return Pattern.matches("[1-9]{2}/[1-9]{2}/[0-9]{4}", date);
	}

	public static boolean isValidDate(Date bookedFrom) {
		LocalDate localDate = LocalDate.now();

		Date todaysDate = java.sql.Date.valueOf(localDate);

		if (bookedFrom.before(todaysDate))
			return false;

		return true;
	}

	public static boolean isValidDate(Date bookedFrom, Date bookedTo) {
		if (bookedTo.after(bookedFrom))
			return true;

		return false;
	}

	public static boolean isValidNoOfChild(int noOfChildren) {

		return Pattern.matches("[0-4]{1}", String.valueOf(noOfChildren));
	}

	public static boolean isValidPassword(String password) {

		if (password.length() <= 4 && password.length() > 8) {
			return false;
		}

		return true;
	}

	public static boolean isValidAddress(String address) {
		if (address.length() <= 4 && address.length() >= 49)
			return false;
		return true;
	}
}
