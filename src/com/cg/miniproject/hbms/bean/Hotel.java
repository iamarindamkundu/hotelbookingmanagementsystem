/************************************************************************************************************
 * File:			Hotel.java
 * 
 * Package:			com.cg.miniproject.hdms.bean
 * 
 * Description:		Bean class of Hotel Booking Management System. This bean has all properties to store
 * 					data of hotel and methods to manipulate those data.
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
package com.cg.miniproject.hbms.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Hotels")
@NamedQueries({@NamedQuery(name="DisplayAllHotel", query="SELECT hotel FROM Hotel hotel")})
public class Hotel {

	// Hotel:
	// hotel_id(varchar(4)), city (varchar(10)),
	// hotel_name(varchar (20)), address(varchar(25)),
	//
	// description varchar(50)), avg_rate_per-night (number(m,n)),
	// phone_no1(varchar(10)), phone_no2(varchar(10)), rating(varchar(4)),
	// email (varchar(15)), fax (varchar(15))

	@Id
	@SequenceGenerator(name = "hotelIdGen", sequenceName = "hotelId_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotelIdGen")
	@Column(name = "hotel_id")
	private int hotelId;
	@Column(name = "city")
	private String city;
	@Column(name = "hotel_name")
	private String hotelName;
	@Column(name = "address")
	private String address;
	@Column(name = "description")
	private String description;
	@Column(name = "avg_rate_pernight")
	private double avgRatePerNight;
	@Column(name = "phone_no1")
	private String phoneNo1;
	@Column(name = "phone_no2")
	private String phoneNo2;
	@Column(name = "rating")
	private String rating;
	@Column(name = "email")
	private String email;
	@Column(name = "fax")
	private String fax;

	public Hotel() {
		super();
	}

	public Hotel(int hotelId, String city, String hotelName, String address, String description, double avgRatePerNight,
			String phoneNo1, String phoneNo2, String rating, String email, String fax) {
		super();
		this.hotelId = hotelId;
		this.city = city;
		this.hotelName = hotelName;
		this.address = address;
		this.description = description;
		this.avgRatePerNight = avgRatePerNight;
		this.phoneNo1 = phoneNo1;
		this.phoneNo2 = phoneNo2;
		this.rating = rating;
		this.email = email;
		this.fax = fax;
	}

	public Hotel(String city, String hotelName, String address, String description, double avgRatePerNight,
			String phoneNo1, String phoneNo2, String rating, String email, String fax) {
		super();
		this.city = city;
		this.hotelName = hotelName;
		this.address = address;
		this.description = description;
		this.avgRatePerNight = avgRatePerNight;
		this.phoneNo1 = phoneNo1;
		this.phoneNo2 = phoneNo2;
		this.rating = rating;
		this.email = email;
		this.fax = fax;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAvgRatePerNight() {
		return avgRatePerNight;
	}

	public void setAvgRatePerNight(double avgRatePerNight) {
		this.avgRatePerNight = avgRatePerNight;
	}

	public String getPhoneNo1() {
		return phoneNo1;
	}

	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public String getPhoneNo2() {
		return phoneNo2;
	}

	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", city=" + city + ", hotelName=" + hotelName + ", address=" + address
				+ ", description=" + description + ", avgRatePerNight=" + avgRatePerNight + ", phoneNo1=" + phoneNo1
				+ ", phoneNo2=" + phoneNo2 + ", rating=" + rating + ", email=" + email + ", fax=" + fax + "]";
	}

}
