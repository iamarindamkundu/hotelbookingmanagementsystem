/************************************************************************************************************
 * File:			DBUtil.java
 * 
 * Package:			com.cg.miniproject.hdms.util
 * 
 * Description:		Utility class of Hotel Booking Management System
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
package com.cg.miniproject.hbms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.cg.miniproject.hbms.exception.BookingException;

public class DBUtil {

	public static Connection getConnection()
			throws BookingException, IOException, ClassNotFoundException, SQLException {
		Connection con = null;

		File propertyFile = new File("db_config.properties");

		if (propertyFile.exists() == false)
			throw new BookingException("Could not Load from properties file. Reason File Not Found");

		InputStream is = new FileInputStream(propertyFile);

		Properties dbProperties = new Properties();

		dbProperties.load(is);

		Class.forName(dbProperties.getProperty("driver"));

		con = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties.getProperty("uname"),
				dbProperties.getProperty("password"));

		return con;
	}
}
