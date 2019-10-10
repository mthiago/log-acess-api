package com.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/log-acess")
public class LogAcessEndPoint {

	@Path("/getData")
	@GET
	public String getData() {
		
		return "a";
	}

}
