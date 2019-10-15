package br.com.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	public DatabaseConnection() {}
	
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "123456";
	
	//ok - possível melhoria url relativa dbPath
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			String dbPath = "jdbc:sqlite:C:/Users/thiag/Desktop/projetos/log-acess-api/utils/database/database.db";
			Connection conn = DriverManager.getConnection(dbPath, DB_USER, DB_PASSWORD);
			return conn;
		} catch (Exception e) {
			System.err.println("Erro ao criar conexão: " + e.getMessage());
		}
		return null;
	}

}