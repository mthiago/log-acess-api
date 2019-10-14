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
import br.com.java.model.PosicaoAcessoResponse;
import br.com.java.model.RegiaoModel;
import br.com.java.model.RegioesModel;

public class LogAcessService {

	//OK
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	//OK
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
		Integer catsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%'");
		Integer dogsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'");
		Integer bidInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%'");

		List<PosicaoAcessoResponse> response = new ArrayList<PosicaoAcessoResponse>();
		
		PosicaoAcessoResponse cat = buildInfos(catsInteger, "/pets/exotic/cats/10");
		PosicaoAcessoResponse dog = buildInfos(dogsInteger, "/pets/guaipeca/dogs/1");
		PosicaoAcessoResponse bid = buildInfos(catsInteger, "/tiggers/bid/now");
		response.add(cat);

		List<PosicaoAcessoResponse> ordenada = response.stream()
				.sorted(Comparator.comparing(PosicaoAcessoResponse::getPosition).reversed())
				.collect(Collectors.toList()); 

		String infos = "";
		StringBuilder msg = new StringBuilder();
		for (PosicaoAcessoResponse r : ordenada) {
			if (r.getPosition() != null && r.getUrl() != null) {
				infos = "A url mais acessada no mundo é " + r.getUrl() + " e ela teve " + r.getPosition() + " acessos\n";
				msg.append(infos);
			}
		}

		return msg.toString();
	}

	private PosicaoAcessoResponse buildInfos(Integer quantidade, String url) {
		PosicaoAcessoResponse metricResponse = new PosicaoAcessoResponse();
		metricResponse.setPosition(quantidade);
		metricResponse.setUrl(url);
		return metricResponse;
	}
	
	private AcessosModel buildAcessosRegiao(String animal, Integer regiao, Integer quantidade) {
		AcessosModel acessos = new AcessosModel();
		acessos.setAnimal(animal);
		acessos.setRegiao(regiao);
		acessos.setAcessos(quantidade);
		return acessos;
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

		AcessosModel acessosCatReg1 = buildAcessosRegiao("cat", 1, gatosRegiaoUm);
		AcessosModel acessosCatReg2 = buildAcessosRegiao("cat", 2, gatosRegiaoDois);
		AcessosModel acessosCatReg3 = buildAcessosRegiao("cat", 3, gatosRegiaoTres);

		AcessosModel acessosDogReg1 = buildAcessosRegiao("dog", 1, dogsRegiaoUm);
		AcessosModel acessosDogReg2 = buildAcessosRegiao("dog", 2, dogsRegiaoDois);
		AcessosModel acessosDogReg3 = buildAcessosRegiao("dog", 3, dogsRegiaoTres);
		
		AcessosModel acessosBidReg1 = buildAcessosRegiao("bid", 1, tigresRegiaoUm);
		AcessosModel acessosBidReg2 = buildAcessosRegiao("bid", 2, tigresRegiaoDois);
		AcessosModel acessosBidReg3 = buildAcessosRegiao("bid", 3, tigresRegiaoTres);

		regiao1.setAcessos(acessosReg1);
		regiao2.setAcessos(acessosReg2);
		regiao3.setAcessos(acessosReg3);

		listRegiao.add(regiao1);
		listRegiao.add(regiao2);
		listRegiao.add(regiao3);

		regioes.setRegiao(listRegiao);

		List<AcessosModel> accreg1 = ordenarListaDecrescente(regioes.getRegiao().get(0));
		List<AcessosModel> accreg2 = ordenarListaDecrescente(regioes.getRegiao().get(1));
		List<AcessosModel> accreg3 = ordenarListaDecrescente(regioes.getRegiao().get(3));

		String infos = "";

		StringBuilder msg = new StringBuilder();

		for (AcessosModel ac : accreg1) {
			msg.append("Quantidade de acessos região 1: " + ac.getAnimal() + "nro: " + ac.getAcessos() + "\n"); 	  
		}

	}	

	private List<AcessosModel> ordenarListaDecrescente(RegiaoModel regiao) {
		List<AcessosModel> listaOrdenada = regiao.getAcessos().stream()
				.sorted(Comparator.comparing(AcessosModel::getAcessos).reversed())
				.collect(Collectors.toList()); 
		return listaOrdenada;
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


		List<PosicaoAcessoResponse> response = new ArrayList<PosicaoAcessoResponse>();

		PosicaoAcessoResponse cat = buildInfos(catsInteger, "/pets/exotic/cats/10");
		PosicaoAcessoResponse dog = buildInfos(dogsInteger, "/pets/guaipeca/dogs/1");
		PosicaoAcessoResponse bid = buildInfos(catsInteger, "/tiggers/bid/now");

		List<PosicaoAcessoResponse> ordenada = response.stream()
				.sorted(Comparator.comparing(PosicaoAcessoResponse::getPosition).reversed())
				.collect(Collectors.toList()); 

		String infos = "";
		StringBuilder msg = new StringBuilder();
		for (PosicaoAcessoResponse r : ordenada) {
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