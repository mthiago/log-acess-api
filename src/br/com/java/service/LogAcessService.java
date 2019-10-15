package br.com.java.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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

	//OK
	public String consultaPrimeiraMetrica(Connection connection, LogAcessDao logAcessDao) throws SQLException{
		Integer catsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%'");
		Integer dogsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'");
		Integer bidInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%'");

		List<PosicaoAcessoResponse> response = new ArrayList<PosicaoAcessoResponse>();
		
		PosicaoAcessoResponse cat = buildInfos(catsInteger, "/pets/exotic/cats/10");
		PosicaoAcessoResponse dog = buildInfos(dogsInteger, "/pets/guaipeca/dogs/1");
		PosicaoAcessoResponse bid = buildInfos(bidInteger, "/tiggers/bid/now");
		addToResponse(response, cat, dog, bid);

		List<PosicaoAcessoResponse> ordenada = ordenaPosicaoAcesso(response); 

		StringBuilder msg = new StringBuilder();
		msg = buildPrimeiraMetricaResponse(ordenada, msg);

		return msg.toString();
	}

	private StringBuilder buildPrimeiraMetricaResponse(List<PosicaoAcessoResponse> ordenada, StringBuilder msg) {
		String infos = "";
		for (PosicaoAcessoResponse r : ordenada) {
			if (r.getPosition() != null && r.getUrl() != null) {
				infos = "A url mais acessada no mundo é " + r.getUrl() + " e ela teve " + r.getPosition() + " acessos\n";
				msg.append(infos);
			}
		}
		return msg;
	}

	private List<PosicaoAcessoResponse> ordenaPosicaoAcesso(List<PosicaoAcessoResponse> response) {
		List<PosicaoAcessoResponse> ordenada = response.stream()
				.sorted(Comparator.comparing(PosicaoAcessoResponse::getPosition).reversed())
				.collect(Collectors.toList());
		return ordenada;
	}

	private void addToResponse(List<PosicaoAcessoResponse> response, PosicaoAcessoResponse cat, PosicaoAcessoResponse dog, PosicaoAcessoResponse bid) {
		response.add(cat);
		response.add(dog);
		response.add(bid);
	}

	//OK
	private PosicaoAcessoResponse buildInfos(Integer quantidade, String url) {
		PosicaoAcessoResponse metricResponse = new PosicaoAcessoResponse();
		metricResponse.setPosition(quantidade);
		metricResponse.setUrl(url);
		return metricResponse;
	}
	
	//OK
	private AcessosModel buildAcessosRegiao(String animal, Integer regiao, Integer quantidade) {
		AcessosModel acessos = new AcessosModel();
		acessos.setAnimal(animal);
		acessos.setRegiao(regiao);
		acessos.setAcessos(quantidade);
		return acessos;
	}

	//OK
	public String consultaSegundaMetrica(Connection connection, LogAcessDao logAcessDao) throws SQLException {

		Integer gatosRegiaoUm = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 1");
		Integer gatosRegiaoDois = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 2");
		Integer gatosRegiaoTres = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 3");

		Integer dogsRegiaoUm = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 1");
		Integer dogsRegiaoDois = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 2");
		Integer dogsRegiaoTres = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 3");

		Integer tigresRegiaoUm = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 1");
		Integer tigresRegiaoDois = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 2");
		Integer tigresRegiaoTres = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 3");

		AcessosModel acessosCatReg1 = buildAcessosRegiao("cat", 1, gatosRegiaoUm);
		AcessosModel acessosCatReg2 = buildAcessosRegiao("cat", 2, gatosRegiaoDois);
		AcessosModel acessosCatReg3 = buildAcessosRegiao("cat", 3, gatosRegiaoTres);

		AcessosModel acessosDogReg1 = buildAcessosRegiao("dog", 1, dogsRegiaoUm);
		AcessosModel acessosDogReg2 = buildAcessosRegiao("dog", 2, dogsRegiaoDois);
		AcessosModel acessosDogReg3 = buildAcessosRegiao("dog", 3, dogsRegiaoTres);
		
		AcessosModel acessosBidReg1 = buildAcessosRegiao("bid", 1, tigresRegiaoUm);
		AcessosModel acessosBidReg2 = buildAcessosRegiao("bid", 2, tigresRegiaoDois);
		AcessosModel acessosBidReg3 = buildAcessosRegiao("bid", 3, tigresRegiaoTres);

		List<AcessosModel> acessosReg1 = adicionaAcessosRegiao(acessosCatReg1, acessosDogReg1, acessosBidReg1);
		List<AcessosModel> acessosReg2 = adicionaAcessosRegiao(acessosCatReg2, acessosDogReg2, acessosBidReg2);
		List<AcessosModel> acessosReg3 = adicionaAcessosRegiao(acessosCatReg3, acessosDogReg3, acessosBidReg3);
		
		RegiaoModel regiao1 = criaRegiao(acessosReg1);
		RegiaoModel regiao2 = criaRegiao(acessosReg2);
		RegiaoModel regiao3 = criaRegiao(acessosReg3);

		List<RegiaoModel> listRegiao = criaListaRegiao(regiao1, regiao2, regiao3);

		RegioesModel regioes = new RegioesModel();
		regioes.setRegiao(listRegiao);

		List<AcessosModel> listaAcessosRegiaoUm = ordenarListaDecrescente(regioes.getRegiao().get(0));
		List<AcessosModel> listaAcessosRegiaoDois = ordenarListaDecrescente(regioes.getRegiao().get(1));
		List<AcessosModel> listaAcessosRegiaoTres = ordenarListaDecrescente(regioes.getRegiao().get(2));

		String infos = buildReturnSegundaMetrica(listaAcessosRegiaoUm, listaAcessosRegiaoDois, listaAcessosRegiaoTres);
		return infos;

	}

	private String buildReturnSegundaMetrica(List<AcessosModel> listaAcessosRegiaoUm, List<AcessosModel> listaAcessosRegiaoDois, List<AcessosModel> listaAcessosRegiaoTres) {
		StringBuilder msg = new StringBuilder();
		for (AcessosModel acessos : listaAcessosRegiaoUm) {
			msg.append("Quantidade de acessos região 1: " + acessos.getAnimal() + "nro: " + acessos.getAcessos() + "\n"); 	  
		}
		for (AcessosModel acessos : listaAcessosRegiaoDois) {
			msg.append("Quantidade de acessos região 2: " + acessos.getAnimal() + "nro: " + acessos.getAcessos() + "\n"); 	  
		}
		for (AcessosModel acessos : listaAcessosRegiaoTres) {
			msg.append("Quantidade de acessos região 3: " + acessos.getAnimal() + "nro: " + acessos.getAcessos() + "\n"); 	  
		}
		return msg.toString();
	}

	private List<RegiaoModel> criaListaRegiao(RegiaoModel regiao1, RegiaoModel regiao2, RegiaoModel regiao3) {
		List<RegiaoModel> listRegiao = new ArrayList<RegiaoModel>();
		listRegiao.add(regiao1);
		listRegiao.add(regiao2);
		listRegiao.add(regiao3);
		return listRegiao;
	}

	private RegiaoModel criaRegiao(List<AcessosModel> acessosReg) {
		RegiaoModel regiao = new RegiaoModel();
		regiao.setAcessos(acessosReg);
		return regiao;
	}

	private List<AcessosModel> adicionaAcessosRegiao(AcessosModel acessosCatReg, AcessosModel acessosDogReg, AcessosModel acessosBidReg) {
		List<AcessosModel> acessosReg = new ArrayList<AcessosModel>();
		acessosReg.add(acessosCatReg);
		acessosReg.add(acessosDogReg);
		acessosReg.add(acessosBidReg);
		return acessosReg;
	}

	//OK
	private List<AcessosModel> ordenarListaDecrescente(RegiaoModel regiao) {
		List<AcessosModel> listaOrdenada = regiao.getAcessos().stream()
				.sorted(Comparator.comparing(AcessosModel::getAcessos).reversed())
				.collect(Collectors.toList()); 
		return listaOrdenada;
	}

	//OK
	public String consultaTerceiraMetrica(Connection connection, LogAcessDao logAcessDao) throws SQLException{
		Integer catsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%'");
		Integer dogsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'");
		Integer bidsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%'");
		String msgReturn = "Cat: " + catsInteger + "///// Dog: " + dogsInteger + "/////bids: " + bidsInteger;
		return msgReturn;
	}

	//OK
	public String consultaQuartaMetrica(Connection connection, LogAcessDao logAcessDao, String data) throws SQLException, ParseException{
		Timestamp timestamp = buildTimestamp(data);

		Integer catsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and timestamp like '%" + timestamp.getTime() + "%'");
		Integer dogsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and timestamp = " + timestamp.getTime());
		Integer bidsInteger = logAcessDao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and timestamp = " + timestamp.getTime());

		List<PosicaoAcessoResponse> response = new ArrayList<PosicaoAcessoResponse>();

		PosicaoAcessoResponse cat = buildInfos(catsInteger, "/pets/exotic/cats/10");
		PosicaoAcessoResponse dog = buildInfos(dogsInteger, "/pets/guaipeca/dogs/1");
		PosicaoAcessoResponse bid = buildInfos(bidsInteger, "/tiggers/bid/now");
		
		addToResponse(response, cat, dog, bid);

		List<PosicaoAcessoResponse> ordenada = response.stream()
				.sorted(Comparator.comparing(PosicaoAcessoResponse::getPosition).reversed())
				.collect(Collectors.toList()); 

		String infos = buildResponseQuartaMetrica(data, ordenada);

		return infos;
	}

	private String buildResponseQuartaMetrica(String data, List<PosicaoAcessoResponse> ordenada) {
		String infos;
		StringBuilder msg = new StringBuilder();
		for (PosicaoAcessoResponse r : ordenada) {
			if (r.getPosition() != null && r.getUrl() != null) {
				infos = "A url: " + r.getUrl() + "ficou na posiçao: " + r.getPosition() + "no dia" + data;
				msg.append(infos);
			}
		}
		return msg.toString();
	}

	private Timestamp buildTimestamp(String data) throws ParseException {
		String novaData = data.replace("data=", "").replace("-", "/");
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date)formatter.parse(novaData);
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}	

	public String consultaQuintaMetrica(Connection connection, LogAcessDao logAcessDao) throws SQLException{
		List<Long> listTimestamp = logAcessDao.consultaDadosTimestamp(connection, "select timestamp from logs");
		List<AcessosMinutoModel> acessosMinuto = criaListaMinutos();
		for (Long timestamp : listTimestamp) {
			Integer minuto = verificaMinutoTimestamp(timestamp);
			montaAcessoMinuto(acessosMinuto, minuto);
		}
		return null;
	}

	private void montaAcessoMinuto(List<AcessosMinutoModel> acessosMinuto, Integer minuto) {
		for (AcessosMinutoModel acessoMinuto : acessosMinuto) {
			if (minuto == acessoMinuto.getMinuto()) {
				if (acessoMinuto.getQuantidade() != null) {
					acessoMinuto.setQuantidade(acessoMinuto.getQuantidade()+1);
					break;
				} else {
					acessoMinuto.setQuantidade(1);
					break;
				}
			}
		}
	}

	private Integer verificaMinutoTimestamp(Long timestamp) {
		Timestamp t = new Timestamp(timestamp);
		Date data = new Date(t.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		Integer minuto = calendar.get(Calendar.MINUTE);
		return minuto;
	}

	private List<AcessosMinutoModel> criaListaMinutos() {
		List<AcessosMinutoModel> acessosMinuto = new ArrayList<AcessosMinutoModel>();
		for (int i=0; i<=59; i++) {		
			AcessosMinutoModel acessoMinuto = new AcessosMinutoModel();
			acessoMinuto.setMinuto(i);
			acessosMinuto.add(acessoMinuto);
		}
		return acessosMinuto;
	}

	//OK
	public Boolean salvarLog(LogModel log) {
		LogAcessDao logAcessDao = new LogAcessDao();
		Boolean response = logAcessDao.salvarLog(log);
		return response;
	}

}