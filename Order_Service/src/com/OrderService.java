package com;

import model.Order; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;

@Path("/Orders") 
public class OrderService {
	
	
	Order orderObj = new Order(); 
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readOrders() 
	 {     
	 return orderObj.readOrders(); 
	 }
	
	
	@GET
	@Path("/getOrderbyID/{id}")
	@Produces(MediaType.TEXT_HTML)
	public String getOrderbyID(@PathParam("id") String id) {
		System.out.println(id);
		
		return this.orderObj.getOrderbyID(id);
	}
	
	
	@POST
	@Path("/add") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertOrder(@FormParam("productID") String productID, 
	 @FormParam("productName") String productName, 
	 @FormParam("buyerName") String buyerName,
	 @FormParam("buyerPhone") String buyerPhone,
	 @FormParam("buyerMail") String buyerMail,
	 @FormParam("date") String date) 
	{ 
	 String output = orderObj.insertOrder(productID, productName, buyerName, buyerPhone, buyerMail, date); 
	return output; 
	}
	
	
	@PUT
	@Path("/update") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateOrder(String orderData) 
	{ 
	//Conversion to JSON object 
	 JsonObject orderObject = new JsonParser().parse(orderData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String orderID = orderObject.get("orderID").getAsString(); 
	 String productID = orderObject.get("productID").getAsString(); 
	 String productName = orderObject.get("productName").getAsString(); 
	 String buyerName = orderObject.get("buyerName").getAsString(); 
	 String buyerPhone = orderObject.get("buyerPhone").getAsString();
	 String buyerMail = orderObject.get("buyerMail").getAsString();
	 String date = orderObject.get("date").getAsString(); 
	 String output = orderObj.updateOrder(orderID, productID, productName, buyerName, buyerPhone, buyerMail, date); 
	return output; 
	}
	
	
	@DELETE
	@Path("/delete") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteOrder(String orderData) 
	{ 
	//Conversion to XML document
	 Document doc = Jsoup.parse(orderData, "", Parser.xmlParser()); 
	 
	//Read the value from orderID
	 String orderID = doc.select("orderID").text(); 
	 String output = orderObj.deleteOrder(orderID); 
	return output; 
	}



}
