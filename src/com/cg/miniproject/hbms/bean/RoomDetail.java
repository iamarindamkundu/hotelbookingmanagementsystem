/************************************************************************************************************
 * File:			RoomDetail.java
 * 
 * Package:			com.cg.miniproject.hdms.bean
 * 
 * Description:		Bean class of Hotel Booking Management System. This bean has all properties to store
 * 					data of room detail and methods to manipulate those data.
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
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RoomDetails")
@NamedQueries({
		@NamedQuery(name = "DeleteRoomOfSpecifiedHotel", query = "DELETE FROM RoomDetail roomDetail WHERE roomDetail.hotelId=:ROOMDETAIL_HOTELID"),
		@NamedQuery(name = "IsRoomExist", query = "SELECT roomDetail FROM RoomDetail roomDetail WHERE roomDetail.roomNo=:ROOMDETAIL_ROOMNO and roomDetail.hotelId=:ROOMDETAIL_HOTELID"),
		@NamedQuery(name = "GuestRoomDetailOfSpecifiedHotel", query = "SELECT roomDetail FROM RoomDetail roomDetail WHERE roomDetail.roomId in (SELECT bookingDetail.roomId FROM BookingDetail bookingDetail WHERE bookingDetail.roomId in ( SELECT roomDetail.roomId FROM RoomDetail roomDetail WHERE roomDetail.hotelId=:ROOMDETAIL_HOTELID))") })
public class RoomDetail {

	// hotel_id(varchar(4)), room_id (varchar(4)),
	// room_no(varchar(3)), room_type(varchar(20)),
	// per_night_rate (number(6,2)), availability (Boolean), photo (blob))

	@Id
	@SequenceGenerator(name = "roomIdGen", sequenceName = "roomId_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomIdGen")
	@Column(name = "room_id")
	private int roomId;
	@Column(name = "hotel_id")
	private int hotelId;
	@Column(name = "room_no")
	private String roomNo;
	@Column(name = "room_type")
	@Enumerated
	private RoomType roomType;
	@Column(name = "per_night_rate")
	private double perNightRate;
	@Column(name = "availability")
	private String availablity;

	public RoomDetail() {
		super();
	}

	public RoomDetail(int roomId, int hotelId, String roomNo, RoomType roomType, double perNightRate,
			String availablity) {
		super();
		this.roomId = roomId;
		this.hotelId = hotelId;
		this.roomNo = roomNo;
		this.roomType = roomType;
		this.perNightRate = perNightRate;
		this.availablity = availablity;
	}

	public RoomDetail(int hotelId, String roomNo, RoomType roomType, double perNightRate, String availablity) {
		super();
		this.hotelId = hotelId;
		this.roomNo = roomNo;
		this.roomType = roomType;
		this.perNightRate = perNightRate;
		this.availablity = availablity;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public double getPerNightRate() {
		return perNightRate;
	}

	public void setPerNightRate(double perNightRate) {
		this.perNightRate = perNightRate;
	}

	public String getAvailablity() {
		return availablity;
	}

	public void setAvailablity(String availablity) {
		this.availablity = availablity;
	}

	@Override
	public String toString() {
		return "RoomDetail [hotelId=" + hotelId + ", roomId=" + roomId + ", roomNo=" + roomNo + ", roomType=" + roomType
				+ ", perNightRate=" + perNightRate + ", availablity=" + availablity + "]";
	}

}
