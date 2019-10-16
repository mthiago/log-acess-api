package br.com.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnection {

	public DatabaseConnection() {}
	
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "123456";
	private static ResourceBundle bundle;
	private static String caminho;

	public static Connection getConnection() throws SQLException {
		try {
			bundle = ResourceBundle.getBundle("DatabaseConfig");
			caminho = bundle.getString("caminho");
			Class.forName("org.sqlite.JDBC");
			String dbPath = "jdbc:sqlite:" + caminho;
			Connection conn = DriverManager.getConnection(dbPath, DB_USER, DB_PASSWORD);
			return conn;
		} catch (Exception e) {
			System.err.println("Erro ao criar conexão: " + e.getMessage());
		}
		return null;
	}

}