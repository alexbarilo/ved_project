#Ved project

CRUD application for accounting in foreign economic activity. It stores 
information about contractors, relevant invoices, payments and helps to 
keep track of current state of due dates and overdrafts. The app realizes 
logic of payments’ debiting according to invoices to be paid.

 - Front-end - HTML / CSS / Javascript;
 - Back-end - XML, MySQL, JDBC, Servlets, JSP, JSTL;
 - Tests - jUnit;
 - Container - Tomcat; 
 - Build tool - Maven.
 
#To clone project:

 - In command line:
```sh 
$ git clone https://github.com/alexbarilo/ved_project.git
```
 - Using IntelligIDEA:
```sh
File -> Import Project (please note that project built on standard IDEA web-application structure not Maven one)
```
 - To create database scheme and populate it (please do it manually): 
```sh
src/resources/dump.sql
```
