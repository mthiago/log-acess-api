import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	public static String status = "Não conectou...";

	public DatabaseConnection() {}

	public static void getConexaoMySQL() throws SQLException {

		    try
		    {
		      // create our mysql database connection
		      //String myDriver = "org.gjt.mm.mysql.Driver";
		      String myUrl = "jdbc:sqlite:C:/Users/thiag/Desktop/projetos/database.db";
		      //Class.forName(myDriver);
		      Connection conn = DriverManager.getConnection(myUrl, "root", "123456");
		      
		      // our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "SELECT * FROM logs";

		      // create the java statement
		      Statement st = conn.createStatement();
		      
		      // execute the query, and get a java resultset
		      ResultSet rs = st.executeQuery(query);
		      
		      // iterate through the java resultset
		      while (rs.next())
		      {
		        String url = rs.getString("url");
		        System.out.println(url);
		      }
		      st.close();
		    }
		    catch (Exception e)
		    {
		      System.err.println("Got an exception! ");
		      System.err.println(e.getMessage());
		    }
		  }
		
		


}