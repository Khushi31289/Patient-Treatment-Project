Drop table IF EXISTS sampledb.Messages;
Drop table IF EXISTS Symptom;
Drop table IF EXISTS Treatment;
Drop table IF EXISTS PatientCondition;
Drop table IF EXISTS Patients;


CREATE TABLE IF NOT EXISTS Patients (
    pid INT(3) ZEROFILL NOT NULL AUTO_INCREMENT ,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    address VARCHAR(255)NOT NULL,
    age INT DEFAULT 1 NOT NULL,
    email VARCHAR(255) NOT NULL,
    gender VARCHAR(6) NOT NULL default 'Female',
    PRIMARY KEY (pid),
    UNIQUE (email)
);
 

CREATE TABLE IF NOT EXISTS Messages (
    msgid INT NOT NULL AUTO_INCREMENT,
    msgsub VARCHAR(255),
    rcvrpid INT NOT NULL,
    senderpid INT NOT NULL,
    msgcontent VARCHAR(255),
    dttime DATETIME NOT NULL,
    PRIMARY KEY (msgid)
);


CREATE TABLE IF NOT EXISTS Symptom (
    sympid INT NOT NULL AUTO_INCREMENT,
    symname VARCHAR(255),
    pid INT NOT NULL,
    dttime DATETIME NOT NULL,
    sympstatus VARCHAR(9) NOT NULL,
    PRIMARY KEY (sympid , pid , symname),
    UNIQUE (sympid)
);


CREATE TABLE IF NOT EXISTS Treatment (
    treatid INT NOT NULL AUTO_INCREMENT,
    treatname VARCHAR(255),
    treatdescrip VARCHAR(255),
    pid INT NOT NULL,
    dttime DATETIME NOT NULL,
    PRIMARY KEY (treatid , pid)
);

CREATE TABLE IF NOT EXISTS PatientCondition (
    pconid INT NOT NULL AUTO_INCREMENT,
    pid INT NOT NULL,
    pconname VARCHAR(255),
    dttime DATETIME NOT NULL,
    pconstatus VARCHAR(9) NOT NULL,
    PRIMARY KEY (pid,pconname,pconstatus),
    UNIQUE (pconid)
);


-- Insert Queries---
--Patient Table---
insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email,gender )
values(
	'Joshi', 'Khushi', 'Detroit-USA', 20, 'kj@gmail.com', 'Female'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email,gender )
values(
	'Joshi', 'Dhwanit', 'California, USA', 15 , 'cooldj@gmail.com', 'Male'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email, gender )
values(
	'Huge', 'Jackman', 'California-USA', 43, 'hugej@gmail.com', 'Male'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email, gender )
values(
	'Duck', 'Donald', 'Detroit-USA', 28 , 'dd@gmail.com', 'Male'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email, gender )
values(
	'Bachchan', 'Amitabh', 'Mumbai-India', 60 , 'abcool@gmail.com', 'Male'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email, gender )
values(
	'M J', 'Roopa', 'Detoit-MI, USA', 20 , 'roopkirani@gmail.com', 'Female'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email, gender )
values(
	'Chopra', 'Pareeneeti', 'Mumbai, India', 30 , 'pari@gmail.com', 'Female'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email, gender )
values(
	'Hossain', 'Naima', 'South Korea,Korea', 20 , 'naimz@gmail.com', 'Female'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email, gender )
values(
	'Gandhi', 'Mahatma', 'Gujarat,India', 50 , 'gandhiji@gmail.com', 'Male'
);

insert into patients 
(lastname ,   firstname ,
    address ,
    age ,
    email, gender )
values(
	'Khan', 'Shahrukh', 'Borevalli,Mumbai-India', 50 , 'srk123@gmail.com', 'Male'
);


--Insert data into messages
insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('Hi', 1,2, 'This is about Knee ligament surgery',
    '2015-10-31 18:23:44');
    
insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('Wasup?', 1,4, 'This is about Fever and cold',
    '2015-11-1 21:23:44');

insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('How are you?', 5,9, 'How are you today?',
    '2015-10-27 10:23:44');

insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('Helloo', 1,4, 'This is about Fever and cold',
    '2015-11-1 13:23:44');

insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('Hieee', 1,4, 'This is about Fever and cold',
    '2015-10-31 9:23:44');

    insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('Hey', 1,4, 'This is about Fever and cold',
    '2015-10-31 4:23:44');
 
    insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('About health', 1,4, 'This is about Fever and cold',
    '2015-10-29 17:23:44');
    
    insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('Hello', 5,8, 'Life is now here..!',
    '2015-11-1 22:23:44');
    
    insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('Life', 2,10, 'Life is lived forwards but understood backwards',
    '2015-10-31 7:23:44');
    
    insert into Messages (
    msgsub, rcvrpid , senderpid ,
    msgcontent, dttime) 
    values('Dreams', 2,10, 'Never give up on your dreams',
    '2015-10-20 19:23:44');
    
    
--Insert data into Symptom

    insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Cold',1, '2015-10-11 13:30:00',
    'Appear' );
    
    insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Flue',5, '2015-10-11 09:30:00',
    'Appear' );

    insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Cold',1, '2015-10-21 10:00:00',
    'Disppear' );

    
	insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Fever',4, '2015-10-14 19:43:00',
    'Appear' );
    
    insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Cough',2, '2015-10-14 17:39:44',
    'Appear' );
    
    insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Nausea',1, '2015-10-11 13:30:44',
    'Appear' );
    
    
	insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Fatigue',7, '2015-10-28 09:53:00',
    'Appear' );
    
    insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Vommit',9, '2015-10-22 15:23:10',
    'Appear' );
    
	insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Fatigue',7, '2015-10-30 07:03:54',
    'Disppear' );
    
    insert into Symptom (
    symname , pid , dttime ,
    sympstatus )
    values('Depression',10, '2015-10-31 10:13:00',
    'Appear' );
    
    --Insert into Patient Condition
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(1, 'Ligament tear', '2015-10-11 20:00:00',
    'Appear' );

    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(2, 'Migrane', '2015-10-21 15:13:00',
    'Appear' );
    
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(1, 'Ligament tear', '2015-10-31 10:00:00',
    'Disappear' );
    
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(3, 'Stroke', '2015-10-05 10:00:00',
    'Appear' );
    
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(5, 'Diabetes', '2015-10-10 18:00:00',
    'Appear' );
    
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(10, 'Thyroid', '2015-10-01 22:10:00',
    'Appear' );
    
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(9, 'Blood Clot', '2015-10-17 19:00:00',
    'Appear' );
    
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(7, 'Thyroid', '2015-10-01 12:13:00',
    'Appear' );
    
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(10, 'Thyroid', '2015-10-21 02:30:00',
    'Disappear' );
    
    insert into PatientCondition(
    pid,pconname, dttime, pconstatus)
    values(5, 'Diabetes', '2015-10-24 20:00:00',
    'Disappear' );
    
    --Insert into Treatment
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Medicine','Metacin: Take twice a day', 1 , '2015-10-31 10:00:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Physical Therapy','Do exercise thrice a day for 2 weeks', 2 , '2015-10-31 20:00:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Medicine','Take daily once', 3 , '2015-10-31 22:30:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Mental Therapy','Meditation', 9 , '2015-10-31 10:00:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Medicine','Insulin: Take twice a day', 5 , '2015-10-31 10:00:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Medicine','Metacin: Take twice a day', 2 , '2015-10-31 10:00:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Medicine','Crocin: Take twice a day', 3 , '2015-10-31 10:00:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Physical Therapy','Leg exercise:daily twice', 4 , '2015-10-31 10:00:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Medicine','Metacin: Take twice a day', 7 , '2015-10-31 10:00:00');
     
     insert into Treatment( 
     treatname, treatdescrip, pid, dttime)
     values('Physical Therapy','Jogging daily 30 minutes', 9 , '2015-10-31 10:00:00');
     
     
     