import java.sql.SQLException;

public class teste {

	
	public static void main (String[] args) {
		//DatabaseConnection = new DatabaseConnection();
		try {
			DatabaseConnection.getConexaoMySQL();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	
}
