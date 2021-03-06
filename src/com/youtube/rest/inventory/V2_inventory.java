package com.youtube.rest.inventory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import com.youtube.doa.DatabaseQueries;

//  This class is used to manage computer parts inventory.

@Path("/v2/inventory")
public class V2_inventory{

	/**
	 * This method will return the specific brand of PC parts the user is
	 * looking for. It uses a QueryParam bring in the data to the method.
	 * 
	 * Example would be:
	 * http://localhost:8080/com.youtube.rest/api/v2/inventory?brand=ASUS
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception{

		String returnString = null;
		JSONArray json = new JSONArray();

		try{

			// return a error if brand is missing from the url string
			if(brand == null){
				// http code 400 = Bad Request
				return Response.status(400).entity("Error: please specify brand for this search.").build();
			}

			DatabaseQueries db = new DatabaseQueries();

			json = db.queryReturnBrandParts(brand);
			returnString = json.toString();

		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}

	/*
	 * This method can be used if the method returnBrandParts is not used.
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * returnErrorOnBrand() throws Exception {
	 * 
	 * return Response.status(400).entity(
	 * "Error: please specify brand for this search.").build(); }
	 */

	/**
	 * This method will return the specific brand of PC parts the user is
	 * looking for. It is very similar to the method returnBrandParts except
	 * this method uses the PathParam to bring in the data.
	 * 
	 * Example would be:
	 * http://localhost:8080/com.youtube.rest/api/v2/inventory/ASUS
	 */
	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam("brand") String brand) throws Exception{

		String noformat;
		String returnString = null;

		JSONArray json = new JSONArray();

		try{

			DatabaseQueries db = new DatabaseQueries();

			json = db.queryReturnBrandParts(brand);
			noformat = json.toString();

			ObjectMapper mapper = new ObjectMapper();
			Object obj = mapper.readValue(noformat, Object.class);
			returnString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}

	/**
	 * This method does a search on both product and the product item number. It
	 * uses PathParam to bring in both parameters.
	 * 
	 * Example:
	 * http://localhost:8080/com.youtube.rest/api/v2/inventory/ASUS/168131318
	 * 
	 * @param brand
	 *            - product brand name
	 * @param item_number
	 *            - product item number
	 * @return - json array results list from the database
	 * @throws Exception
	 */
	@Path("/{brand}/{item_number}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSpecificBrandItem(@PathParam("brand") String brand, @PathParam("item_number") int item_number)
			throws Exception{

		String noformat;
		String returnString = null;

		JSONArray json = new JSONArray();

		try{

			DatabaseQueries db = new DatabaseQueries();

			json = db.queryReturnBrandItemNumber(brand, item_number);
			noformat = json.toString();

			ObjectMapper mapper = new ObjectMapper();
			Object obj = mapper.readValue(noformat, Object.class);
			returnString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}

	/**
	 * This method will allow you to insert data the PC_PARTS table. This is a
	 * example of using the Jackson Processor
	 * 
	 * Note: If you look, this method addPcParts using the same URL as a GET
	 * method returnBrandParts. We can do this because we are using different
	 * HTTP methods for the same URL string.
	 * 
	 * @param incomingData
	 *            - must be in JSON format
	 * @return String
	 * @throws Exception
	 */
	@POST
	// @Consumes = specifies the media types a resource can take in.
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception{

		String returnString = null;
		DatabaseQueries db = new DatabaseQueries();

		try{
			System.out.println("incomingData: " + incomingData);

			/*
			 * ObjectMapper is from Jackson Processor framework
			 * 
			 * Using the readValue method, you can parse the json from the http
			 * request and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper(); 
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class); 

			int http_code = db.insertIntoPC_PARTS(itemEntry.PC_PARTS_TITLE, itemEntry.PC_PARTS_CODE,
					itemEntry.PC_PARTS_MAKER, itemEntry.PC_PARTS_AVAIL, itemEntry.PC_PARTS_DESC);

			if(http_code == 200){
				// returnString = jsonArray.toString(); 
				returnString = "Item inserted.";
			}else{
				return Response.status(500).entity("Unable to process Item.").build();
			}

		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was unable to process your request.").build();
		}

		return Response.ok(returnString).build();
	}
}

// modularity = each method does one task
// simplicity = a method can do multiple tasks

/*
 * This is a class used by the addPcParts method. Used by the Jackson Processor
 * 
 * Note: for re-usability you should place this in its own package.
 */
class ItemEntry{
	public String PC_PARTS_TITLE;
	public String PC_PARTS_CODE;
	public String PC_PARTS_MAKER;
	public String PC_PARTS_AVAIL;
	public String PC_PARTS_DESC;
}