package com.cg.miniproject.hbms.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.cg.miniproject.hbms.bean.BookingDetail;
import com.cg.miniproject.hbms.bean.Hotel;
import com.cg.miniproject.hbms.bean.RoomDetail;
import com.cg.miniproject.hbms.bean.RoomType;
import com.cg.miniproject.hbms.bean.User;
import com.cg.miniproject.hbms.exception.BookingException;
import com.cg.miniproject.hbms.exception.HotelManagementException;
import com.cg.miniproject.hbms.exception.LoginException;
import com.cg.miniproject.hbms.service.IHotelBookingAdminService;
import com.cg.miniproject.hbms.service.IHotelBookingCEService;
import com.cg.miniproject.hbms.util.Validation;
import com.cg.miniproject.hbms.util.ViewDesigner;

@Component
public class HotelBookingUI {
	@Autowired
	private IHotelBookingAdminService hotelBookingAdminService;
	@Autowired
	private IHotelBookingCEService hotelBookingCEService;

	Scanner sc = new Scanner(System.in);

	public HotelBookingUI() {

	}

	public HotelBookingUI(IHotelBookingAdminService hotelBookingAdminService,
			IHotelBookingCEService hotelBookingCEService) {
		super();
		this.hotelBookingAdminService = hotelBookingAdminService;
		this.hotelBookingCEService = hotelBookingCEService;
	}

	public static void main(String[] args) {

//		PropertyConfigurator.configure("log4j.properties");
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");

		HotelBookingUI ui = ctx.getBean(HotelBookingUI.class);
		
		
		
		while (true) {
			try {
				ui.showMenu();
			} catch (Exception e) {
				System.out.println("Something went very bad. I am sorry to say that I cannot handle such situation.");
				System.out.println(" >>>> Application Closed <<<< ");
				System.exit(1);
			}
		}
	}

	private void showMenu() {

		System.out.println("**************************************************************");
		System.out.println("||  Welcome to Cheap Stays Hotel Booking Management System  ||");
		System.out.println("**************************************************************");
		System.out.println(" >> Please select your category : ");
		System.out.println(" >> Press 1 for 'Customer' related activities");
		System.out.println(" >> Press 2 for Hotel 'Employee' related activities");
		System.out.println(" >> Press 3 for 'Admin' related activities");
		System.out.println(" >> Press 9 to exit from the application");
		System.out.println(" >> Please enter your choice : ");

		int choice = sc.nextInt();

		switch (choice) {
		case 1:

			customerActivites();
			break;
		case 2:

			hotelEmployeeActivites();
			break;
		case 3:

			adminActivites();
			break;

		case 9:
			System.out.println("Thank you for visiting our application. Have a good day. Bye");
			System.exit(0);
			break;

		default:
			System.out.println("Wrong Choice. Please enter from the above given options only");
		}
	}

	private void customerActivites() {

		boolean continueIteration = true;

		do {
			System.out.println();
			System.out.println("************************************");
			System.out.println(" Welcome to Customer Portal: ");
			System.out.println("------------------------------------");
			System.out.println(" Press 1 to Register ");
			System.out.println(" Press 2 to login ");
			System.out.println(" Press 3 to go back ");
			System.out.println(" Press 4 to exit ");
			System.out.println(" Please enter your choice : ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				customerRegister();
				break;
			case 2:
				customerLogin();
				break;
			case 3:
				continueIteration = false;
				break;
			case 4:
				System.out.println("Thank you for visiting our application. Have a good day. Bye");
				System.exit(0);
			default:
				System.out.println("Wrong Choice. Please enter from the above given options only");
			}
		} while (continueIteration);
	}

	private void customerLogin() {
		System.out.println();
		System.out.println("************************************");
		System.out.println(" Welcome to Customer Login Page");
		System.out.println("--------------------------------");
		System.out.println(" >> Press 1 to Enter User ID & Password:");
		System.out.println(" >> Press 2 to if you have forgotten your password or want to change it:");
		System.out.println(" << Press -1 to go back: ");
		System.out.print(" >> Your Choice: ");
		int choice = sc.nextInt();
		int userId = 0;
		String password = null;
		String email = null;
		switch (choice) {
		case 1:
			System.out.println(" Enter your User ID : ");
			userId = sc.nextInt();
			System.out.println(" Enter your Password : ");
			password = sc.next();

			processCustomerLogin(userId, password);

			break;
		case 2:
			System.out.println(" Enter your User ID :");
			userId = sc.nextInt();
			System.out.println(" Enter your registered email ID :");
			email = sc.next();

			forgetPassword(userId, email);

			break;
		case -1:
			return;
		default:
			System.out.println("Wrong Choice. Please enter from the above given options only");

		}

	}

	private void forgetPassword(int userId, String email) {

		User user = new User();
		user.setUserId(userId);
		user.setEmail(email);
		String newPassword = null;
		try {
			hotelBookingCEService.sendPassword(user);
			System.out.println("Please Check your email and enter OTP to reset password:");
			String otp = sc.next();
			do {
				System.out.println("Enter new password:");
				newPassword = sc.next();
				System.out.println("Re-enter new password:");
				String reEnterNewPassword = sc.next();

				if (newPassword.equals(reEnterNewPassword))
					break;

				System.out.println("Password doesnot match. Please re-enter again.");
			} while (true);

			user.setPassword(newPassword);

			hotelBookingCEService.changePassword(user, otp);

			System.out.println("Password is successfully changed. Please Login again.");

		} catch (BookingException e) {
			e.printStackTrace();
			System.out.println("Cannot send mail: Reason: " + e.getMessage());
		}

	}

