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
		 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/orderdb", "root", "shl199809"); 
		 } 
		 catch (Exception e) 
		 {e.printStackTrace();} 
		 return con; 
		 } 
		
		
	//insertOrder method
		public String insertOrder(String productID, String productName, String buyerName, String buyerPhone,String buyerMail,String date) 
		 { 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null){
			 return "Error while connecting to the database for inserting. ";
			 } 
		 
		 String query = " insert into orders (`orderID`,`productID`,`productName`,`buyerName`,`buyerPhone`,`buyerMail`,`date`)"
		 + " values (?, ?, ?, ?, ?, ?, ?)"; 
		 
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setInt(1, 0); 
		 preparedStmt.setString(2, productID); 
		 preparedStmt.setString(3, productName); 
		 preparedStmt.setString(4, buyerName); 
		 preparedStmt.setString(5, buyerPhone);
		 preparedStmt.setString(6, buyerMail);
		 preparedStmt.setString(7, date);
		 
		// execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "Inserted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
		 output = "Error while inserting the order."; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
		
		
	//readOrder method
		public String readOrders() 
		 { 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for reading."; } 
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>ProductID</th><th>Product Name</th>" +
		 "<th>Name</th>" + 
		 "<th>Phone Number</th>" +
		 "<th>Email</th>" +
		 "<th>Date</th>" +
		 "<th>Update</th><th>Remove</th></tr>"; 
		 
		 String query = "select * from orders"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
		 String orderID = Integer.toString(rs.getInt("orderID")); 
		 String productID = rs.getString("productID"); 
		 String productName = rs.getString("productName"); 
		 String buyerName = rs.getString("buyerName");
		 String buyerPhone = rs.getString("buyerPhone");
		 String buyerMail = rs.getString("buyerMail");
		 String date = rs.getString("date");
		 // Add into the html table
		 output += "<tr><td>" + productID + "</td>"; 
		 output += "<td>" + productName + "</td>"; 
		 output += "<td>" + buyerName + "</td>"; 
		 output += "<td>" + buyerPhone + "</td>";
		 output += "<td>" + buyerMail + "</td>";
		 output += "<td>" + date + "</td>"; 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"+ "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
		 + "<input name='orderID' type='hidden' value='" + orderID 
		 + "'>" + "</form></td></tr>"; 
		 } 
		 con.close(); 
		 
		 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
		 output = "Error while reading the orders."; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
		
		
	//readOrderbyID method
		public String getOrderbyID(String id)
		{
			
			String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for reading."; } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>ProductID</th><th>Product Name</th>" +
			 "<th>Name</th>" + 
			 "<th>Phone Number</th>" +
			 "<th>Email</th>" +
			 "<th>Date</th>"+ 
			 "<th>Update</th><th>Remove</th></tr>"; 
			 
				 
				 String query = "SELECT * FROM orders where orderID=" + id; 
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query); 
				 // iterate through the rows in the result set
				 while (rs.next()) 
				 { 		 
				 String orderID = Integer.toString(rs.getInt("orderID"));
				 String productID = rs.getString("productID"); 
				 String productName = rs.getString("productName"); 
				 String buyerName = rs.getString("buyerName");
				 String buyerPhone = rs.getString("buyerPhone");
				 String buyerMail = rs.getString("buyerMail");
				 String date = rs.getString("date");
				 // Add into the html table
				 
				 output += "<tr><td>" + productID + "</td>"; 
				 output += "<td>" + productName + "</td>"; 
				 output += "<td>" + buyerName + "</td>"; 
				 output += "<td>" + buyerPhone + "</td>";
				 output += "<td>" + buyerMail + "</td>";
				 output += "<td>" + date + "</td>"; 
				 // buttons
				 
				 
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"+ "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
				 +"<input name='orderID' type='hidden' value='" + orderID
				 + "'>" + "</form></td></tr>"; 
				 } 
				 
				 con.close(); 
				 
				 output += "</table>"; 
				 } 
				 catch (Exception e) 
				 { 
				 output = "Error while reading the orders."; 
				 System.err.println(e.getMessage()); 
				 } 
			 
				 return output;  
			}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	//updateOrder method
		public String updateOrder(String ID,String productID, String productName, String buyerName, String buyerPhone,String buyerMail,String date)
		{ 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for updating."; } 
			 // create a prepared statement
			 String query = "UPDATE orders SET productID=?,productName=?,buyerName=?,buyerPhone=?,buyerMail=?,date=?  WHERE orderID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, productID); 
			 preparedStmt.setString(2, productName); 
			 preparedStmt.setString(3, buyerName); 
			 preparedStmt.setString(4, buyerPhone);
			 preparedStmt.setString(5, buyerMail);
			 preparedStmt.setString(6, date);
			 preparedStmt.setInt(7, Integer.parseInt(ID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Updated successfully"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "Error while updating the order."; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 
		
		//deleteOrder method
			public String deleteOrder(String orderID) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for deleting."; } 
			 // create a prepared statement
			 String query = "delete from orders where orderID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(orderID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Deleted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "Error while deleting the order."; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 


}
