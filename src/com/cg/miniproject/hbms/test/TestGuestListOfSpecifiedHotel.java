package com.cg.miniproject.hbms.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.miniproject.hbms.exception.HotelManagementException;
import com.cg.miniproject.hbms.model.HotelBookingAdminDAOImpl;
import com.cg.miniproject.hbms.model.IHotelBookingAdminDAO;

public class TestGuestListOfSpecifiedHotel {

	static IHotelBookingAdminDAO hotelBookingAdminDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		hotelBookingAdminDAO = new HotelBookingAdminDAOImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		hotelBookingAdminDAO = null;
	}

	@Test(expected = HotelManagementException.class)
	public void testGuestListOfSpecifiedHotel() throws HotelManagementException {

		hotelBookingAdminDAO.guestListOfSpecifiedHotel(80);
	}

}
