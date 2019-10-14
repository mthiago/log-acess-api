package br.com.java.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.java.dao.LogAcessDao;
import br.com.java.database.DatabaseConnection;
import br.com.java.model.LogModel;
import br.com.java.model.Metric1Response;

public class LogAcessService {

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public String consultaMetricas(String data) {
		String metricas = "";
		LogAcessDao logAcessDao = new LogAcessDao();
		try {
			Connection connection = DatabaseConnection.getConnection();
			String primeiraMetrica = consultaPrimeiraMetrica(connection, logAcessDao);
			String segundaMetrica = consultaSegundaMetrica(connection, logAcessDao);
			String terceiraMetrica = consultaTerceiraMetrica(connection, logAcessDao);
			String quartaMetrica = consultaQuartaMetrica(connection, logAcessDao, data);
			String quintaMetrica = consultaQuintaMetrica(connection, logAcessDao);
			metricas = primeiraMetrica + segundaMetrica + terceiraMetrica + quartaMetrica + quintaMetrica;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return metricas;
	}

	public String consultaPrimeiraMetrica(Connection connection, LogAcessDao logAcessDao){
		String msgReturn = "";
		List<Integer> numbers = logAcessDao.consultaPrimeiraMetrica(connection);
		List<Metric1Response> response = new ArrayList<Metric1Response>();

		Metric1Response cat = new Metric1Response();
		cat.setPosition(numbers.get(0));
		cat.setUrl("/pets/exotic/cats/10");
		response.add(cat);

		Metric1Response dog = new Metric1Response();
		dog.setPosition(numbers.get(1));
		dog.setUrl("/pets/guaipeca/dogs/1");
		response.add(dog);

		Metric1Response bid = new Metric1Response();
		bid.setPosition(numbers.get(2));
		cat.setUrl("/tiggers/bid/now");
		response.add(cat);

		List<Metric1Response> ordenada = response.stream()
				.sorted(Comparator.comparing(Metric1Response::getPosition).reversed())
				.collect(Collectors.toList()); 

		String infos = "";
		StringBuilder msg = new StringBuilder();
		for (Metric1Response r : ordenada) {
			if (r.getPosition() != null && r.getUrl() != null) {
				infos = "A url mais acessada no mundo é " + r.getUrl() + " e ela teve " + r.getPosition() + " acessos\n";
				msg.append(infos);
			}
		}

		msgReturn = msg.toString();

		return msgReturn;
	}

	public String consultaSegundaMetrica(Connection connection, LogAcessDao logAcessDao){
		String msgReturn = "";
		logAcessDao.consultaSegundaMetrica(connection);
		return msgReturn;
	}	

	public String consultaTerceiraMetrica(Connection connection, LogAcessDao logAcessDao){
		String msgReturn = "";
		logAcessDao.consultaTerceiraMetrica(connection);
		return msgReturn;
	}

	public String consultaQuartaMetrica(Connection connection, LogAcessDao logAcessDao, String data){
		String msgReturn = "";
		logAcessDao.consultaQuartaMetrica(connection, data);
		return msgReturn;
	}	

	public String consultaQuintaMetrica(Connection connection, LogAcessDao logAcessDao){
		String msgReturn = "";
		logAcessDao.consultaQuintaMetrica(connection);
		return msgReturn;
	}

	public Boolean salvarLog(LogModel log) {
		LogAcessDao logAcessDao = new LogAcessDao();
		Boolean response = logAcessDao.salvarLog(log);
		return response;
	}

}