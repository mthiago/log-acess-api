package com.rest.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import request.LogRequest;

@Path("/log-acess")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogAcessEndPoint {

	@GET
	@Path("/laar/metrics")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public String metrics() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			Connection connection = DatabaseConnection.getConexaoMySQL();
           String query = "select count(*) from logs where url like '%/pets/exotic/cats/10%'";


           //PEGANDO CONTAGEM DE VISITANTES
           PreparedStatement Stmt = connection.prepareStatement(query);

           rs = Stmt.executeQuery();
           rs.next();
           String metricCats = rs.getString(1);
           Integer catsInteger = Integer.valueOf(metricCats);
           
          Stmt.close();
	        
	        
	           query = "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'";


	           //PEGANDO CONTAGEM DE VISITANTES
	           Stmt = connection.prepareStatement(query);

	           rs = Stmt.executeQuery();
	           rs.next();
	           String metricDogs = rs.getString(1);
	           Integer dogsInteger = Integer.valueOf(metricDogs);
	           
	           
	           Stmt.close();
		        
		        
	           query = "select count(*) from logs where url like '%/tiggers/bid/now%'";


	           //PEGANDO CONTAGEM DE VISITANTES
	           Stmt = connection.prepareStatement(query);

	           rs = Stmt.executeQuery();
	           rs.next();
	           String metricBid = rs.getString(1);
	           Integer bidInteger = Integer.valueOf(metricBid);
	           
	           
	          List<Metric1Response> response = new ArrayList<Metric1Response>();
	           
	          Metric1Response cat = new Metric1Response();
	          cat.setPosition(catsInteger);
	          cat.setUrl("/pets/exotic/cats/10");
	          response.add(cat);
	          
	          
	          Metric1Response dog = new Metric1Response();
	          dog.setPosition(dogsInteger);
	          dog.setUrl("/pets/guaipeca/dogs/1");
	          response.add(dog);
	          
	          Metric1Response bid = new Metric1Response();
	          bid.setPosition(bidInteger);
	          cat.setUrl("/tiggers/bid/now");
	          response.add(cat);

	          List<Metric1Response> ordenada = response.stream()
	        		    .sorted(Comparator.comparing(Metric1Response::getPosition).reversed())
	        		    .collect(Collectors.toList()); 
	          
	          String infos = "";
	          StringBuilder msg = new StringBuilder();
	      for (Metric1Response r : ordenada) {
	    	  if (r.getPosition() != null && r.getUrl() != null) {
	    		  infos = "A url mais acessada no mundo é" + r.getUrl() + "e ela teve " + r.getPosition() + " acessos";
	    		  msg.append(infos);
	    	  }
	      }
	      
	      String msgReturn = msg.toString();
          
	      
	      return msgReturn;
          
          
	       
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/laar/ingest")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public Boolean salvarLog(LogRequest request) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection connection = DatabaseConnection.getConexaoMySQL();

			// the mysql insert statement
			String query = "INSERT INTO LOGS(url, timestamp, userId, region) VALUES(?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			pstmt = connection.prepareStatement(query);

			pstmt.setString(1, request.getUrl());
			pstmt.setLong(2, request.getTimestamp());
			pstmt.setString(3, request.getUserId());
			pstmt.setInt(4, request.getRegion());
			pstmt.executeUpdate();
			pstmt.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}