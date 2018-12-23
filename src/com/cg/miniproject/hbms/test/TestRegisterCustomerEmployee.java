package com.cg.miniproject.hbms.test;

import static org.junit.Assert.assertNotSame;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.model.HotelBookingCEDAOImpl;
import com.cg.miniproject.hbms.model.IHotelBookingCEDAO;

public class TestRegisterCustomerEmployee {

	static IHotelBookingCEDAO hotelBookingCEDAO = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		hotelBookingCEDAO = new HotelBookingCEDAOImpl();

	}

	@AfterClass
	public static void setUpAfterClass() throws Exception {
		hotelBookingCEDAO = null;
	}

	@Test
	public void testRegisterUser() {
		User user = new User();

		user.setUserName("AAAA BBBB");
		user.setRole("Customer");
		user.setPhone("9076543348");
		user.setPassword("hu7890");
		user.setEmail("abc_345@gmail.com");
		user.setAddress("");

		User tempUser = null;

		try {
			tempUser = hotelBookingCEDAO.registerUser(user);

		} catch (BookingException e) {
			e.printStackTrace();
		}
		assertNotSame(0, tempUser.getUserId());
	}

}
