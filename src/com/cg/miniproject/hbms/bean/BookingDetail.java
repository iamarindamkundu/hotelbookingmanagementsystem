/************************************************************************************************************
 * File:			BookingDetail.java
 * 
 * Package:			com.cg.miniproject.hdms.bean
 * 
 * Description:		Bean class of Hotel Booking Management System. This bean has all properties to store
 * 					data of booking details and methods to manipulate those data.
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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "BookingDetails")
@NamedQueries({
		@NamedQuery(name = "BookingsOfSpecifiedHotel", query = "SELECT bookingDetail FROM BookingDetail bookingDetail WHERE bookingDetail.roomId IN ( SELECT roomDetail.roomId FROM RoomDetail roomDetail WHERE roomDetail.hotelId=:ROOMDETAIL_HOTELID)"),
		@NamedQuery(name = "BookingOfSpecifiedDate", query = "select bookingDetail from BookingDetail bookingDetail where bookingDetail.bookedFrom=:BOOKINGDETAIL_BOOKEDFROM") })
public class BookingDetail {

	// BookingDetails:
	// booking_id(varchar(4)), room_id(varchar(4)),
	// user_id(varchar(4)), booked_from (date),
	// booked_to(date), no_of_adults, no_of_children, amount(number(6,2))

	@Id
	@SequenceGenerator(name = "bookingIdGen", sequenceName = "bookingId_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingIdGen")
	@Column(name = "booking_id")
	private int bookingId;
	@Column(name = "room_id")
	private int roomId;
	@Column(name = "user_id")
	private int userId;
	@Column(name = "booked_from")
	@Temporal(TemporalType.DATE)
	private Date bookedFrom;
	@Column(name = "booked_to")
	@Temporal(TemporalType.DATE)
	private Date bookedTo;
	@Column(name = "no_of_adults")
	private int noOfAdults;
	@Column(name = "no_of_children")
	private int noOfChildren;
	@Column(name = "amount")
	private double amount;

	public BookingDetail() {
		super();
	}

	public BookingDetail(int bookingId, int roomId, int userId, Date bookedFrom, Date bookedTo, int noOfAdults,
			int noOfChildren, double amount) {
		super();
		this.bookingId = bookingId;
		this.roomId = roomId;
		this.userId = userId;
		this.bookedFrom = bookedFrom;
		this.bookedTo = bookedTo;
		this.noOfAdults = noOfAdults;
		this.noOfChildren = noOfChildren;
		this.amount = amount;
	}

	public BookingDetail(int roomId, int userId, Date bookedFrom, Date bookedTo, int noOfAdults, int noOfChildren,
			double amount) {
		super();
		this.roomId = roomId;
		this.userId = userId;
		this.bookedFrom = bookedFrom;
		this.bookedTo = bookedTo;
		this.noOfAdults = noOfAdults;
		this.noOfChildren = noOfChildren;
		this.amount = amount;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getBookedFrom() {
		return bookedFrom;
	}

	public void setBookedFrom(Date bookedFrom) {
		this.bookedFrom = bookedFrom;
	}

	public Date getBookedTo() {
		return bookedTo;
	}

	public void setBookedTo(Date bookedTo) {
		this.bookedTo = bookedTo;
	}

	public int getNoOfAdults() {
		return noOfAdults;
	}

	public void setNoOfAdults(int noOfAdults) {
		this.noOfAdults = noOfAdults;
	}

	public int getNoOfChildren() {
		return noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "BookingDetail [bookingId=" + bookingId + ", roomId=" + roomId + ", userId=" + userId + ", bookedFrom="
				+ bookedFrom + ", bookedTo=" + bookedTo + ", noOfAdults=" + noOfAdults + ", noOfChildren="
				+ noOfChildren + ", amount=" + amount + "]";
	}

}
