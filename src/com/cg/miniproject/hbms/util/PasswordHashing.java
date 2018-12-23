/************************************************************************************************************
 * File:			PasswordHashing.java
 * 
 * Package:			com.cg.miniproject.hdms.util
 * 
 * Description:		Utility class of Hotel Booking Management System. This class uses SHA-1 algorithm 
 * 					to hash password.
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

import java.security.MessageDigest;

public class PasswordHashing {
	private static final String SALT = "my-first-hashing";

	public static String generateHash(String password) throws Exception {
		String hashedPassword = null;

		String saltedPassword = SALT + password;

		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");

			byte[] hashedBytes = sha.digest(saltedPassword.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

			for (int index = 0; index < hashedBytes.length; index++) {
				byte b = hashedBytes[index];

				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		hashedPassword = hash.toString();

		
		return hashedPassword;
	}

}
