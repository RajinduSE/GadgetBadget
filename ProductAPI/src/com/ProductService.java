package com;

import model.Product; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 
@Path("/Products") 
public class ProductService {
	
Product proObj = new Product(); 



@GET
@Path("/")
@Produces(MediaType.TEXT_HTML)
public String readProduct() {
	return proObj.readProduct();
}

	
@POST
@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_PLAIN) 
public String insertProduct(@FormParam("proCode") String proCode, 
		@FormParam("proName") String proName,
		@FormParam("proPrice") String proPrice,
		@FormParam("proQty") String proQty,
		@FormParam("proDesc") String proDesc)
{
	String output = proObj.insertProduct(proCode, proName, proPrice, proQty, proDesc);
	return output;
}

	 
	
@PUT
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public String updateProduct(String ProductData) {
	// Convert the input string to a JSON object
	JsonObject productObject = new JsonParser().parse(ProductData).getAsJsonObject();
	// Read the values from the JSON object
	int ProID = productObject.get("proID").getAsInt();
	String proCode = productObject.get("proCode").getAsString();
	String proName = productObject.get("proName").getAsString();
	double proPrice = productObject.get("proPrice").getAsDouble();
	int proQty = productObject.get("proQty").getAsInt();
	String proDesc = productObject.get("proDesc").getAsString();
	
	String output = proObj.updateProduct(ProID, proCode, proName, proPrice, proQty, proDesc);
	return output;
}
	 


@DELETE
@Path("/")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.TEXT_PLAIN)
public String deleteProduct(String ProductData) {
	// Convert the input string to an XML document
	Document doc = Jsoup.parse(ProductData, "", Parser.xmlParser());

	// Read the value from the element <proID>
	String ID = doc.select("proID").text();
	String output = proObj.deleteProduct(ID);
	return output;
}

}
