package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class User {
	public Connection connect()
	{ 
		 Connection con = null; 
		 
		 try { 
			 Class.forName("com.mysql.jdbc.Driver"); 
			 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users_db", "root", ""); 
			 //For testing
			 System.out.print("Successfully connected"); 
		} catch(Exception e){ 
			 e.printStackTrace(); 
		} 
		return con; 
	}
	
	public Map<String, String> usersList() {
		Map<String, String> list = new HashMap<String,String>();
		try {
			Connection con = connect();
			if(con == null) {
				return null;
			}
			String query = "select * from users";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				
				list.put(username, password);			
			}
			con.close();
		}catch(Exception e) {
				System.out.println(e.getMessage());
				return null;
		}
		return list;
	}
	
	public String insertUser(String userID, String name, String nic, String email, String contact, String username, String password) 
	 {
		 String output = "";
		 try
		 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for inserting."; }
			 // create a prepared statement
			 String query = " insert into users(`userID`, `name`, `nic`, `email`, `contact`, `username`, `password`)"
			 + " values (?, ?, ?, ?, ?, ?, ?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setString(1, userID);
			 preparedStmt.setString(2, name);
			 preparedStmt.setString(3, nic);
			 preparedStmt.setString(4, email);
			 preparedStmt.setString(5, contact);
			 preparedStmt.setString(6, username);
			 preparedStmt.setString(7, password);
			// execute the statement
		
			 preparedStmt.execute();
			 con.close();
			 output = "Inserted successfully";
		 }
		 catch (Exception e)
		 {
		 output = "Error while inserting the user.";
		 System.err.println(e.getMessage());
		 }
		 return output;
	}
	

public String readUsers()
 {
	 String output = "";
	 try
	 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for reading."; }
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr>"
		 		+ "<th>User ID</th>"
				+"<th>name</th>" 
		 		+"<th>NIC</th>" 
				+"<th>Email</th>" 
		 		+"<th>Contact</th>"
				+"<th>Update</th>"
		 		+"<th>Remove</th></tr>" ;
		
		 String query = "select * from users";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String userID = rs.getString("userID");
		 String name = rs.getString("name");
		 String nic = rs.getString("nic");
		 String email = rs.getString("email");
		 String contact = rs.getString("contact");

		 
		 // Add into the html table
		 output += "<tr><td>" + userID + "</td>";
		 output += "<td>" + name + "</td>";
		 output += "<td>" + nic + "</td>";
		 output += "<td>" + email + "</td>";
		 output += "<td>" + contact + "</td>";
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update'></td>" + "<td><form method='post' action='#'>" + "<input name='btnRemove' type='submit' value='Remove'>"
		 + "<input name='userID' type='hidden' value='" + userID
		 + "'>" + "</form></td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the users.";
	 System.err.println(e.getMessage());
	 }
	 return output;
 }

public String updateUser(String ID, String name, String nic, String email, String contact, String username, String password)
{
	String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for updating."; }
		// create a prepared statement
		String query = "UPDATE users SET name=?,nic=?,email=?, contact=?,username=?, password=? WHERE userID LIKE ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, nic);
		preparedStmt.setString(3, email);
		preparedStmt.setString(4, contact);
		preparedStmt.setString(5, username);
		preparedStmt.setString(6, password);
		preparedStmt.setString(7,ID);
		
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
	}
	catch (Exception e)
	{
		output = "Error while updating the user.";
		System.err.println(e.getMessage());
	}
		return output;
	}

	public String deleteUser(String userID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			// create a prepared statement
			String query = "delete from users where userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, userID);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		}catch (Exception e) {
			return "Error Deleteing";
		}
		return output;
	}

}
