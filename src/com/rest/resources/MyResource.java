package com.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/MyRes")
public class MyResource {

	@Path("/getData")
	@GET
	public String getData() {
		
		return "a";
	}

}
