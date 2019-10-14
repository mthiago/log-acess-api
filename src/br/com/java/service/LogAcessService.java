package br.com.java.service;

import java.sql.Connection;
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

import br.com.java.dao.LogAcessDao;
import br.com.java.database.DatabaseConnection;
import br.com.java.model.AcessosMinutoModel;
import br.com.java.model.AcessosModel;
import br.com.java.model.LogModel;
import br.com.java.model.Metric1Response;
import br.com.java.model.RegiaoModel;
import br.com.java.model.RegioesModel;

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

	public String consultaPrimeiraMetrica(Connection connection, LogAcessDao logAcessDao) throws SQLException{
		String msgReturn ="";
		Integer catsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%'");
		Integer dogsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'");
		Integer bidInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%'");

		List<Metric1Response> response = new ArrayList<Metric1Response>();

		Metric1Response cat = new Metric1Response();
		cat.setPosition(catsInteger);
		cat.setUrl("/pets/exotic/cats/10");
		response.add(cat);

		Metric1Response dog = new Metric1Response();
		dog.setPosition(dogsInteger);
		dog.setUrl("/pets/guaipeca/dogs/1");
		response.add(dog);

		Metric1Response bid = new Metric1Response();
		bid.setPosition(bidInteger);
		bid.setUrl("/tiggers/bid/now");
		response.add(bid);

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

	public String consultaSegundaMetrica(Connection connection, LogAcessDao logAcessDao) throws SQLException{
		Integer gatosRegiaoUm = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 1");
		Integer gatosRegiaoDois = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 2");
		Integer gatosRegiaoTres = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 3");
		
		Integer dogsRegiaoUm = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 1");
		Integer dogsRegiaoDois = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 2");
		Integer dogsRegiaoTres = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 3");
		
		Integer tigresRegiaoUm = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 1");
		Integer tigresRegiaoDois = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 2");
		Integer tigresRegiaoTres = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 3");
		

		RegioesModel regioes = new RegioesModel();
		List<RegiaoModel> listRegiao = new ArrayList<RegiaoModel>();
		RegiaoModel regiao1 = new RegiaoModel();
		RegiaoModel regiao2 = new RegiaoModel();
		RegiaoModel regiao3 = new RegiaoModel();

		List<AcessosModel> acessosReg1 = new ArrayList<AcessosModel>();
		List<AcessosModel> acessosReg2 = new ArrayList<AcessosModel>();
		List<AcessosModel> acessosReg3 = new ArrayList<AcessosModel>();

		AcessosModel acessosCatReg1 = new AcessosModel();
		acessosCatReg1.setAnimal("cat");
		acessosCatReg1.setRegiao(1);
		acessosCatReg1.setAcessos(gatosRegiaoUm);
		acessosReg1.add(acessosCatReg1);

		AcessosModel acessosCatReg2 = new AcessosModel();
		acessosCatReg2.setAnimal("cat");
		acessosCatReg2.setRegiao(2);
		acessosCatReg2.setAcessos(gatosRegiaoDois);
		acessosReg2.add(acessosCatReg2);

		AcessosModel acessosCatReg3 = new AcessosModel();
		acessosCatReg3.setAnimal("cat");
		acessosCatReg3.setRegiao(3);
		acessosCatReg3.setAcessos(gatosRegiaoTres);
		acessosReg3.add(acessosCatReg3);

		AcessosModel acessosDogReg1 = new AcessosModel();
		acessosDogReg1.setAnimal("dog");
		acessosDogReg1.setRegiao(1);
		acessosDogReg1.setAcessos(dogsRegiaoUm);
		acessosReg1.add(acessosDogReg1);           

		AcessosModel acessosDogReg2 = new AcessosModel();
		acessosDogReg2.setAnimal("dog");
		acessosDogReg2.setRegiao(2);
		acessosReg2.add(acessosDogReg2);
		acessosDogReg2.setAcessos(dogsRegiaoDois);

		AcessosModel acessosDogReg3 = new AcessosModel();
		acessosDogReg3.setAnimal("dog");
		acessosDogReg3.setRegiao(1);
		acessosDogReg3.setAcessos(dogsRegiaoTres);
		acessosReg3.add(acessosDogReg3);

		AcessosModel acessosBidReg1 = new AcessosModel();
		acessosBidReg1.setAnimal("bid");
		acessosBidReg1.setRegiao(1);
		acessosBidReg1.setAcessos(tigresRegiaoUm);
		acessosReg1.add(acessosBidReg1);

		AcessosModel acessosBidReg2 = new AcessosModel();
		acessosBidReg2.setAnimal("bid");
		acessosBidReg2.setRegiao(2);
		acessosBidReg2.setAcessos(tigresRegiaoDois);
		acessosReg2.add(acessosBidReg2);

		AcessosModel acessosBidReg3 = new AcessosModel();
		acessosBidReg3.setAnimal("bid");
		acessosBidReg3.setRegiao(2);
		acessosBidReg3.setAcessos(tigresRegiaoTres);
		acessosReg3.add(acessosBidReg3);


		regiao1.setAcessos(acessosReg1);
		regiao2.setAcessos(acessosReg2);
		regiao3.setAcessos(acessosReg3);

		listRegiao.add(regiao1);
		listRegiao.add(regiao2);
		listRegiao.add(regiao3);


		regioes.setRegiao(listRegiao);




		List<AcessosModel> accreg1 = regioes.getRegiao().get(0).getAcessos().stream()
				.sorted(Comparator.comparing(AcessosModel::getAcessos).reversed())
				.collect(Collectors.toList()); 

		List<AcessosModel> accreg2 = regioes.getRegiao().get(0).getAcessos().stream()
				.sorted(Comparator.comparing(AcessosModel::getAcessos).reversed())
				.collect(Collectors.toList()); 

		List<AcessosModel> accreg3 = regioes.getRegiao().get(0).getAcessos().stream()
				.sorted(Comparator.comparing(AcessosModel::getAcessos).reversed())
				.collect(Collectors.toList()); 	          

		String infos = "";

		StringBuilder msg = new StringBuilder();



		for (AcessosModel ac : accreg1) {
			msg.append("Quantidade de acessos região 1: " + ac.getAnimal() + "nro: " + ac.getAcessos() + "\n"); 	  
		}
		
		
		
	}	

	public String consultaTerceiraMetrica(Connection connection, LogAcessDao logAcessDao) throws SQLException{
		Integer catsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%'");
		Integer dogsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'");
		Integer bidsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%'");
		String msgReturn = "Cat: " + catsInteger + "///// Dog: " + dogsInteger + "/////bids: " + bidsInteger;
		return msgReturn;
	}

	public String consultaQuartaMetrica(Connection connection, LogAcessDao logAcessDao, String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date)formatter.parse(data);

		Timestamp timestamp = new Timestamp(date.getTime());
		Integer catsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and timestamp like '%" + timestamp.getTime() + "%'");
		Integer dogsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and timestamp = " + timestamp.getTime());
		Integer bidsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and timestamp = " + timestamp.getTime());


		List<Metric1Response> response = new ArrayList<Metric1Response>();

		Metric1Response cat = new Metric1Response();
		cat.setPosition(catsInteger);
		cat.setUrl("/pets/exotic/cats/10");
		response.add(cat);


		Metric1Response dog = new Metric1Response();
		dog.setPosition(dogsInteger);
		dog.setUrl("/pets/guaipeca/dogs/1");
		response.add(dog);

		Metric1Response bid = new Metric1Response();
		bid.setPosition(bidsInteger);
		cat.setUrl("/tiggers/bid/now");
		response.add(cat);

		List<Metric1Response> ordenada = response.stream()
				.sorted(Comparator.comparing(Metric1Response::getPosition).reversed())
				.collect(Collectors.toList()); 

		String infos = "";
		StringBuilder msg = new StringBuilder();
		for (Metric1Response r : ordenada) {
			if (r.getPosition() != null && r.getUrl() != null) {
				infos = "A url: " + r.getUrl() + "ficou na posiçao: " + r.getPosition() + "no dia" + data;
				msg.append(infos);
			}
		}

		msgReturn = msg.toString();
	}	

	public String consultaQuintaMetrica(Connection connection, LogAcessDao logAcessDao) throws SQLException{
		List<Long> asdadqd = logAcessDao.consultaDadosTimestamp(connection, "select timestamp from logs");


		List<AcessosMinutoModel> acessosMinuto = new ArrayList<AcessosMinutoModel>();
		for (int i=0; i<=59; i++) {
			AcessosMinutoModel acessoMinuto = new AcessosMinutoModel();
			acessoMinuto.setMinuto(i);
			acessosMinuto.add(acessoMinuto);
		}

		for (Long qdqdq : asdadqd) {

			if (qdqdq.equals(Long.valueOf(1570853975))){

				Timestamp t = new Timestamp(qdqdq);



				Date dadas = new Date(t.getTime());

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dadas);

				Integer minuto = calendar.get(Calendar.MINUTE);



				for (AcessosMinutoModel asdqdq : acessosMinuto) {
					if (minuto == asdqdq.getMinuto() ) {
						if ( asdqdq.getQuantidade() != null) {
							asdqdq.setQuantidade(asdqdq.getQuantidade()+1);
							break;
						} else {
							asdqdq.setQuantidade(1);
							break;
						}
					}
				}
			}

		}
		return null;
	}

	public Boolean salvarLog(LogModel log) {
		LogAcessDao logAcessDao = new LogAcessDao();
		Boolean response = logAcessDao.salvarLog(log);
		return response;
	}

}