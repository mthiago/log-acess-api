package br.com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.java.database.DatabaseConnection;
import br.com.java.model.LogModel;

public class LogAcessDao {

	//ok
	public Boolean salvarLog(LogModel request) {
		PreparedStatement pstmt = null;
		try {
			Connection connection = DatabaseConnection.getConnection();
			String query = "INSERT INTO LOGS(url, timestamp, userId, region) VALUES(?, ?, ?, ?)";
			pstmt = connection.prepareStatement(query);

			if (request.getUrl() != null) pstmt.setString(1, request.getUrl());
			if (request.getTimestamp() != null) pstmt.setLong(2, request.getTimestamp());
			if (request.getUserId() != null) pstmt.setString(3, request.getUserId());
			if (request.getRegion() != null) pstmt.setInt(4, request.getRegion());

			pstmt.executeUpdate();
			pstmt.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//ok
	public Integer consultaDadosUrl(Connection connection, String query) throws SQLException {
		PreparedStatement Stmt = connection.prepareStatement(query);
		ResultSet rs = Stmt.executeQuery();
		rs.next();
		String retorno = rs.getString(1);
		Integer quantidade = Integer.valueOf(retorno);
		Stmt.close();
		return quantidade;
	}
	//ok
	public List<Long> consultaDadosTimestamp(Connection connection, String query) throws SQLException {
		PreparedStatement Stmt = connection.prepareStatement(query);
		ResultSet rs = Stmt.executeQuery();	
		List<Long> listTimestamp = new ArrayList<>();
		while (rs.next()) {
			Long timestamp = rs.getLong("timestamp");
			listTimestamp.add(timestamp);
		}
		return listTimestamp;
	}

}