import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	public static String status = "Não conectou...";

	public DatabaseConnection() {}

	public static Connection getConexaoMySQL() {

		Connection connection = null;

		try {

			String serverName = "localhost";

			String mydatabase = "logs";

			String url = "jdbc:sqlite:C:/sqlite/database.db";
			 
			connection = DriverManager.getConnection(url);

			
			
			
			

		      // our SQL SELECT query. 
		      // if you only need a few columns, specify them by name instead of using "*"
		      String query = "SELECT * FROM logs";

		      // create the java statement
		      Statement st = connection.createStatement();
		      
		      // execute the query, and get a java resultset
		      ResultSet rs = st.executeQuery(query);
			
			
		      while (rs.next())
		      {
		        String url2 = rs.getString("url");
		        System.out.println(url2);
		      }
			
			
			
			
			if (connection != null) {
				status = ("STATUS--->Conectado com sucesso!");
			} else {
				status = ("STATUS--->Não foi possivel realizar conexão");
			}

			return connection;

		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			return null;
		}
	}

	public static String statusConection() {
		return status;
	}

	public static boolean FecharConexao() {
		try {
			DatabaseConnection.getConexaoMySQL().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public static java.sql.Connection ReiniciarConexao() {
		FecharConexao();
		return DatabaseConnection.getConexaoMySQL();

	}

}