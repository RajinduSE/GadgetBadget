package com;

import model.Payment; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 
@Path("/Payments") 

		public class PaymentService 
		{ 
		Payment payObj = new Payment(); 
		
		
		
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readPayment() {
			return payObj.readPayment();
		}
		
		
		
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertPayment(@FormParam("nameOnCard") String nameOnCard, 
				@FormParam("cardNumber") String cardNumber,
				@FormParam("expiredDate") String expiredDate,
				@FormParam("cvv") String cvv)
	
		{
			String output = payObj.insertPayment(nameOnCard, cardNumber, expiredDate, cvv);
			return output;
		}
		
		// new edit
		
		
		
		
		
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String updatePayment(@FormParam("payId") String payId,
				@FormParam("nameOnCard") String nameOnCard, 
				@FormParam("cardNumber") String cardNumber,
				@FormParam("expiredDate") String expiredDate,
				@FormParam("cvv") String cvv) {
		 
			
			String output = payObj.updatePayment(payId,nameOnCard, cardNumber, expiredDate, cvv);
			return output;
			
			 
		}
		
		
		
		// end here
		
		
		
		/*
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updatePayment(String PaymentData) {
			// Convert the input string to a JSON object
			JsonObject PaymentObject = new JsonParser().parse(PaymentData).getAsJsonObject();
			// Read the values from the JSON object
			String payId = PaymentObject.get("payId").getAsString();
			String nameOnCard = PaymentObject.get("nameOnCard").getAsString();
			String cardNumber = PaymentObject.get("cardNumber").getAsString();
			String expiredDate = PaymentObject.get("expiredDate").getAsString();
			String cvv = PaymentObject.get("cvv").getAsString();
			String output = payObj.updatePayment(payId, nameOnCard, cardNumber, expiredDate, cvv);
			return output;
		}
			 
		*/

		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deletePayment(String PaymentData) {
			// Convert the input string to an XML document
			Document doc = Jsoup.parse(PaymentData, "", Parser.xmlParser());

			// Read the value from the element <proID>
			String ID = doc.select("payID").text();
			String output = payObj.deletePayment(ID);
			return output;
		}
		

}