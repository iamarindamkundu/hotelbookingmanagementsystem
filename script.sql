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
	user_id number(6) primary key not null,
	password varchar(100),
	role varchar(10),
	user_name varchar(20),
	mobile_no varchar(10),
	phone varchar(10),
	address varchar(50),
	email varchar(30)
);

CREATE SEQUENCE userId_seq START WITH 999;

SELECT * FROM USERS;

delete from users where user_id = 3750;


--============================admin details=========================
-- admin user_id: admn
-- admin password: admin123

INSERT INTO USERS VALUES 
(
	'999',
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
	hotel_id number(4) primary key not null,
	city varchar(10) not null,
	hotel_name varchar(30) not null,
	address varchar(50) not null,
	description varchar(50),
	avg_rate_pernight number(7,2) not null,
	phone_no1 varchar(10),
	phone_no2 varchar(10),
	rating varchar(4),
	email varchar(30),
	fax varchar(15)
);

CREATE SEQUENCE hotelId_seq START WITH 20;

drop sequence hotelId_seq;

select hotelId_seq.nextVal from dual;

SELECT * FROM HOTELS;

select * from hotels where hotel_id = '30';

insert into hotels values(60, 'Kolkata', 'Kolkata Hotel', 'Kolkata', 'Best Price Best Comfort',1500.00,'9876543210','567890','3','kol123@gmail.com','345-612-323');
insert into hotels values(61, 'Bengalore', 'Hotel Maharaja', 'Bengalore', 'Comfortable Room in Reasonable price',2500.00,'9876503210','567800','4','maha123@gmail.com','375-602-323');
insert into hotels values(62, 'Digha', 'Hotel Sea Hock', 'Digha', '5% Discount in first 3 booking',1200.00,'9876543211','567880','3.8','seahock123@gmail.com','345-612-321');


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
	hotel_id number(4),
	room_id number(4) primary key not null,
	room_no varchar(5) not null,
	room_type varchar(20),
	per_night_rate number(10,3),
	availability varchar(5)
);



desc roomdetails;

drop sequence roomId_seq;

CREATE SEQUENCE roomId_seq START WITH 30;


SELECT * FROM ROOMDETAILS;


delete hotels , roomdetails from hotels inner join ROOMDETAILS where HOTELS.hotel_id = roomdetails.hotel_id and HOTELS.hotel_id='60';

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
	booking_id number(4) primary key not null,
	room_id number(4) not null,
	user_id number(6) not null,
	booked_from date,
	booked_to date,
	no_of_adults number(4),
	no_of_children number(4),
	amount number(7, 2)
);

create sequence bookingId_seq start with 40;

SELECT * FROM BOOKINGDETAILS;

delete from bookingdetails where booking_id=4050;