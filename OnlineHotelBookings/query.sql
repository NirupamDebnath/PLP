drop sequence users_seq;
drop sequence Hotel_seq;
drop sequence RoomDetails_seq;
drop sequence BOOKINGDETAILS_seq;

drop table BookingDetails;
drop table RoomDetails;
drop table Hotel;
drop table Users;

create sequence Users_seq start with 1000;

create table Users( 
	user_id varchar(4) primary key,
	password varchar(7), 
 	role varchar(10), 
 	user_name varchar(20), 
 	mobile_no varchar(10),
	phone varchar(10), 
	address varchar(25),
	email varchar(15)
);

insert into Users values(Users_seq.nextVal,'12345','admin','tom','9985467006','9177453454','chennai','admin@gmail.com');
insert into Users values(Users_seq.nextVal,'12345','employee','jerry','9676662841','9701048244','chennai','empl@gmail.com');
insert into Users values(Users_seq.nextVal,'12345','customer','goofy','9885467005','9177453136','tirupati','cus@gmail.com');

select * from users;
delete from users;

select password from users where user_id=1002;

create sequence Hotel_seq start with 100;

create table Hotel(
hotel_id varchar(4) primary key, 
city varchar(10), 
hotel_name varchar(20), 
address varchar(25), 
description varchar(50), 
avg_rate_per_night number(9,2),
phone_no1 varchar(10), 
phone_no2 varchar(10), 
rating number(4), 
email varchar(15), 
fax varchar(15)
);

insert into Hotel values(Hotel_seq.nextVal,'chennai','raj','chengalpattu','fivestar',1500,'8866553322','9876543210',4,'rajgroup@gmail','0896325');
insert into Hotel values(Hotel_seq.nextVal,'chennai','taj','koyambedu','sevenstar',2500,'8861253389','9876541234',5,'tajgroup@gmail','0896123');
insert into Hotel values(Hotel_seq.nextVal,'banglore','abc','thambaram','threestar',1000,'9966253389','8123541234',3,'abcgroup@gmail','0812323');

delete from Hotel where hotel_id=102;

select * from Hotel;

create sequence RoomDetails_seq start with 6000;

create table RoomDetails (
hotel_id varchar(4) references Hotel(hotel_id),  
room_id varchar(4) primary key,  
room_no varchar(3) unique,
room_type varchar(30),
per_night_rate number(6,2), 
availability varchar(3) check (availability in ('yes','no')));

insert into ROOMDETAILS values (102,RoomDetails_seq.nextval,'109','Standard A/C room',2000,'yes');
insert into ROOMDETAILS values (101,RoomDetails_seq.nextval,'110','Standard non A/C room',2500,'yes');
insert into ROOMDETAILS values (100,RoomDetails_seq.nextval,'111','Standard non A/C room',3500,'yes');

insert into ROOMDETAILS values (101,RoomDetails_seq.nextval,'112','Standard A/C room',2000,'yes');
insert into ROOMDETAILS values (101,RoomDetails_seq.nextval,'113','Standard non A/C room',2500,'yes');
insert into ROOMDETAILS values (101,RoomDetails_seq.nextval,'114','Standard non A/C room',3500,'yes');
insert into ROOMDETAILS values (100,RoomDetails_seq.nextval,'115','Standard A/C room',2000,'yes');
insert into ROOMDETAILS values (100,RoomDetails_seq.nextval,'116','Standard non A/C room',2500,'yes');
insert into ROOMDETAILS values (100,RoomDetails_seq.nextval,'117','Standard non A/C room',3500,'yes');
insert into ROOMDETAILS values (102,RoomDetails_seq.nextval,'118','Standard A/C room',2000,'yes');
insert into ROOMDETAILS values (102,RoomDetails_seq.nextval,'119','Standard non A/C room',2500,'yes');
insert into ROOMDETAILS values (102,RoomDetails_seq.nextval,'120','Standard non A/C room',3500,'yes');

delete from ROOMDETAILS where room_id=6005;

select RoomDetails_seq.nextval from dual;

select * from RoomDetails;
select * from ROOMDETAILS where availability like 'yes' and room_id = 6003;

update ROOMDETAILS set availability='yes' where room_id=6005;

create sequence BookingDetails_seq start with 2000;

create table BookingDetails(
booking_id varchar(4), 
room_id varchar(4) references RoomDetails(room_id), 
user_id varchar(4) references Users(user_id), 
booked_from varchar(10),
booked_to varchar(10),
no_of_adults number(2), 
no_of_children number(2), 
amount number(12,2)
);

delete from BOOKINGDETAILS;
select booking_id,room_id,user_id,booked_from,booked_to,no_of_adults,no_of_children,amount from hotel natural join roomdetails natural join BookingDetails where hotel_id=100;

select booking_id,room_id,user_id,booked_from,booked_to,no_of_adults,no_of_children,amount,user_name from users natural join hotel natural join roomdetails natural join BookingDetails where hotel_id=100;

select room_id,user_id,user_name,mobile_no from BOOKINGDETAILS natural join users natural join ROOMDETAILS where hotel_id=100;

delete from BOOKINGDETAILS; where room_id = 6002;

select BOOKINGDETAILS_seq.nextval from dual;

insert into BOOKINGDETAILS values(BOOKINGDETAILS_seq.nextval,6007,1002,'15-aug-2013','18-aug-2013',
2,2,1200);

select * from BOOKINGDETAILS; where booking_id=2005;

select * from BOOKINGDETAILS where booked_from <= '15-aug-2013' and booked_to >= '15-aug-2013';

select BOOKINGDETAILS_seq.currval from dual;

SELECT room_id FROM hotel natural join RoomDetails natural join BOOKINGDETAILS;

select room_id from roomdetails where hotel_id=100;