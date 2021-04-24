package com;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.User;

@Path("/Users") 
public class UserService {
	//private static final UserService Obj = null;
	//private static final String userData = null;
	//private static final String Jsoup = null;
	User user = new User();

	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String usersList(){ 
		Map<String, String> map = user.usersList(); 
		return map.toString();
	 } 
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertUser(@FormParam("userId") String userId,
							 @FormParam("name") String name,
							 @FormParam("userNIC") String userNIC,
							 @FormParam("userEmail") String userEmail,
							 @FormParam("userContact") String userContact,
							 @FormParam("username") String username,
							 @FormParam("password") String password)
	{
	 String output = user.insertUser(userId, name, userNIC, userEmail, userContact, username, password);
	return output;
	}
	
	@GET
	@Path("/all") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String readUsers(){ 
		String output = user.readUsers(); 
		return output;
	 } 

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUser(String userData)
	{
	//Convert the input string to a JSON object
	 JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
	 
	//Read the values from the JSON object
	 String id = userObject.get("userId").getAsString();
	 String name = userObject.get("name").getAsString();
	 String userNIC =userObject.get("userNIC").getAsString();
	 String userEmail = userObject.get("userEmail").getAsString();
	 String userContact = userObject.get("userContact").getAsString();
	 String username = userObject.get("username").getAsString();
	 String password = userObject.get("password").getAsString();
	 String output = user.updateUser( id, name, userNIC, userEmail, userContact,username, password);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String userData)
	{
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(userData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <itemID>
		 String userID = doc.select("userId").text(); 
		 String output = user.deleteUser(userID); 
		return output;
	}

}
