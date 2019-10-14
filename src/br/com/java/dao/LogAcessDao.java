package br.com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.java.database.DatabaseConnection;
import br.com.java.model.AcessosMinutoModel;
import br.com.java.model.AcessosModel;
import br.com.java.model.LogModel;
import br.com.java.model.Metric1Response;
import br.com.java.model.RegiaoModel;
import br.com.java.model.RegioesModel;

public class LogAcessDao {

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
	
	public Integer consultaDadosUrl(Connection connection, String query) throws SQLException {
		PreparedStatement Stmt = connection.prepareStatement(query);
		ResultSet rs = Stmt.executeQuery();
		rs.next();
		String retorno = rs.getString(1);
		Integer number = Integer.valueOf(retorno);
		Stmt.close();
		return number;
	}
	
	public List<Long> consultaDadosTimestamp(Connection connection, String query) throws SQLException {
		PreparedStatement Stmt = connection.prepareStatement(query);
		ResultSet rs = Stmt.executeQuery();	
		List<Long> asdadqd = new ArrayList<>();
		while (rs.next()) {
			Long ada = rs.getLong("timestamp");
			asdadqd.add(ada);
		}
		return asdadqd;
	}

}