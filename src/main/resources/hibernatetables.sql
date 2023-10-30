-- Employee table
create table EMPLOYEE (
   id INT NOT NULL auto_increment,
   first_name VARCHAR(20) default NULL,
   last_name VARCHAR(20) default NULL,
   salary INT  default NULL,
   PRIMARY KEY (id)
);

-- Certificate table
create table CERTIFICATE (
   id INT NOT NULL auto_increment,
   certificate_name VARCHAR(30) default NULL,
   employee_id INT default NULL,
   PRIMARY KEY (id)
);

-- Certificate table for ORMappingList
create table CERTIFICATE (
   id INT NOT NULL auto_increment,
   certificate_name VARCHAR(30) default NULL,
   idx INT default NULL, 
   employee_id INT default NULL,
   PRIMARY KEY (id)
);

-- Certificate table for ORMappingMap
create table CERTIFICATE (
   id INT NOT NULL auto_increment,
   certificate_type VARCHAR(40) default NULL,
   certificate_name VARCHAR(30) default NULL,
   employee_id INT default NULL,
   PRIMARY KEY (id)
);