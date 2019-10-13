package br.com.java.database;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	public static String status = "Não conectou...";

	public DatabaseConnection() {}

	public static Connection getConexaoMySQL() throws SQLException {

		    try
		    {
		    	Class.forName("org.sqlite.JDBC");
		      // create our mysql database connection
		      //String myDriver = "org.gjt.mm.mysql.Driver";
		      String myUrl = "jdbc:sqlite:C:/Users/thiag/Desktop/projetos/database.db";
		      //Class.forName(myDriver);
		      Connection conn = DriverManager.getConnection(myUrl, "root", "123456");
		      
		      return conn;
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());
		    }
			return null;
		  }

}