package com.rest.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import request.LogRequest;

@Path("/log-acess")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogAcessEndPoint {

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