package com.cg.miniproject.hbms.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.exception.LoginException;
import com.cg.miniproject.hbms.model.HotelBookingCEDAOImpl;
import com.cg.miniproject.hbms.model.IHotelBookingCEDAO;

public class TestLoginUser {

	static IHotelBookingCEDAO hotelBookingCEDAO = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hotelBookingCEDAO = new HotelBookingCEDAOImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		hotelBookingCEDAO = null;
	}

	@Test(expected = LoginException.class)
	public void testLogin() throws BookingException, LoginException {
		User user = new User();
		
//		user.setUserId("Ar12");
		user.setPassword("arin@1234");
		user.setRole("Customer");
		
		hotelBookingCEDAO.login(user);
	}

}
