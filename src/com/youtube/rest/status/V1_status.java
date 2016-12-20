package com.youtube.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
}

// root = com.youtube.rest/api/
// browser address is always a @GET
// @POST - for submitting forms on websites
// versioning(v1)... if deprecated create v2, for new/old users