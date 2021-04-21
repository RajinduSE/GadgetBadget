package com;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import model.Auth;

@Path("/Auth")
public class AuthService {
	Auth auth = new Auth();
	@GET
	@Path("/login") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String login(String credentials){ 
		//get existing users list from UsersAPI
		Client c = Client.create();
		WebResource resource = c.resource("http://localhost:8080/UsersAPI/UserService/Users/");
		String usersList = resource.get(String.class);
		
		//Convert the input string to a JSON object 
		 JsonObject userObject = new JsonParser().parse(credentials).getAsJsonObject(); 
		 
		//Read the values from the JSON object
		 String username = userObject.get("username").getAsString(); 
		 String password = userObject.get("password").getAsString(); 

		String output = auth.login(usersList,username,password);
		
		return output;

	 } 
	
	@GET
	@Path("/verify") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String verify(){ 
	
		String output = auth.verify();
		
		return output;

	 }
	
	@GET
	@Path("/adminVerify")
	@Produces(MediaType.TEXT_PLAIN) 
	public String adminVerify(){ 
	
		String output = auth.adminVerify();
		
		return output;

	 }
	
	@GET
	@Path("/logout") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String logout(){ 
	
		String output = auth.logout();
		
		return output;

	 }
	
}