	private void processCustomerLogin(int userId, String password) {
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		user.setRole("Customer");

		boolean isIterate = true;

		try {
			user = hotelBookingCEService.login(user);
			do {
				System.out.println();
				System.out.println("=========================================");
				System.out.println(" Welcome " + user.getUserName());
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

				System.out.println("Please select an option to continue ");
				System.out.println(" >> Press 1 Check Available hotels");
				System.out.println(" >> Press 2 to Search for hotel details");
				System.out.println(" >> Press 3 to Book hotel rooms");
				System.out.println(" >> Press 4 to View Booking Status");
				System.out.println(" << Press 5 to Logout");
				System.out.println(" << Press 0 to Logout and Exit");

				System.out.println("=========================================");

				System.out.println("Please enter your choice : ");
				int ch = sc.nextInt();

				switch (ch) {

				case 1:
					checkAvailableHotels();
					break;
				case 2:
					searchHotelRoom(user);
					break;

				case 3:
					bookHotelRoom(user);
					break;

				case 4:
					viewBookingStatus(user);
					break;

				case 5:
					isIterate = false;
					user = null;
					break;
				case 0:
					System.out.println("Thank you for visiting our application. Have a good day. Bye");
					System.exit(0);
					break;

				default:
					System.out.println("Wrong Choice. Please enter from the above given options only");
				}
			} while (isIterate);
		} catch (LoginException e) {
			System.out.println("Cannot Login. Reason: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Invalid credentials");
		}

	}

	private void checkAvailableHotels() {
		System.out.println("Hotel list: ");

		List<Hotel> hotels = null;
		try {
			hotels = hotelBookingCEService.getHotelList();

			Iterator<Hotel> it = hotels.iterator();

			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

			while (it.hasNext()) {
				Hotel hotel = it.next();

				Map<String, Object> hotelDetail = new HashMap<String, Object>();

				hotelDetail.put("Hotel Id", hotel.getHotelId());
				hotelDetail.put("Hotel Name", hotel.getHotelName());
				hotelDetail.put("Hotel Address", hotel.getAddress());
				hotelDetail.put("City", hotel.getCity());
				hotelDetail.put("Description", hotel.getDescription());
				hotelDetail.put("Average Rate", hotel.getAvgRatePerNight());
				hotelDetail.put("Mobile No", hotel.getPhoneNo1());
				hotelDetail.put("Phone No", hotel.getPhoneNo2());
				hotelDetail.put("Rating", hotel.getRating());
				hotelDetail.put("Email", hotel.getEmail());
				hotelDetail.put("Fax", hotel.getFax());

				items.add(hotelDetail);
			}

			List<String> headerOrder = new ArrayList<String>();

			headerOrder.add("Hotel Id");
			headerOrder.add("Hotel Name");
			headerOrder.add("Hotel Address");
			headerOrder.add("City");
			headerOrder.add("Description");
			headerOrder.add("Average Rate");
			headerOrder.add("Mobile No");
			headerOrder.add("Phone No");
			headerOrder.add("Rating");
			headerOrder.add("Email");
			headerOrder.add("Fax");

			if (!hotels.isEmpty()) {
				ViewDesigner.tableView(items, headerOrder);
			}

		} catch (BookingException e) {
			System.out.println("Something went wrong. Reason: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Something went wrong. Application is going to shut down.");
			System.exit(1);
		}
	}

	private void employeeLogin() {
		System.out.println();
		System.out.println("********************************");
		System.out.println(" Welcome to Login Page");
		System.out.println("--------------------------------");
		System.out.println(" >> Enter your User ID : ");
		int user_id = sc.nextInt();
		System.out.println(" >> Enter your Password : ");
		String password = sc.next();

		User user = new User();
		user.setUserId(user_id);
		user.setPassword(password);
		user.setRole("Employee");

		boolean isIterate = true;

		try {
			user = hotelBookingCEService.login(user);
			do {
				System.out.println();
				System.out.println("=========================================");
				System.out.println("Welcome " + user.getUserName());
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

				System.out.println(" >> Please select an option to continue ");
				System.out.println(" >> Press 1 to Check Available hotels");
				System.out.println(" >> Press 2 to Search for hotel details");
				System.out.println(" >> Press 3 to Book hotel rooms");
				System.out.println(" >> Press 4 to View Booking Status");
				System.out.println(" << Press 5 to Logout");
				System.out.println(" << Press 0 to Logout and Exit");

				System.out.println("=========================================");

				System.out.println("Please enter your choice : ");
				int ch = sc.nextInt();

				switch (ch) {

				case 1:
					checkAvailableHotels();
					break;
				case 2:
					searchHotelRoom(user);
					break;

				case 3:
					bookHotelRoom(user);
					break;

				case 4:
					viewBookingStatus(user);
					break;

				case 5:
					isIterate = false;
					user = null;
					break;
				case 0:
					System.out.println("Thank you for visiting our application. Have a good day. Bye");
					System.exit(0);
					break;

				default:
					System.out.println("Wrong Choice. Please enter from the above given options only");
				}
			} while (isIterate);
		} catch (LoginException e) {
			System.out.println("Cannot Login. Reason: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Invalid credentials");
		}
	}

