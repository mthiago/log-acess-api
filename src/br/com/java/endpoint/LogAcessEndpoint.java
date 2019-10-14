package br.com.java.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.java.model.LogModel;
import br.com.java.service.LogAcessService;

@Path("/log-acess")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogAcessEndpoint {

	LogAcessService service = new LogAcessService();

	@GET
	@Path("/laar/metrics/{data}")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public String metrics(@PathParam("data") String data) {
		String metrics = service.consultaMetricas(data);
		return metrics;
	}

	@POST
	@Path("/laar/ingest")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public Boolean salvarLog(LogModel log) {
		Boolean response = service.salvarLog(log);
		return response;
	}

}