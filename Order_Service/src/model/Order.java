package model;

import java.sql.*; 

public class Order {
	
	//Database connection
		private Connection connect() 
		 { 
		 Connection con = null; 
		 try
		 { 
		 Class.forName("com.mysql.jdbc.Driver"); 
		 
		 //DBServer/DBName, username, password 
		 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/orderdb", "root", "1233"); 
		 } 
		 catch (Exception e) 
		 {e.printStackTrace();} 
		 return con; 
		 } 

}