	private void customerRegister() {

		String username = null;
		String password = null;
		String mobileNo = null;
		String phoneNo = null;
		String address = null;
		String email = null;

		System.out.println("Customer Register Yourself");
		sc.nextLine();
		do {
			System.out.println("Enter your name (Username should be of minimum 5 and maximum 20 characters): ");
			username = sc.nextLine();

			if (Validation.isValidName(username))
				break;

			System.out.println(
					"Username should be minimum 5 character and max 20 character in length.\n You are name is: "
							+ username.length() + " character long.");
		} while (true);

		do {
			System.out.println("Enter Password(min 4 and max 8 character long) : ");
			password = sc.next();

			if (Validation.isValidPassword(password))
				break;

			System.out.println("Password has to be minimum of 4 characters and maximum 8");
		} while (true);

		do {
			System.out.println("Enter Mobile Number : ");
			mobileNo = sc.next();

			if (Validation.isValidMobileNo(mobileNo))
				break;
			System.out.println("Mobile no should be start with 7,8 or 9 and 10 digits long");
		} while (true);
		do {
			System.out.println("Enter Phone Number : ");
			phoneNo = sc.next();

			if (Validation.isValidPhone(phoneNo))
				break;
			System.out.println("PhoneNo should start with 2,3 or 4 and 8 digits long");
		} while (true);
		sc.nextLine();
		do {
			System.out.println("Enter Address : ");
			address = sc.nextLine();

			if (Validation.isValidAddress(address))
				break;

			System.out.println("Address should be 4 to 49 character long. \n Your address is: " + address.length()
					+ "character long");
		} while (true);
		do {
			System.out.println("Enter Email Id : ");
			email = sc.next();

			if (Validation.isValidEmail(email))
				break;
			System.out.println("Enter appropriate emailId");
		} while (true);
		String role = "Customer";

		User user = new User(password, role, username, mobileNo, phoneNo, address, email);

		try {

			user = hotelBookingCEService.registerUser(user);

			System.out.println("You are successfully registered. You user Id is " + user.getUserId());

			System.out.println("Please Login to continue. Press 1 to Login");
			int choice = sc.nextInt();
			if (choice == 1) {
				customerLogin();
			} else {
				System.out.println("Thank You for visiting.");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println("Cannot register. Reason: " + e.getMessage());
		}

	}

	private void searchHotelRoom(User user) {
		sc.nextLine();
		System.out.println("Enter Hotel Name to search for : ");
		String hotelName = sc.nextLine();

		try {
			List<Hotel> hotels = hotelBookingCEService.searchHotels(hotelName);

			Iterator<Hotel> it = hotels.iterator();

			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

			while (it.hasNext()) {
				Hotel hotel = it.next();

				Map<String, Object> hotelDetail = new HashMap<String, Object>();

				hotelDetail.put("Hotel Id", hotel.getHotelId());
				hotelDetail.put("Hotel Name", hotel.getHotelName());
				hotelDetail.put("Hotel Address", hotel.getAddress());
				hotelDetail.put("City", hotel.getCity());
				hotelDetail.put("Description", hotel.getDescription());
				hotelDetail.put("Average Rate", hotel.getAvgRatePerNight());
				hotelDetail.put("Mobile No", hotel.getPhoneNo1());
				hotelDetail.put("Phone No", hotel.getPhoneNo2());
				hotelDetail.put("Rating", hotel.getRating());
				hotelDetail.put("Email", hotel.getEmail());
				hotelDetail.put("Fax", hotel.getFax());

				items.add(hotelDetail);
			}

			List<String> headerOrder = new ArrayList<String>();

			headerOrder.add("Hotel Id");
			headerOrder.add("Hotel Name");
			headerOrder.add("Hotel Address");
			headerOrder.add("City");
			headerOrder.add("Description");
			headerOrder.add("Average Rate");
			headerOrder.add("Mobile No");
			headerOrder.add("Phone No");
			headerOrder.add("Rating");
			headerOrder.add("Email");
			headerOrder.add("Fax");

			if (!hotels.isEmpty()) {
				ViewDesigner.tableView(items, headerOrder);
			}

		} catch (Exception e) {
			System.out.println("Cannot search for hotels. Reason: " + e.getMessage());
		}
	}

	private void bookHotelRoom(User user) {

		boolean isValidChoice = false;

		System.out.println();
		System.out.println("============================================");
		System.out.println("Welcome " + user.getUserName());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Book A Hotel Room: ");
		String hotelId = null;
		int bookingId = 0;

		do {
			System.out.println("Enter hotel id: ");
			hotelId = sc.next();

			if (Validation.isValidIds(hotelId))
				break;
			System.out.println("Enter Hotel Id with 1 to 4 digits long.");
		} while (true);

		List<RoomDetail> rooms = null;
		try {
			rooms = hotelBookingCEService.getAllRooms(Integer.valueOf(hotelId));

			System.out.println();
			System.out.println("List of all rooms:");
			System.out.println("------------------------------------------------------------");
			System.out.println("Room no. \t Room Type \t\t Rate Per Night ");
			System.out.println("------------------------------------------------------------");
			for (RoomDetail room : rooms) {

				if (room.getAvailablity().equals("True")) {
					System.out
							.println(room.getRoomNo() + "\t\t" + room.getRoomType() + "\t\t" + room.getPerNightRate());
				}
			}

			System.out.println("------------------------------------------------------------");

			System.out.println("Press 0 to check other hotel rooms:");
			System.out.println("Press -1 to go back:");

			System.out.println("Enter a room no to book: ");

			String choice = sc.next();

			isValidChoice = false;

			if (choice.equalsIgnoreCase("0")) {
				bookHotelRoom(user);
			} else if (choice.equalsIgnoreCase("-1")) {

			} else {
				for (RoomDetail room : rooms) {
					if (room.getRoomNo().equalsIgnoreCase(choice)) {
						bookingId = bookARoom(user, room);
						isValidChoice = true;
						break;
					}
				}

				if (isValidChoice == false) {
					System.out.println("You have entered wrong room no. Please try again.");
					bookHotelRoom(user);
				}
			}

			if (isValidChoice == true) {
				System.out.println("Your Booking is successful. Booking Id: " + bookingId);
			}
		} catch (BookingException e) {
			System.out.println("Booking Unsuccessful. Reason: " + e.getMessage());
		}

	}

	private int bookARoom(User user, RoomDetail room) throws BookingException {

		int bookingId = 0;
		Date bookedFrom = null;
		Date bookedTo = null;
		BookingDetail bookingDetail = null;

		int noOfAdults = 0;
		int noOfChildren = 0;

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		do {
			System.out.println("Booked From Date(dd/MM/yyy) : ");
			String d1 = sc.next();

			LocalDate lDate = null;

			try {
				lDate = LocalDate.parse(d1, format);

				bookedFrom = java.sql.Date.valueOf(lDate);

				if (Validation.isValidDate(bookedFrom))
					break;

				System.out.println("Date cannot be before current date. Please Enter Booked From Date Again.");
			} catch (Exception e) {
				System.out.println("Please enter date dd/MM/yyyy format e.g. 12/12/2012.");
			}

		} while (true);

		do {
			System.out.println("Booked To Date(dd/MM/yyyy) : ");
			String d1 = sc.next();

			LocalDate lDate = null;

			try {
				lDate = LocalDate.parse(d1, format);
				bookedTo = java.sql.Date.valueOf(lDate);

				if (Validation.isValidDate(bookedFrom, bookedTo))
					break;

				System.out.println("Booked To Date should be greater than Booked From Date.");
			} catch (Exception e) {
				System.out.println("Please enter date dd/MM/yyyy format e.g. 12/12/2012.");
			}

		} while (true);

		do {
			System.out.println("Enter no of adults: ");
			noOfAdults = sc.nextInt();

			if (Validation.isValidNoOfPerson(noOfAdults))
				break;

			System.out.println("No of adults should be less than 5 and greater than 0 ");
		} while (true);

		do {
			System.out.println("Enter no of children: ");
			noOfChildren = sc.nextInt();

			if (Validation.isValidNoOfChild(noOfChildren))
				break;

			System.out.println("No of children should be less than 5 and greater than 0");
		} while (true);

		bookingDetail = new BookingDetail();

		bookingDetail.setUserId(user.getUserId());
		bookingDetail.setRoomId(room.getRoomId());
		bookingDetail.setBookedFrom(bookedFrom);
		bookingDetail.setBookedTo(bookedTo);
		bookingDetail.setNoOfAdults(noOfAdults);
		bookingDetail.setNoOfChildren(noOfChildren);

		long duration = 0;

		try {
			duration = getDuration(bookedFrom, bookedTo);
		} catch (Exception e) {

		}

		bookingDetail.setAmount((int) duration * room.getPerNightRate());

		bookingId = hotelBookingCEService.bookAHotel(bookingDetail);

		return bookingId;

	}

	private long getDuration(Date bookedFrom, Date bookedTo) {

		long diff = bookedTo.getTime() - bookedFrom.getTime();

		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	private void viewBookingStatus(User user) {

		System.out.println();
		System.out.println("=============================================");
		System.out.println("Welcome " + user.getUserName());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Check Booking Status:");
		String bookingId = null;
		do {
			System.out.println("Enter your Booking Id : ");
			bookingId = sc.next();

			if (Validation.isValidIds(bookingId))
				break;
			System.out.println("Booking Id should be of 1 to 4 digits.");
		} while (true);

		try {
			BookingDetail bookingDetail = hotelBookingCEService.viewBookingStatus(Integer.valueOf(bookingId));

			RoomDetail roomDetail = hotelBookingCEService.getARoom(bookingDetail.getRoomId());

			Hotel hotel = hotelBookingCEService.getAHotel(roomDetail.getHotelId());

			String bookingStatus = "";

			bookingStatus = "\n" + "Your Booking Details are : " + "\n" + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
					+ "\n" + "Booking ID : " + bookingDetail.getBookingId() + "\n" + "Booked By : " + user.getUserName()
					+ "(" + user.getUserId() + ")" + "\n" + "Hotel Name : " + hotel.getHotelName() + "\n" + "Room No. :"
					+ roomDetail.getRoomNo() + "\n" + "Room Type.:" + roomDetail.getRoomType() + "\n" + "Booked From : "
					+ bookingDetail.getBookedFrom() + "\n" + "Booked To : " + bookingDetail.getBookedTo() + "\n"
					+ "No of Adults : " + bookingDetail.getNoOfAdults() + "\n" + "No of Children : "
					+ bookingDetail.getNoOfChildren() + "\n" + "Amount : " + bookingDetail.getAmount() + "\n"
					+ "---------------------------------------" + "\n" + "Hotel Details:" + "\n" + "Hotel Name : "
					+ hotel.getHotelName() + "\n" + "Hotel Phone no. : " + hotel.getPhoneNo1() + "\n" + "Hotel Email : "
					+ hotel.getEmail() + "\n" + "Hotel Address : " + hotel.getAddress() + "\n"
					+ "******* Cheap Stay Hotel Booking *******" + "\n";

			System.out.println(bookingStatus);

			System.out.println("Do you want to get a email copy of it?(yes/no)");
			String choice = sc.next();

			if (choice.equalsIgnoreCase("yes")) {
				hotelBookingCEService.emailBookingStatus(user, bookingStatus);
				System.out.println("Email Sent Successfully.");
			}

		} catch (Exception e) {
			System.out.println("Processing Failed. Reason: " + e.getMessage());
		}
	}

	private void hotelEmployeeActivites() {
		System.out.println();
		System.out.println("**********************************");
		System.out.println("Welcome to Employee Portal");
		System.out.println("----------------------------------");
		System.out.println(" >> Press 1 to Register");
		System.out.println(" >> Press 2 to login");
		System.out.println("Please enter your choice : ");
		int choice = sc.nextInt();

		switch (choice) {
		case 1:
			employeeRegistration();
			break;

		case 2:
			employeeLogin();
			break;

		default:
			System.out.println("Wrong Choice !");
		}
	}

	private void employeeRegistration() {
		String username;
		String password;
		String mobileNo;
		String phoneNo;
		String address;
		String email;
		System.out.println();
		System.out.println("Employee Register Yourself");
		do {
			sc.nextLine();
			System.out.println("Enter your name(min 5 and max 20 character long) : ");
			username = sc.nextLine();

			if (Validation.isValidName(username))
				break;
			System.out.println("User name should be between 5 to 20 characters");
		} while (true);
		do {
			System.out.println("Enter Password(min 4 and max 8 character long) : ");
			password = sc.next();

			if (Validation.isValidPassword(password))
				break;
			System.out.println("Password has to be minimum of 4 characters and maximum 8");
		} while (true);
		do {
			System.out.println("Enter Mobile Number : ");
			mobileNo = sc.next();

			if (Validation.isValidMobileNo(mobileNo))
				break;
			System.out.println("Mobile no should start with 7,8 or 9 and of 10 digits.");
		} while (true);
		do {
			System.out.println("Enter Phone Number : ");
			phoneNo = sc.next();

			if (Validation.isValidPhone(phoneNo))
				break;
			System.out.println("Phone no should start 2,3 or 4 and of 6 digits");
		} while (true);
		sc.nextLine();
		do {
			System.out.println("Enter Address : ");
			address = sc.nextLine();

			if (Validation.isValidAddress(address))
				break;
			System.out.println("Address should be 4 to 49 character long. \n Your address is: " + address.length()
					+ "character long");
		} while (true);
		do {
			System.out.println("Enter Email Id : ");
			email = sc.next();
			if (Validation.isValidEmail(email))
				break;
			System.out.println("Enter appropriate email Id");
		} while (true);

		String role = "Employee";

		User user = new User(password, role, username, mobileNo, phoneNo, address, email);

		try {

			user = hotelBookingCEService.registerUser(user);

			System.out.println("You are successfully registered. You user Id is " + user.getUserId());

			System.out.println("Please Login to continue. Press 1 to Login");
			int choice = sc.nextInt();
			if (choice == 1) {
				employeeLogin();
			} else {
				System.out.println("Thank You for visiting.");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.println("Cannot register. Reason is " + e.getMessage());
		}

	}

	private void adminActivites() {
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("\tWelcome to Admin Portal");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		adminLogin();

	}

	private void adminLogin() {
		System.out.println("Welcome to Login Page");
		System.out.println("--------------------------------");
		System.out.println("Enter your User ID : ");
		int user_id = sc.nextInt();
		System.out.println("Enter your Password : ");
		String password = sc.next();

		User user = new User();
		user.setUserId(user_id);
		user.setPassword(password);
		user.setRole("Admin");

		boolean isIterate = true;

		try {
			user = hotelBookingAdminService.login(user);

			do {

				System.out.println();
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("\tWelcome " + user.getUserName());
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("Please select an option to continue: ");
				System.out.println("Press 1 to Perform Hotel Management: ");
				System.out.println("Press 2 to Perform Room Management: ");
				System.out.println("Press 3 to Generate Reports: ");
				System.out.println("Press 4 to logout: ");

				System.out.println("Please enter your choice : ");
				int ch = sc.nextInt();

				switch (ch) {

				case 1:
					hotelManagementActivities();
					break;

				case 2:
					roomManagementActivities();
					break;

				case 3:
					generateReports();
					break;
				case 4:
					isIterate = false;
					break;
				default:
					System.out.println("Wrong Choice. Please enter from the above given options only");
				}

			} while (isIterate);
		} catch (LoginException e) {
			e.printStackTrace();
			System.out.println("Cannot Login. Reason is " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid credentials");
		}
	}

	private void generateReports() {

		boolean isIterate = true;
		do {
			System.out.println();
			System.out.println("================== Select Type Of Report =====================");
			System.out.println(" >> Press 1. to generate list of hotels: ");
			System.out.println(" >> Press 2. to view bookings of specific hotel: ");
			System.out.println(" >> Press 3. to view guest list of specific hotel: ");
			System.out.println(" >> Press 4. to view booking of specified date: ");
			System.out.println(" << Press 5. to go back: ");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Enter your choice: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				generateHotelList();
				break;
			case 2:
				bookingsOfSpecifiedHotel();
				break;
			case 3:
				guestListOfSpecifiedHotel();
				break;
			case 4:
				bookingsOfSpecifiedDate();
				break;
			case 5:
				isIterate = false;
				break;
			default:
				System.out.println("Invalid Option. Please Select from the option given only.");
			}
		} while (isIterate);
	}

	private void bookingsOfSpecifiedDate() {
		Date bookingDate = null;

		List<BookingDetail> bookings = null;

		System.out.println();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		do {
			System.out.println("Enter booking date(dd/MM/yyy) : ");
			String d1 = sc.next();

			LocalDate lDate = null;

			try {
				lDate = LocalDate.parse(d1, format);

				bookingDate = java.sql.Date.valueOf(lDate);

				break;
			} catch (Exception e) {
				System.out.println("Please enter date dd/MM/yyyy format e.g. 12/12/2012.");
			}

		} while (true);

		try {
			bookings = hotelBookingAdminService.bookingOfSpecifiedDate(bookingDate);

			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			List<String> headerOrder = new ArrayList<String>();

			Iterator<BookingDetail> it = bookings.iterator();

			while (it.hasNext()) {
				BookingDetail bookingDetail = it.next();

				RoomDetail room = hotelBookingAdminService.getARoom(bookingDetail.getRoomId());

				Hotel hotel = hotelBookingAdminService.getAHotel(room.getHotelId());

				User user = hotelBookingAdminService.getAUser(bookingDetail.getUserId());

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("Booking Id", bookingDetail.getBookingId());
				map.put("Booked From ", bookingDetail.getBookedFrom());
				map.put("Booked To", bookingDetail.getBookedTo());
				map.put("No of Adults", bookingDetail.getNoOfAdults());
				map.put("No of Children", bookingDetail.getNoOfChildren());
				map.put("Amount", bookingDetail.getAmount());

				map.put("Hotel Name", hotel.getHotelName());
				map.put("Hotel Phone No.", hotel.getPhoneNo1());

				map.put("Room No.", room.getRoomNo());
				map.put("Room Type", room.getRoomType());

				map.put("Booked By", user.getUserName());
				map.put("Mobile No.", user.getMobileNo());
				map.put("Email", user.getEmail());
				map.put("Address", user.getAddress());
				map.put("Role", user.getRole());

				items.add(map);

			}

			headerOrder.add("Booking Id");
			headerOrder.add("Booked From ");
			headerOrder.add("Booked To");
			headerOrder.add("No of Adults");
			headerOrder.add("No of Children");
			headerOrder.add("Amount");
			headerOrder.add("Hotel Name");
			headerOrder.add("Hotel Phone No.");
			headerOrder.add("Room No.");
			headerOrder.add("Room Type");
			headerOrder.add("Booked By");
			headerOrder.add("Mobile No.");
			headerOrder.add("Email");
			headerOrder.add("Address");
			headerOrder.add("Role");

			ViewDesigner.tableView(items, headerOrder);

		} catch (HotelManagementException e) {
			System.out.println("Something went wrong. Reason: " + e.getMessage());
		}

	}

	private void guestListOfSpecifiedHotel() {
		String hotelId = null;
		do {
			System.out.println();
			System.out.println("Enter hotel id to get guest details: ");
			hotelId = sc.next();
			if (Validation.isValidIds(hotelId))
				break;
			System.out.println("Hotel Id should be of 1 to 4 digit");
		} while (true);

		List<User> users = null;
		List<RoomDetail> roomDetails = null;

		try {
			users = hotelBookingAdminService.guestListOfSpecifiedHotel(Integer.valueOf(hotelId));
			roomDetails = hotelBookingAdminService.guestRoomDetailOfSpecifiedHotel(Integer.valueOf(hotelId));

			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

			List<String> headerOrder = new ArrayList<String>();

			Iterator<User> userIterator = users.iterator();
			Iterator<RoomDetail> roomDetailIterator = roomDetails.iterator();

			while (userIterator.hasNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				User user = userIterator.next();
				RoomDetail roomDetail = roomDetailIterator.next();

				map.put("User Id", user.getUserId());
				map.put("Name", user.getUserName());
				map.put("Role", user.getRole());
				map.put("Mobile No", user.getMobileNo());
				map.put("Phone No.", user.getPhone());
				map.put("Address", user.getAddress());
				map.put("Email", user.getEmail());

				map.put("Room No.", roomDetail.getRoomNo());
				map.put("Room Type", roomDetail.getRoomType());

				items.add(map);
			}

			headerOrder.add("User Id");
			headerOrder.add("Name");
			headerOrder.add("Role");
			headerOrder.add("Mobile No");
			headerOrder.add("Phone No.");
			headerOrder.add("Address");
			headerOrder.add("Email");
			headerOrder.add("Room No.");
			headerOrder.add("Room Type");

			ViewDesigner.tableView(items, headerOrder);

		} catch (HotelManagementException e) {
			System.out.println("Something went wrong. Reason: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Something went wrong. Application is shutting down.");
			System.exit(1);
		}
	}

	private void bookingsOfSpecifiedHotel() {
		String hotelId = null;
		do {
			System.out.println();
			System.out.println("Enter hotel id to get bookings: ");
			hotelId = sc.next();

			if (Validation.isValidIds(hotelId))
				break;
			System.out.println("Hotel id should be of 1 to 4 digit");
		} while (true);

		List<BookingDetail> bookings = null;
		try {
			bookings = hotelBookingAdminService.bookingsOfSpecifiedHotel(Integer.valueOf(hotelId));

			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

			List<String> headerOrder = new ArrayList<String>();

			for (BookingDetail bookingDetail : bookings) {
				Map<String, Object> map = new HashMap<>();
				User user = hotelBookingAdminService.getAUser(bookingDetail.getUserId());
				RoomDetail roomDetail = hotelBookingAdminService.getARoom(bookingDetail.getRoomId());

				map.put("Booking Id", bookingDetail.getBookingId());
				map.put("Booked From", bookingDetail.getBookedFrom());
				map.put("Booked To", bookingDetail.getBookedTo());
				map.put("No of Adults", bookingDetail.getNoOfAdults());
				map.put("No of Children", bookingDetail.getNoOfChildren());
				map.put("Amount", bookingDetail.getAmount());

				map.put("Room No", roomDetail.getRoomNo());
				map.put("Room Type", roomDetail.getRoomType());

				map.put("Booked By", user.getUserName());
				map.put("Mobile No", user.getMobileNo());
				map.put("Email", user.getEmail());
				map.put("Address", user.getAddress());
				map.put("Role", user.getRole());

				items.add(map);
			}

			headerOrder.add("Booking Id");
			headerOrder.add("Booked From");
			headerOrder.add("Booked To");
			headerOrder.add("No of Adults");
			headerOrder.add("No of Children");
			headerOrder.add("Amount");
			headerOrder.add("Room No");
			headerOrder.add("Room Type");
			headerOrder.add("Booked By");
			headerOrder.add("Mobile No");
			headerOrder.add("Email");
			headerOrder.add("Address");
			headerOrder.add("Role");

			ViewDesigner.tableView(items, headerOrder);

		} catch (HotelManagementException e) {
			System.out.println("Something went wrong. Reason: " + e.getMessage());
		}
	}

	private void generateHotelList() {
		System.out.println("Hotel list: ");

		List<Hotel> hotels = null;
		try {
			hotels = hotelBookingAdminService.getHotelList();

			Iterator<Hotel> it = hotels.iterator();

			List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

			while (it.hasNext()) {
				Hotel hotel = it.next();

				Map<String, Object> hotelDetail = new HashMap<String, Object>();

				hotelDetail.put("Hotel Id", hotel.getHotelId());
				hotelDetail.put("Hotel Name", hotel.getHotelName());
				hotelDetail.put("Hotel Address", hotel.getAddress());
				hotelDetail.put("City", hotel.getCity());
				hotelDetail.put("Description", hotel.getDescription());
				hotelDetail.put("Average Rate", hotel.getAvgRatePerNight());
				hotelDetail.put("Mobile No", hotel.getPhoneNo1());
				hotelDetail.put("Phone No", hotel.getPhoneNo2());
				hotelDetail.put("Rating", hotel.getRating());
				hotelDetail.put("Email", hotel.getEmail());
				hotelDetail.put("Fax", hotel.getFax());

				items.add(hotelDetail);
			}

			List<String> headerOrder = new ArrayList<String>();

			headerOrder.add("Hotel Id");
			headerOrder.add("Hotel Name");
			headerOrder.add("Hotel Address");
			headerOrder.add("City");
			headerOrder.add("Description");
			headerOrder.add("Average Rate");
			headerOrder.add("Mobile No");
			headerOrder.add("Phone No");
			headerOrder.add("Rating");
			headerOrder.add("Email");
			headerOrder.add("Fax");

			if (!hotels.isEmpty()) {
				ViewDesigner.tableView(items, headerOrder);
			}

		} catch (HotelManagementException e) {
			System.out.println("Something went wrong. Reason: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong. Application is going to shut down.");
			System.exit(1);
		}
	}

	private void roomManagementActivities() {
		System.out.println();
		System.out.println("Perform Room Management");
		System.out.println("---------------------------------------");
		System.out.println(" >> Press 1 to add room information");
		System.out.println(" >> Press 2 to delete room information");
		System.out.println(" >> Press 3 to modify room information");
		System.out.println(" << Press 9 to exit");

		int ch = sc.nextInt();

		switch (ch) {
		case 1:
			addRoomInfo();

			break;
		case 2:
			deleteRoomInfo();

			break;

		case 3:
			modifyRoomInfo();

			break;

		case 9:
			System.out.println("Thank you for visiting our application. Have a good day. Bye");
			System.exit(0);
			break;

		default:
			System.out.println("Wrong Choice. Please enter from the above given options only");

			break;
		}
	}

	private void addRoomInfo() {

		String hotelId;
		String roomNo;
		double perNightRate;
		int roomType;
		System.out.println("Add Room Details");

		do {
			System.out.println("Enter Hotel ID");
			hotelId = sc.next();

			if (Validation.isValidIds(hotelId))
				break;
			System.out.println("Room id should be of 1 to 4 digit.");
		} while (true);

		do {
			System.out.println("Enter room Number");
			roomNo = sc.next();

			if (Validation.isValidRoomNo(roomNo))
				break;
			System.out.println("Room No should be of 1 to 5 digit.");
		} while (true);

		do {
			System.out.println("Room Type:");
			System.out.println("1. Standard_NON_AC: ");
			System.out.println("2. Standard_AC:");
			System.out.println("3. Executive_AC:");
			System.out.println("4. Deluxe_AC:");

			System.out.println("Enter Room Type:");
			roomType = sc.nextInt();

			if (roomType <= 5 && roomType >= 1)
				break;

			System.out.println("Please enter valid room type.");
		} while (true);

		do {
			System.out.println("Enter Per Night Rate:");
			perNightRate = sc.nextDouble();

			if (Validation.isValidRatePerNight(perNightRate))
				break;
		} while (true);

		System.out.println("Enter Room Availability (yes/no):");
		String availablity = sc.next();

		RoomDetail roomDetail = new RoomDetail();

		roomDetail.setHotelId(Integer.valueOf(hotelId));
		roomDetail.setRoomNo(roomNo);
		roomDetail.setRoomType(roomTypeIntToEnum(roomType));
		roomDetail.setPerNightRate(perNightRate);
		roomDetail.setAvailablity(availablity.equalsIgnoreCase("yes") ? "True" : "False");

		try {

			String roomId = hotelBookingAdminService.addARoom(roomDetail);

			System.out.println("Room is added. Room Id is " + roomId);
		} catch (Exception e) {

			System.out.println("Cannot add Room to Database Reason: " + e.getMessage());
		}

	}

	private RoomType roomTypeIntToEnum(int roomType) {
		RoomType tempRoomType = null;
		switch (roomType) {
		case 1:
			tempRoomType = RoomType.Standard_NON_AC;
			break;
		case 2:
			tempRoomType = RoomType.Standard_AC;
			break;
		case 3:
			tempRoomType = RoomType.Executive_AC;
			break;
		case 4:
			tempRoomType = RoomType.Deluxe_AC;
			break;
		}
		return tempRoomType;
	}

	private void deleteRoomInfo() {
		String roomId = null;
		do {
			System.out.println("Enter Room ID");
			roomId = sc.next();
			if (Validation.isValidIds(roomId))
				break;
			System.out.println("Room Id should be of 1 to 4 digits");
		} while (true);

		try {
			hotelBookingAdminService.deleteARoom(Integer.valueOf(roomId));

			System.out.println("Room Deleted");
		} catch (Exception e) {
			System.out.println("Cannot delete Room Reson: " + e.getMessage());

		}

	}

	private void modifyRoomInfo() {
		String roomId = null;
		do {
			System.out.println("Enter ID of Room to Update Room Details: ");
			roomId = sc.next();

			if (Validation.isValidIds(roomId))
				break;
			System.out.println("RoomId should be of 1 to 4 digit.");
		} while (true);

		try {

			RoomDetail roomDetail = hotelBookingAdminService.getARoom(Integer.valueOf(roomId));

			System.out.println();

			System.out.println("Old Room Rate: " + roomDetail.getPerNightRate());
			System.out.println("Do you want to update (yes/no): ");
			String reply = sc.next();

			if (reply != null && reply.trim().equalsIgnoreCase("yes")) {
				double newRate = 0l;
				do {
					System.out.println("Enter new Room Rate: ");
					newRate = sc.nextDouble();
					if (Validation.isValidRatePerNight(newRate))
						break;
					System.out.println("Rate should be upto max 3 decimal points.");
				} while (true);

				roomDetail.setPerNightRate(newRate);
			}

			System.out.println();

			System.out.println("Old Room Availability: " + roomDetail.getAvailablity());
			System.out.println("Do you want to update (yes/no): ");
			reply = sc.next();

			if (reply != null && reply.trim().equalsIgnoreCase("yes")) {
				System.out.println("Enter new Availability: ");
				String avail = sc.next();

				roomDetail.setAvailablity(avail.equalsIgnoreCase("yes") ? "True" : "False");
			}

			hotelBookingAdminService.modifyRoom(roomDetail);

		} catch (HotelManagementException e) {

			System.out.println("Something went wrong while trying to update room details. Reason: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(
					"Something went wrong. Contact Developer. Shutting down Application. Reason: " + e.getMessage());
			System.exit(1);
		}

	}

	private void hotelManagementActivities() {
		System.out.println();
		System.out.println("Perform Hotel Management");
		System.out.println("--------------------------------------");
		System.out.println(" >> Press 1 to add hotel information");
		System.out.println(" >> Press 2 to delete hotel information");
		System.out.println(" >> Press 3 to modify hotel information");
		System.out.println(" << Press 9 to exit");

		int ch = sc.nextInt();

		switch (ch) {
		case 1:
			addHotelInfo();

			break;
		case 2:
			deleteHotelInfo();

			break;

		case 3:
			modifyHotelInfo();

			break;

		case 9:
			System.out.println("Thank you for visiting our application. Have a good day. Bye");
			System.exit(0);
			break;

		default:
			System.out.println("Wrong Choice. Please enter from the above given options only");
		}
	}

	private void addHotelInfo() {

		String hotelName;
		String hotelCity;
		String hotelDesc;
		double averageRate;
		String phone1;
		String phone2;
		String rating;
		String hotelEmail;
		String fax;

		System.out.println("Add Hotel Details");
		sc.nextLine();
		do {
			System.out.println("Enter Hotel Name");
			hotelName = sc.nextLine();
			if (Validation.isValidHotelName(hotelName))
				break;
			System.out.println("Hotel Name should be of 5 to 30 characters");
		} while (true);

		System.out.println("Enter Hotel Address");
		String hotelAddress = sc.nextLine();

		do {
			System.out.println("Enter Hotel City");
			hotelCity = sc.next();
			if (Validation.isValidCity(hotelCity))
				break;
			System.out.println("City should be of 3 to 10 characters");
		} while (true);

		sc.nextLine();
		do {
			System.out.println("Enter Hotel Description");
			hotelDesc = sc.nextLine();
			if (Validation.isValidDescription(hotelDesc))
				break;
			System.out
					.println("Description should be between 10 to 50 charcaters.You can use digits also to describe.");
		} while (true);

		do {
			System.out.println("Enter Average rate per Night");
			averageRate = sc.nextDouble();
			if (Validation.isValidRatePerNight(averageRate))
				break;
			System.out.println("Enter upto 3 decimal point ");
		} while (true);

		do {
			System.out.println("Enter Hotel Mobile No");
			phone1 = sc.next();

			if (Validation.isValidMobileNo(phone1))
				break;
			System.out.println("No should start with 7,8, or 9 and of 10 digits");
		} while (true);

		do {
			System.out.println("Enter Hotel Phone No");
			phone2 = sc.next();
			if (Validation.isValidPhone(phone2))
				break;
			System.out.println("No should start with 2,3 or 4 and of 6 digits.");
		} while (true);

		sc.nextLine();
		System.out.println("Enter Hotel Rating");
		rating = sc.nextLine();

		do {
			System.out.println("Enter Hotel Email");
			hotelEmail = sc.next();

			if (Validation.isValidEmail(hotelEmail))
				break;
			System.out.println("Enter proper hotel email Id");
		} while (true);

		System.out.println("Enter Hotel FAX Details");
		fax = sc.next();
		Hotel hotel = new Hotel(hotelCity, hotelName, hotelAddress, hotelDesc, averageRate, phone1, phone2, rating,
				hotelEmail, fax);

		try {
			String HotelId = hotelBookingAdminService.addAHotel(hotel);

			System.out.println("Hotel is added. Hotel Id is " + HotelId);
		} catch (Exception e) {
			System.out.println("CAnnot add Hotel to Database Reason: " + e.getMessage());
		}

	}

	private void deleteHotelInfo() {
		String hotelId = null;

		do {
			System.out.println("Enter Hotel ID");
			hotelId = sc.next();
			if (Validation.isValidIds(hotelId))
				break;
			System.out.println("Hotel Id should be of 1 to 4 digit");
		} while (true);

		try {
			System.out.println("Hotel with id: " + hotelId + " is deleted successfully.");
		} catch (Exception e) {
			System.out.println("Cannot delete Hotel Reason: " + e.getMessage());

		}

	}

	private void modifyHotelInfo() {
		String id = null;
		do {
			System.out.println("Enter ID of Hotel to Update Hotel Details: ");
			id = sc.next();

			if (Validation.isValidIds(id))
				break;
			System.out.println("Hotel Id should be of 1 to 4 digit.");
		} while (true);

		try {

			Hotel hotel = hotelBookingAdminService.getAHotel(Integer.valueOf(id));

			Hotel updatedHotel = new Hotel();

			updatedHotel.setHotelId(hotel.getHotelId());
			updatedHotel.setHotelName(hotel.getHotelName());
			updatedHotel.setAddress(hotel.getAddress());
			updatedHotel.setCity(hotel.getCity());
			updatedHotel.setAvgRatePerNight(hotel.getAvgRatePerNight());
			updatedHotel.setDescription(hotel.getDescription());
			updatedHotel.setPhoneNo1(hotel.getPhoneNo1());
			updatedHotel.setPhoneNo2(hotel.getPhoneNo2());
			updatedHotel.setRating(hotel.getRating());
			updatedHotel.setEmail(hotel.getEmail());
			updatedHotel.setFax(hotel.getFax());

			hotel = null;

			System.out.println();

			System.out.println("Old Description: " + updatedHotel.getDescription());
			System.out.println("Do you want to update (yes/no): ");
			String reply = sc.next();

			if (reply != null && reply.trim().equalsIgnoreCase("yes")) {

				String newdesc = null;
				do {
					sc.nextLine();
					System.out.println("Enter new description: ");
					newdesc = sc.nextLine();
					if (Validation.isValidDescription(newdesc))
						break;
					System.out.println("Description should between 10 to 50 characters");
				} while (true);

				updatedHotel.setDescription(newdesc);
			}

			System.out.println();

			System.out.println("Old Average Rate: " + updatedHotel.getAvgRatePerNight());
			System.out.println("Do you want to update (yes/no): ");
			reply = sc.next();

			if (reply != null && reply.trim().equalsIgnoreCase("yes")) {
				double avrrate = 0;

				do {
					System.out.println("Enter new average rate: ");
					avrrate = sc.nextDouble();

					if (Validation.isValidRatePerNight(avrrate))
						break;
					System.out.println("Rate should be upto max 3 decimal point");
				} while (true);

				updatedHotel.setAvgRatePerNight(avrrate);
			}

			System.out.println();

			System.out.println("Old Phone number: " + updatedHotel.getPhoneNo1());
			System.out.println("Do you want to update (yes/no): ");
			reply = sc.next();

			if (reply != null && reply.trim().equalsIgnoreCase("yes")) {
				String phone = null;
				do {
					System.out.println("Enter new Phone number: ");
					phone = sc.next();

					if (Validation.isValidMobileNo(phone))
						break;
					System.out.println("No should start with 7, 8 or 9 and of 10 digits");
				} while (true);

				updatedHotel.setPhoneNo1(phone);
			}

			hotelBookingAdminService.modifyHotel(updatedHotel);

		} catch (HotelManagementException e) {

			e.printStackTrace();
			System.out.println("Something went wrong while trying to update hotel details. Reason: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(
					"Something went wrong. Contact Developer. Shutting down Application. Reason: " + e.getMessage());
			System.exit(1);
		}

	}

}
