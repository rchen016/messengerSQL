DROP TABLE WORK_EXPR;
DROP TABLE EDUCATIONAL_DETAILS;
DROP TABLE MESSAGE;
DROP TABLE CONNECTION_USR;
DROP TABLE USR;


CREATE TABLE USR(
	userId 	varchar(50) UNIQUE NOT NULL, 
	password varchar(50) NOT NULL,
	email text NOT NULL,
	name varchar(50),
	dateOfBirth date,
	Primary Key(userId));

CREATE TABLE WORK_EXPR(
	userId varchar(50) NOT NULL, 
	company varchar(50) NOT NULL, 
	role varchar(50) NOT NULL,
	location varchar(50),
	startDate date,
	endDate date,
	PRIMARY KEY(userId,company,role,startDate));

CREATE TABLE EDUCATIONAL_DETAILS(
	userId varchar(50) NOT NULL, 
	instituitionName varchar(500) NOT NULL, 
	major varchar(500) NOT NULL,
	degree varchar(500) NOT NULL,
	startdate date,
	enddate date,
	PRIMARY KEY(userId,major,degree));

CREATE TABLE MESSAGE(
	msgId integer UNIQUE NOT NULL, 
	senderId varchar(500) NOT NULL,
	receiverId varchar(500) NOT NULL,
	contents varchar(300) NOT NULL,
	sendTime timestamp,
	deleteStatus integer,
	status varchar(500) NOT NULL,
	PRIMARY KEY(msgId));

CREATE TABLE CONNECTION_USR(
	userId varchar(50) NOT NULL, 
	connectionId varchar(50) NOT NULL, 
	status varchar(50) NOT NULL,
	PRIMARY KEY(userId,connectionId));