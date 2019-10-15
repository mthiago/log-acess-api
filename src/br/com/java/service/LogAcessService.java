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
			metricas = primeiraMetrica + "\n" + segundaMetrica + "\n" + terceiraMetrica + "\n" + quartaMetrica +"\n" + quintaMetrica;
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

		StringBuilder mensagem = montaRespPrimeiraMetrica(listaOrdenada);

		return mensagem.toString();
	}

	//ok
	private StringBuilder montaRespPrimeiraMetrica(List<PosicaoAcessoModel> listaOrdenada) {
		String texto = "As urls mais acessadas no mundo inteiro são: \n";
		StringBuilder mensagem = new StringBuilder();
		mensagem.append(texto);
		for (PosicaoAcessoModel model : listaOrdenada) {
			if (model.getPosicao() != null && model.getUrl() != null) {
				String infos = model.getUrl() + " - " + model.getPosicao() + " acessos\n";
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

		List<AcessosModel> listaAcessosRegiaoUm = ordenarListaDecrescente(regiaoUm);
		List<AcessosModel> listaAcessosRegiaoDois = ordenarListaDecrescente(regiaoDois);
		List<AcessosModel> listaAcessosRegiaoTres = ordenarListaDecrescente(regiaoTres);

		String infos = montaRetornoSegundaMetrica(listaAcessosRegiaoUm, listaAcessosRegiaoDois, listaAcessosRegiaoTres);
		return infos;
	}

	//ok
	private String montaRetornoSegundaMetrica(List<AcessosModel> listaAcessosRegiaoUm, List<AcessosModel> listaAcessosRegiaoDois, List<AcessosModel> listaAcessosRegiaoTres) {
		String texto = "Quantidade de acessos por região: \n";
		String mensagemRegiaoUm = montaMensagemSegundaMetrica(listaAcessosRegiaoUm, 1);
		String mensagemRegiaoDois = montaMensagemSegundaMetrica(listaAcessosRegiaoDois, 2);
		String mensagemRegiaoTres = montaMensagemSegundaMetrica(listaAcessosRegiaoTres, 3);
		return texto + mensagemRegiaoUm + mensagemRegiaoDois + mensagemRegiaoTres;
	}

	private String montaMensagemSegundaMetrica(List<AcessosModel> listaAcessosRegiao, Integer regiao) {
		String mensagem = "";
		for (AcessosModel acessos : listaAcessosRegiao) {
			mensagem+= "A região " + regiao + " teve " + acessos.getAcessos() + " para a url " + acessos.getAnimal() + "\n"; 	  
		}
		return mensagem;
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

		List<PosicaoAcessoModel> listaPosicaoAcesso = new ArrayList<PosicaoAcessoModel>();
		
		PosicaoAcessoModel quantidadeAcessosGato = montaPosicaoModel(quantidadeGato, "/pets/exotic/cats/10");
		PosicaoAcessoModel quantidadeAcessosCachorro = montaPosicaoModel(quantidadeCachorro, "/pets/guaipeca/dogs/1");
		PosicaoAcessoModel quantidadeAcessosTigre = montaPosicaoModel(quantidadeTigre, "/tiggers/bid/now");

		addListaPrimeiraMetrica(listaPosicaoAcesso, quantidadeAcessosGato, quantidadeAcessosCachorro, quantidadeAcessosTigre);
		
		List<PosicaoAcessoModel> listaOrdenada = listaPosicaoAcesso.stream()
				.sorted(Comparator.comparing(PosicaoAcessoModel::getPosicao))
				.collect(Collectors.toList());
		
		String urlMenosAcessada = listaOrdenada.get(0).getUrl();
		Integer quantidadeAcessos = listaOrdenada.get(0).getPosicao();
		
		return "A url com menos acesso no mundo inteiro é " + urlMenosAcessada + " e possui " + quantidadeAcessos + " acessos \n";
	}

	//ok
	public String consultaQuartaMetrica(Connection connection, LogAcessDao dao, String data) throws SQLException, ParseException {
		String novaData = data.replace("data=", "").replace("-", "/");
		Timestamp timestamp = montaTimestamp(novaData);

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

		return montaRetornoQuartaMetrica(novaData, listaOrdenada);
	}

	//ok
	private String montaRetornoQuartaMetrica(String data, List<PosicaoAcessoModel> listaOrdenada) {
		String texto = "As urls mais acessadas no dia " + data + " são: \n";
		StringBuilder mensagem = new StringBuilder();
		mensagem.append(texto);
		for (PosicaoAcessoModel model : listaOrdenada) {
			if (model.getPosicao() != null && model.getUrl() != null) {
				mensagem.append(model.getPosicao() + " acessos: " + model.getUrl() + "\n");
			}
		}
		return mensagem.toString();
	}

	//ok
	private Timestamp montaTimestamp(String data) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date)formatter.parse(data);
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
		Integer minutoMaisAcesso = montaRetornoQuintaMetrica(acessosMinuto);
		return "O minuto com mais acessos de todas urls é " + minutoMaisAcesso;
	}

	private Integer montaRetornoQuintaMetrica(List<AcessosMinutoModel> acessosMinuto) {
		Integer minutoMaisAcesso = 0;
		for (AcessosMinutoModel acessos : acessosMinuto) {
			if (acessos.getQuantidade() != null) {
				if (acessos.getQuantidade() > minutoMaisAcesso) {
					minutoMaisAcesso = acessos.getQuantidade();
				}
			}
		}
		return minutoMaisAcesso;
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