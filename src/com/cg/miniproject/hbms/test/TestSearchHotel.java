package com.cg.miniproject.hbms.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.miniproject.hbms.bean.Hotel;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.model.HotelBookingCEDAOImpl;
import com.cg.miniproject.hbms.model.IHotelBookingCEDAO;

public class TestSearchHotel {

	static IHotelBookingCEDAO hotelBookingCEDAO = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		hotelBookingCEDAO = new HotelBookingCEDAOImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		hotelBookingCEDAO = null;
	}

	@Test
	public void testSearchHotels() {
		List<Hotel> hotels = null;

		try {
			hotels = hotelBookingCEDAO.searchHotels("Lakeview");
		} catch (BookingException e) {
			e.printStackTrace();
		}

		assertEquals(1, hotels.size());
	}

}
