package com.youtube.rest.inventory;

import java.sql.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import com.youtube.doa.SQLServerDataSource;
import com.youtube.util.ToJSON;

@Path("/v1/inventory")
public class V1_inventory{

	/**
	 * This method will return all computer parts that are listed in PC_PARTS
	 * table.
	 * 
	 * Note: This is a good method for a tutorial but probably should never have
	 * a method that returns everything from a database. 
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPcParts() throws Exception{

		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;

		try{
			SQLServerDataSource ds = new SQLServerDataSource();
			conn = ds.getConnection();
			query = conn.prepareStatement("SELECT * FROM PC_PARTS");
			
			ResultSet rs = query.executeQuery(); // records set

			ToJSON converter = new ToJSON(); // bring in instance
			JSONArray json = new JSONArray(); // holds array

			json = converter.toJSONArray(rs); // puts in records
			query.close();

			returnString = json.toString();

		}catch(SQLException e){
			System.out.println("msg: " + e.getMessage());
		}finally{
			if(conn != null){
				conn.close();
			}
		}
		return returnString;
	}
}