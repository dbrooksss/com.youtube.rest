package com.youtube.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import com.youtube.doa.*;

@Path("/v1/status")
public class V1_status{

	private static final String apiVersion = "00.01.00";

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle(){
		return "<p>Java Web Service in GET</p>";
	}

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion(){
		return "<p>Version: </p>" + apiVersion;
	}

	@Path("/database") // if has path of db, run method
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception{
		PreparedStatement query = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;
		
		 // 1. get db connection
		 // 2. write sql query & have java compile it
		 // 3. send pre-compiled to db for data
		
		try{
			SQLServerDataSource ds = new SQLServerDataSource();
			conn = ds.getConnection();
			query = conn.prepareStatement("SELECT GETDATE() AS DATETIME");
			ResultSet rs = query.executeQuery();

			while(rs.next()){ // loop through rs
				myString = rs.getString("DATETIME"); // column name
			}

			returnString = "<p>Database Status</p> " + "<p>Database Date/Time return: " + myString + "</p>";
			query.close(); // close connection ***

		}catch(SQLException e){
			System.out.println("msg: " + e.getMessage());
		}finally{ // should have if db connection
			if(conn != null){
				conn.close();
			}
		}
		 return returnString;
	}
}

// root = com.youtube.rest/api/
// browser address is always a @GET
// @POST - for submitting forms on websites
// versioning(v1)... if deprecated create v2, for new/old users
//