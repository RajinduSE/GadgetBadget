package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import model.Research;

@Path("/Researches")
public class ResearchService {
	Research research = new Research();
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readResearchesUser(){ 
		if(!verifyProfile()) {
			return "Login First";
		}
		return research.readResearchesUser(); 
	 } 
	
	@GET
	@Path("/pending") 
	@Produces(MediaType.TEXT_HTML) 
	public String readResearchesAdmin(){ 
		if(!verifyProfile()) {
			return "Login First";
		}
		
		if(!verifyAdminProfile()) {
			return "Not Permitted";
		}
		return research.readResearchesAdmin(); 
	 } 
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addResearch(String researchData) 
	{
		if(!verifyProfile()) {
			return "Login First";
		}
		
		//Convert the input string to a JSON object 
		 JsonObject researchObj = new JsonParser().parse(researchData).getAsJsonObject(); 
		   
		//Read the values from the JSON object
		 String title = researchObj.get("title").getAsString();
		 String author = researchObj.get("author").getAsString();
		 String catergory = researchObj.get("catergory").getAsString();
		 int year = researchObj.get("year").getAsInt();
		 String path = researchObj.get("path").getAsString(); 
		 String output = research.addResearch(title, author, catergory, year, path);
		 return output;
	}
	
	@GET
	@Path("/download") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN) 
	public String downloadResearch(String researchData){ 
		//Convert the input string to a JSON object 
		 JsonObject researchObj = new JsonParser().parse(researchData).getAsJsonObject(); 
		 
		 		 
		//Read the values from the JSON object
		 int id = researchObj.get("id").getAsInt();
		 String path = researchObj.get("path").getAsString();
		 String file = researchObj.get("file").getAsString();
		
		return research.downloadResearch(id, path, file); 
	 }
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateResearch(String researchData) {
		if(!verifyProfile()) {
			return "Login First";
		}
				
		//Convert the input string to a JSON object 
		 JsonObject researchObj = new JsonParser().parse(researchData).getAsJsonObject(); 
		 
		//Read the values from the JSON object
		 int researchId = researchObj.get("id").getAsInt();
		 String title = researchObj.get("title").getAsString();
		 String author = researchObj.get("author").getAsString();
		 String catergory = researchObj.get("catergory").getAsString();
		 int year = researchObj.get("year").getAsInt();
		 String output = research.updateResearch(researchId, title, author, catergory, year); 
		 return output;
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteResearch(String researchData) {
		if(!verifyProfile()) {
			return "Login First";
		}
				
		//Convert the input string to a JSON object 
		 JsonObject researchObj = new JsonParser().parse(researchData).getAsJsonObject(); 
		 
		//Read the values from the JSON object
		 int researchId = researchObj.get("id").getAsInt();

		 String output = research.deleteResearch(researchId); 
		 return output;
	}
	
	@PUT
	@Path("/approve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String approveResearch(String researchData) {
		if(!verifyProfile()) {
			return "Login First";
		}
		
		if(!verifyAdminProfile()) {
			return "Not Permitted";
		}
				
		//Convert the input string to a JSON object 
		 JsonObject researchObj = new JsonParser().parse(researchData).getAsJsonObject(); 
		 
		//Read the values from the JSON object
		 int researchId = researchObj.get("id").getAsInt();
		 String output = research.approveResearch(researchId); 
		 return output;
	}
	
	public boolean verifyAdminProfile() {
		//get existing users list from UsersAPI
		Client c = Client.create();
		WebResource resource = c.resource("http://localhost:8080/AuthenticationAPI/AuthService/Auth/adminVerify");
		String status = resource.get(String.class);
				
		if(!status.equals("Verified Admin Profile")) {
			return false;
		}
		return true;		
	}	
	
	public boolean verifyProfile() {
		//get existing users list from UsersAPI
		Client c = Client.create();
		WebResource resource = c.resource("http://localhost:8080/AuthenticationAPI/AuthService/Auth/verify");
		String status = resource.get(String.class);
				
		if(!status.equals("Verified Profile")) {
			return false;
		}
		return true;		
	}
	
}
