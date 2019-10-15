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
import br.com.java.model.PosicaoAcessoModel;
import br.com.java.model.RegiaoModel;
import br.com.java.model.RegioesModel;

public class LogAcessService {

	//ok
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	//ok
	public String consultaMetricas(String data) {
		String metricas = "";
		LogAcessDao dao = new LogAcessDao();
		try {
			Connection connection = DatabaseConnection.getConnection();
			String primeiraMetrica = consultaPrimeiraMetrica(connection, dao);
			String segundaMetrica = consultaSegundaMetrica(connection, dao);
			String terceiraMetrica = consultaTerceiraMetrica(connection, dao);
			String quartaMetrica = consultaQuartaMetrica(connection, dao, data);
			String quintaMetrica = consultaQuintaMetrica(connection, dao);
			metricas = primeiraMetrica + segundaMetrica + terceiraMetrica + quartaMetrica + quintaMetrica;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return metricas;
	}

	//ok
	public String consultaPrimeiraMetrica(Connection connection, LogAcessDao dao) throws SQLException{
		Integer quantidadeGatos = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%'");
		Integer quantidadeCachorros = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'");
		Integer quantidadeTigres = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%'");

		List<PosicaoAcessoModel> listaPosicaoAcesso = new ArrayList<PosicaoAcessoModel>();
		
		PosicaoAcessoModel gatos = montaPosicaoModel(quantidadeGatos, "/pets/exotic/cats/10");
		PosicaoAcessoModel cachorros = montaPosicaoModel(quantidadeCachorros, "/pets/guaipeca/dogs/1");
		PosicaoAcessoModel tigres = montaPosicaoModel(quantidadeTigres, "/tiggers/bid/now");
		addListaPrimeiraMetrica(listaPosicaoAcesso, gatos, cachorros, tigres);

		List<PosicaoAcessoModel> listaOrdenada = ordenaPosicaoAcesso(listaPosicaoAcesso); 

		StringBuilder mensagem = new StringBuilder();
		mensagem = montaRespPrimeiraMetrica(listaOrdenada, mensagem);

		return mensagem.toString();
	}

	//ok
	private StringBuilder montaRespPrimeiraMetrica(List<PosicaoAcessoModel> listaOrdenada, StringBuilder mensagem) {
		String infos = "";
		for (PosicaoAcessoModel model : listaOrdenada) {
			if (model.getPosicao() != null && model.getUrl() != null) {
				infos = "A url mais acessada no mundo � " + model.getUrl() + " e ela teve " + model.getPosicao() + " acessos\n";
				mensagem.append(infos);
			}
		}
		return mensagem;
	}

	//ok
	private List<PosicaoAcessoModel> ordenaPosicaoAcesso(List<PosicaoAcessoModel> model) {
		List<PosicaoAcessoModel> listaOrdenada = model.stream()
				.sorted(Comparator.comparing(PosicaoAcessoModel::getPosicao).reversed())
				.collect(Collectors.toList());
		return listaOrdenada;
	}

	//ok
	private void addListaPrimeiraMetrica(List<PosicaoAcessoModel> model, PosicaoAcessoModel gatos, PosicaoAcessoModel cachorros, PosicaoAcessoModel tigres) {
		model.add(gatos);
		model.add(cachorros);
		model.add(tigres);
	}

	//ok
	private PosicaoAcessoModel montaPosicaoModel(Integer quantidade, String url) {
		PosicaoAcessoModel model = new PosicaoAcessoModel();
		model.setPosicao(quantidade);
		model.setUrl(url);
		return model;
	}
	
	//ok
	private AcessosModel montaAcessosRegiao(String animal, Integer regiao, Integer quantidade) {
		AcessosModel acessos = new AcessosModel();
		acessos.setAnimal(animal);
		acessos.setRegiao(regiao);
		acessos.setAcessos(quantidade);
		return acessos;
	}

	//ok
	public String consultaSegundaMetrica(Connection connection, LogAcessDao dao) throws SQLException {
		Integer gatosRegiaoUm = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 1");
		Integer gatosRegiaoDois = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 2");
		Integer gatosRegiaoTres = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 3");

		Integer cachorrosRegiaoUm = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 1");
		Integer cachorrosRegiaoDois = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 2");
		Integer cachorrosRegiaoTres = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 3");

		Integer tigresRegiaoUm = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 1");
		Integer tigresRegiaoDois = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 2");
		Integer tigresRegiaoTres = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 3");

		AcessosModel acessosGatoRegiaoUm = montaAcessosRegiao("cat", 1, gatosRegiaoUm);
		AcessosModel acessosGatoRegiaoDois = montaAcessosRegiao("cat", 2, gatosRegiaoDois);
		AcessosModel acessosGatoRegiaoTres = montaAcessosRegiao("cat", 3, gatosRegiaoTres);

		AcessosModel acessosCachorroRegiaoUm = montaAcessosRegiao("dog", 1, cachorrosRegiaoUm);
		AcessosModel acessosCachorroRegiaoDois = montaAcessosRegiao("dog", 2, cachorrosRegiaoDois);
		AcessosModel acessosCachorroRegiaoTres = montaAcessosRegiao("dog", 3, cachorrosRegiaoTres);
		
		AcessosModel acessosTigreRegiaoUm = montaAcessosRegiao("bid", 1, tigresRegiaoUm);
		AcessosModel acessosTigreRegiaoDois = montaAcessosRegiao("bid", 2, tigresRegiaoDois);
		AcessosModel acessosTigreRegiaoTres = montaAcessosRegiao("bid", 3, tigresRegiaoTres);

		List<AcessosModel> acessosRegiaoUm = adicionaAcessosRegiao(acessosGatoRegiaoUm, acessosCachorroRegiaoUm, acessosTigreRegiaoUm);
		List<AcessosModel> acessosRegiaoDois = adicionaAcessosRegiao(acessosGatoRegiaoDois, acessosCachorroRegiaoDois, acessosTigreRegiaoDois);
		List<AcessosModel> acessosRegiaoTres = adicionaAcessosRegiao(acessosGatoRegiaoTres, acessosCachorroRegiaoTres, acessosTigreRegiaoTres);
		
		RegiaoModel regiaoUm = criaRegiao(acessosRegiaoUm);
		RegiaoModel regiaoDois = criaRegiao(acessosRegiaoDois);
		RegiaoModel regiaoTres = criaRegiao(acessosRegiaoTres);

		List<RegiaoModel> listaRegiao = criaListaRegiao(regiaoUm, regiaoDois, regiaoTres);

		RegioesModel regioes = new RegioesModel();
		regioes.setRegiao(listaRegiao);

		List<AcessosModel> listaAcessosRegiaoUm = ordenarListaDecrescente(regioes.getRegiao().get(0));
		List<AcessosModel> listaAcessosRegiaoDois = ordenarListaDecrescente(regioes.getRegiao().get(1));
		List<AcessosModel> listaAcessosRegiaoTres = ordenarListaDecrescente(regioes.getRegiao().get(2));

		String infos = montaRetornoSegundaMetrica(listaAcessosRegiaoUm, listaAcessosRegiaoDois, listaAcessosRegiaoTres);
		return infos;
	}

	//ok
	private String montaRetornoSegundaMetrica(List<AcessosModel> listaAcessosRegiaoUm, List<AcessosModel> listaAcessosRegiaoDois, List<AcessosModel> listaAcessosRegiaoTres) {
		StringBuilder mensagem = new StringBuilder();
		for (AcessosModel acessos : listaAcessosRegiaoUm) {
			mensagem.append("Quantidade de acessos regi�o 1: " + acessos.getAnimal() + "nro: " + acessos.getAcessos() + "\n"); 	  
		}
		for (AcessosModel acessos : listaAcessosRegiaoDois) {
			mensagem.append("Quantidade de acessos regi�o 2: " + acessos.getAnimal() + "nro: " + acessos.getAcessos() + "\n"); 	  
		}
		for (AcessosModel acessos : listaAcessosRegiaoTres) {
			mensagem.append("Quantidade de acessos regi�o 3: " + acessos.getAnimal() + "nro: " + acessos.getAcessos() + "\n"); 	  
		}
		return mensagem.toString();
	}

	//ok
	private List<RegiaoModel> criaListaRegiao(RegiaoModel regiaoUm, RegiaoModel regiaoDois, RegiaoModel regiaoTres) {
		List<RegiaoModel> listaRegiao = new ArrayList<RegiaoModel>();
		listaRegiao.add(regiaoUm);
		listaRegiao.add(regiaoDois);
		listaRegiao.add(regiaoTres);
		return listaRegiao;
	}

	//ok
	private RegiaoModel criaRegiao(List<AcessosModel> acessosRegiao) {
		RegiaoModel regiao = new RegiaoModel();
		regiao.setAcessos(acessosRegiao);
		return regiao;
	}

	//ok
	private List<AcessosModel> adicionaAcessosRegiao(AcessosModel acessosGatoRegiao, AcessosModel acessosCachorroRegiao, AcessosModel acessosTigreRegiao) {
		List<AcessosModel> acessosRegiao = new ArrayList<AcessosModel>();
		acessosRegiao.add(acessosGatoRegiao);
		acessosRegiao.add(acessosCachorroRegiao);
		acessosRegiao.add(acessosTigreRegiao);
		return acessosRegiao;
	}

	//ok
	private List<AcessosModel> ordenarListaDecrescente(RegiaoModel regiao) {
		List<AcessosModel> listaOrdenada = regiao.getAcessos().stream()
				.sorted(Comparator.comparing(AcessosModel::getAcessos).reversed())
				.collect(Collectors.toList()); 
		return listaOrdenada;
	}

	//ok
	public String consultaTerceiraMetrica(Connection connection, LogAcessDao dao) throws SQLException{
		Integer quantidadeGato = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%'");
		Integer quantidadeCachorro = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'");
		Integer quantidadeTigre = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%'");
		String mensagem = "Cat: " + quantidadeGato + "///// Dog: " + quantidadeCachorro + "/////bids: " + quantidadeTigre;
		return mensagem;
	}

	//ok
	public String consultaQuartaMetrica(Connection connection, LogAcessDao dao, String data) throws SQLException, ParseException {
		Timestamp timestamp = montaTimestamp(data);

		Integer quantidadeGato = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/exotic/cats/10%' and timestamp like '%" + timestamp.getTime() + "%'");
		Integer quantidadeCachorro = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and timestamp = " + timestamp.getTime());
		Integer quantidadeTigre = dao.consultaDadosUrl(connection, "select count(*) from logs where url like '%/tiggers/bid/now%' and timestamp = " + timestamp.getTime());

		List<PosicaoAcessoModel> model = new ArrayList<PosicaoAcessoModel>();

		PosicaoAcessoModel quantidadeAcessosGato = montaPosicaoModel(quantidadeGato, "/pets/exotic/cats/10");
		PosicaoAcessoModel quantidadeAcessosCachorro = montaPosicaoModel(quantidadeCachorro, "/pets/guaipeca/dogs/1");
		PosicaoAcessoModel quantidadeAcessosTigre = montaPosicaoModel(quantidadeTigre, "/tiggers/bid/now");
		
		addListaPrimeiraMetrica(model, quantidadeAcessosGato, quantidadeAcessosCachorro, quantidadeAcessosTigre);

		List<PosicaoAcessoModel> listaOrdenada = model.stream()
				.sorted(Comparator.comparing(PosicaoAcessoModel::getPosicao).reversed())
				.collect(Collectors.toList()); 

		String infos = montaRetornoQuartaMetrica(data, listaOrdenada);

		return infos;
	}

	//ok
	private String montaRetornoQuartaMetrica(String data, List<PosicaoAcessoModel> listaOrdenada) {
		String infos;
		StringBuilder mensagem = new StringBuilder();
		for (PosicaoAcessoModel model : listaOrdenada) {
			if (model.getPosicao() != null && model.getUrl() != null) {
				infos = "A url: " + model.getUrl() + "ficou na posi�ao: " + model.getPosicao() + "no dia" + data;
				mensagem.append(infos);
			}
		}
		return mensagem.toString();
	}

	//ok
	private Timestamp montaTimestamp(String data) throws ParseException {
		String novaData = data.replace("data=", "").replace("-", "/");
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date)formatter.parse(novaData);
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	//ok
	public String consultaQuintaMetrica(Connection connection, LogAcessDao dao) throws SQLException{
		List<Long> listaTimestamp = dao.consultaDadosTimestamp(connection, "select timestamp from logs");
		List<AcessosMinutoModel> acessosMinuto = criaListaMinutos();
		for (Long timestamp : listaTimestamp) {
			Integer minuto = verificaMinutoTimestamp(timestamp);
			montaAcessoMinuto(acessosMinuto, minuto);
		}
		return null;
	}

	//ok
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

	//ok
	private Integer verificaMinutoTimestamp(Long timestamp) {
		Timestamp t = new Timestamp(timestamp);
		Date data = new Date(t.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		Integer minuto = calendar.get(Calendar.MINUTE);
		return minuto;
	}

	//ok
	private List<AcessosMinutoModel> criaListaMinutos() {
		List<AcessosMinutoModel> acessosMinuto = new ArrayList<AcessosMinutoModel>();
		for (int i=0; i<=59; i++) {		
			AcessosMinutoModel acessoMinuto = new AcessosMinutoModel();
			acessoMinuto.setMinuto(i);
			acessosMinuto.add(acessoMinuto);
		}
		return acessosMinuto;
	}

	//ok
	public Boolean salvarLog(LogModel log) {
		LogAcessDao dao = new LogAcessDao();
		Boolean response = dao.salvarLog(log);
		return response;
	}

}