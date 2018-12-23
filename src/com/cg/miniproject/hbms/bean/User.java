/************************************************************************************************************
 * File:			User.java
 * 
 * Package:			com.cg.miniproject.hdms.bean
 * 
 * Description:		Bean class of Hotel Booking Management System. This bean has all properties to store
 * 					data of user and methods to manipulate those data.
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
@Table(name = "Users")
@NamedQueries({
		@NamedQuery(name = "GetAllUsersWithSpecificRole", query = "select user from User user where user.role=:USERROLE"),
		@NamedQuery(name = "GuestListOfSpecifiedHotel", query = "select user from User user where user.userId in ( select bookingDetail.userId from BookingDetail bookingDetail where bookingDetail.roomId in ( select roomDetail.roomId from RoomDetail roomDetail where roomDetail.hotelId=:ROOMDETAIL_HOTELID))") })
public class User {

	// Users:
	// user_id(varchar(4)), password (varchar(7)), role(varchar(10),
	// user_name(varchar (20)), mobile_no(varchar(10)), phone(varchar(10)),
	// address (varchar(25)), email (varchar(15))

	@Id
	@SequenceGenerator(name = "userIdGen", sequenceName = "userId_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdGen")
	@Column(name = "user_id")
	private int userId;
	@Column(name = "password")
	private String password;
	@Column(name = "role")
	private String role;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "phone")
	private String phone;
	@Column(name = "address")
	private String address;
	@Column(name = "email")
	private String email;

	public User() {
		super();
	}

	public User(int userId, String password, String role, String userName, String mobileNo, String phone,
			String address, String email) {
		super();
		this.userId = userId;
		this.password = password;
		this.role = role;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}

	public User(String password, String userName, String mobileNo, String phone, String address, String email) {
		super();
		this.password = password;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}

	public User(String password, String role, String userName, String mobileNo, String phone, String address,
			String email) {
		super();
		this.password = password;
		this.role = role;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", role=" + role + ", userName=" + userName
				+ ", mobileNo=" + mobileNo + ", phone=" + phone + ", address=" + address + ", email=" + email + "]";
	}

}
