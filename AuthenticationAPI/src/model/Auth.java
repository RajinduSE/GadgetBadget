package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Auth {
	public Connection connect()
	{ 
		 Connection con = null; 
		 
		 try { 
			 Class.forName("com.mysql.jdbc.Driver"); 
			 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/auth_db", "root", ""); 
			 //For testing
			 System.out.print("Successfully connected"); 
		} catch(Exception e){ 
			 e.printStackTrace(); 
		} 
		return con; 
	}
	
	public String login(String usersList, String username, String password) {
		String output = null;
		
		Map<String,String> map = getMap(usersList);  
		boolean status = validateCredentials(map, username, password);
		
		System.out.println(status);
		
		if(status) {
			String token = generateToken(username);
			boolean exists = checkExistingUser(username);
				
			if(exists) {
				output = updateToken(username, token);
			}else {
				output = insertToken(username, token);
			}		
			return output;
		}else {
			return "Invalid Login";
		}
	}
	
	private String insertToken(String username, String token) {
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) 
			{ 
				return "Error while connecting to the database"; 
			} 
			// create a prepared statement
			String query = "insert into auth(`username`,`token`) values(?,?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values 
			 preparedStmt.setString(1, username); 
			 preparedStmt.setString(2, token); 
				 
			//execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Successfully Logged";
		}catch (Exception e) { 
			 output = "Logging Failed"; 
			 System.err.println(e.getMessage()); 
		} 
		return output;
	}

	public String verify() {
		String output = "";
		String token = null;
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database";
			}
			
			String query = "select * from auth";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			if(rs.next()) {
				token = rs.getString("token");
				con.close();
				if(validateToken(token)) {
					return "Verified Profile";
				}else {
					return "Token Expired";
				}
			}else {
				con.close();
				return "Login First";
			}			
			
		}catch(Exception e) {
			output = "Error while reading";
			System.out.println(e.getMessage());
		}
		return output;
	}
	
	public String adminVerify() {
		String output = "";
		String token = null;
		try {
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database";
			}
			
			String query = "select * from auth";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			if(rs.next()) {
				token = rs.getString("token");
				con.close();
				if(validateToken(token)) {
					if(getUserFromToken(token).equals("admin")) {
						return "Verified Admin Profile";
					}else {
						return "Not Permitted";
					}					
				}else {
					return "Token Expired";
				}
			}else {
				con.close();
				return "Login First";
			}			
			
		}catch(Exception e) {
			output = "Error while reading";
			System.out.println(e.getMessage());
		}
		return output;
	}
	
	public String logout() {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from auth"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Logout successfully"; 
		} 
		catch (Exception e) 
		{ 
			output = "Error while login out"; 
			System.err.println(e.getMessage()); 
		} 
		return output;
	}
	
	//convert String into a map
	private Map<String, String> getMap(String list){
		list = list.substring(1, list.length()-1);         
		String[] keyValuePairs = list.split(",");              
		Map<String,String> map = new HashMap<>();               

		for(String pair : keyValuePairs)                        
		{
		    String[] entry = pair.split("=");                    
		    map.put(entry[0].trim(), entry[1].trim());          
		}
		
		return map;
	}
	
	//check username password correct or not
	private boolean validateCredentials(Map<String,String> map, String username, String password) {
		//Set<Map.Entry<String, String>> set = map.entrySet();
		for(Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println();
			System.out.println(entry.getKey() + ":"+ entry.getValue());
			System.out.println();
			if(entry.getKey().equals(username)) {
				if(entry.getValue().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String updateToken(String username, String token) {
		String output = ""; 
		try { 
			Connection con = connect(); 
			if (con == null) 
			{ 
				return "Error while connecting to the database"; 
			} 
			// create a prepared statement
			String query = "update auth set token = ? where username = ?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values 
			 preparedStmt.setString(1, token); 
			 preparedStmt.setString(2, username); 
				 
			//execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 output = "Successfully Logged";
		}catch (Exception e) { 
			 output = "Logging Failed"; 
			 System.err.println(e.getMessage()); 
		} 
		return output;
	}
	
	private String generateToken(String username) {
		Instant now = Instant.now();
		byte[] secret = Base64.getDecoder().decode("43DXvhrwMGeLLlP4P4izjgsBB2yrpo82oiUPhADakLs");
		
		String jwt = Jwts.builder()
				.setSubject(username)
				.setAudience("Auth")
     			.claim("id", new Random().nextInt(20) + 1)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
				.signWith(Keys.hmacShaKeyFor(secret))
				.compact();
		System.out.println(jwt);
		
		return jwt;
	}
	
	private String getUserFromToken(String token) {
		byte[] secret = Base64.getDecoder().decode("43DXvhrwMGeLLlP4P4izjgsBB2yrpo82oiUPhADakLs");
		String username = null;
		try {
			Jws<Claims> result = Jwts.parser()
					.setSigningKey(Keys.hmacShaKeyFor(secret))
					.parseClaimsJws(token);
			username = result.getBody().getSubject();
			System.out.println(username);
			return username;
		}catch(Exception e) {
			return null;
		}
	}
	
	private boolean validateToken(String token) {
		byte[] secret = Base64.getDecoder().decode("43DXvhrwMGeLLlP4P4izjgsBB2yrpo82oiUPhADakLs");
		try {
			Jws<Claims> result = Jwts.parser()
					.setSigningKey(Keys.hmacShaKeyFor(secret))
					.parseClaimsJws(token);
			System.out.println(result);
		}catch(Exception e) {
			return false;
		}
		return true;
		
	}
	
	private boolean checkExistingUser(String user) {
		try {
			Connection con = connect();

			String query = "select * from auth where username = " + "'" + user + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			if(rs.next()) {
				return true;
			}
			
			con.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
