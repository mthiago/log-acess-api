package br.com.java.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.java.model.LogModel;
import br.com.java.service.LogAcessService;

@Path("/log-acess")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogAcessEndpoint {

	LogAcessService service = new LogAcessService();

	@GET
	@Path("/laar/health")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public Response health() {
		return Response.status(Response.Status.OK).build();
	}	

	@GET
	@Path("/laar/metrics/{data}")
	@Produces("text/plain")
	@Consumes("application/json")
	public Response metrics(@PathParam("data") String data) {
		String metrics = "";
		try {
			metrics = service.consultaMetricas(data);
		} catch (Exception e) {
			e.getStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("Erro").build();
		}
		return Response.status(Response.Status.OK).entity(metrics).build();
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