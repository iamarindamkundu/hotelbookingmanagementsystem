--Table Script:

--Users: 
--user_id(varchar(4)), 
--password (varchar(7)), 
--role(varchar(10),  
--user_name(varchar (20)), 
--mobile_no(varchar(10)), 
--phone(varchar(10)), 
--address (varchar(25)), 
--email (varchar(15))

--Users table

DROP TABLE Users;

CREATE TABLE Users
(
	user_id int UNSIGNED not null AUTO_INCREMENT,
	password varchar(100),
	role varchar(10),
	user_name varchar(20),
	mobile_no varchar(10),
	phone varchar(10),
	address varchar(50),
	email varchar(30),
	primary key(user_id)
);

--CREATE SEQUENCE userId_seq START WITH 10;

SELECT * FROM USERS;


--============================admin details=========================
-- admin user_id: 99
-- admin password: admin123

INSERT INTO USERS VALUES 
(
	99,
	'3e2f8529c68bf2f1160c38af589ad342455b6c8c',
	'Admin',
	'Adga',
	'9038755320',
	'22640659',
	'34, Creek Row, Kolkata-700014',
	'arindamkundu09@gmail.com'
);
--==================================================================

--ii.	Hotel: 
--hotel_id(varchar(4)), 
--city (varchar(10)), 
--hotel_name(varchar (20)), 
--address(varchar(25)), 
--description varchar(50)), 
--avg_rate_per-night (number(m,n)), 
--phone_no1(varchar(10)), 
--phone_no2(varchar(10)), 
--rating(varchar(4)), 
--email (varchar(15)), 
--fax (varchar(15))

-- Hotel table

DROP TABLE Hotels;

CREATE TABLE Hotels
(
	hotel_id int unsigned not null auto_increment,
	city varchar(10) not null,
	hotel_name varchar(30) not null,
	address varchar(50) not null,
	description varchar(50),
	avg_rate_pernigth decimal(7,2),
	phone_no1 varchar(10),
	phone_no2 varchar(10),
	rating varchar(4),
	email varchar(30),
	fax varchar(15),
	primary key(hotel_id)
);

--CREATE SEQUENCE hotelId_seq START WITH 20;

--select hotelId_seq.nextVal from dual;

SELECT * FROM HOTELS;

--iv.	RoomDetails: 
--hotel_id(varchar(4)),  
--room_id (varchar(4)),  
--room_no(varchar(3)), 
--room_type(varchar(20)), 
--per_night_rate (number(6,2)), 
--availability (Boolean), 
--photo (blob))

DROP TABLE RoomDetails;

CREATE TABLE RoomDetails
(
	hotel_id varchar(4),
	room_id int(4) primary key not null auto_increment,
	room_no varchar(5) unique not null,
	room_type varchar(20),
	per_night_rate decimal(10,3),
	availability varchar(5)
);

--CREATE SEQUENCE roomId_seq START WITH 30;


SELECT * FROM ROOMDETAILS;

--vi.	BookingDetails: 
--booking_id(varchar(4)), 
--room_id(varchar(4)),  
--user_id(varchar(4)), 
--booked_from (date), 
--booked_to(date), 
--no_of_adults, 
--no_of_children, 
--amount(number(6,2))

DROP TABLE BookingDetails;

CREATE TABLE BookingDetails
(
	booking_id int(4) primary key not null auto_increment,
	room_id varchar(4) not null,
	user_id varchar(4) not null,
	booked_from date,
	booked_to date,
	no_of_adults int(4),
	no_of_children int(4),
	amount double(7, 2)
);

SELECT * FROM BOOKINGDETAILS;


-- Report generation query
--select 
select * from bookingdetails where room_id = 
(select room_id from roomdetails where hotel_id = 1);


select * from users where user_id =  
(select user_id from bookingdetails where room_id = 
(select room_id from roomdetails where hotel_id = 1));