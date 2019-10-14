package br.com.java.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	public DatabaseConnection() {}
	
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "123456";
	
	public static Connection getConnection() throws SQLException {

		try {
			String basePath = new File("").getAbsolutePath();
			Class.forName("org.sqlite.JDBC");
			String dbPath = "jdbc:sqlite:" + basePath + "/utils/database/database.db";
			Connection conn = DriverManager.getConnection(dbPath, DB_USER, DB_PASSWORD);
			return conn;
		} catch (Exception e) {
			System.err.println("Erro ao criar conexão: " + e.getMessage());
		}
		return null;
	}

}