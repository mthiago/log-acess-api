package br.com.java.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.com.java.dao.LogAcessDao;
import br.com.java.model.AcessosModel;
import br.com.java.model.LogModel;
import br.com.java.model.PosicaoAcessoResponse;
import br.com.java.model.RegiaoModel;

class TesteLogAcessService {

	/*
	@Test
	public void testeSalvarLog() {
		LogAcessDao logAcessDao = new LogAcessDao();
		LogModel log = new LogModel();
		log.setRegion(1);
		log.setTimestamp(Long.valueOf(15134141));
		log.setUrl("/cats/1");
		log.setUserId("a123ad");
		Boolean response = logAcessDao.salvarLog(log);
		assert(response == true);
	}
	
	@Test
	public void testeConsultaMetricas() {
		String primeiraMetrica = "primeira métrica/";
		String segundaMetrica = "segunda métrica/";
		String terceiraMetrica = "terceira métrica/";
		String quartaMetrica = "quarta métrica/";
		String quintaMetrica = "quinta métrica/";
		String metricas = primeiraMetrica + segundaMetrica + terceiraMetrica + quartaMetrica + quintaMetrica;
		assert(metricas.equals("primeira métrica/segunda métrica/terceira métrica/quarta métrica/quinta métrica"));
	}
	
	@Test
	public void TesteConsultaPrimeiraMetrica() {
		Integer catsInteger = 1;
		Integer dogsInteger = 2;
		Integer bidInteger = 3;

		List<PosicaoAcessoResponse> response = new ArrayList<PosicaoAcessoResponse>();
		
		PosicaoAcessoResponse cat = new PosicaoAcessoResponse();
		cat.setPosition(1);
		cat.setUrl("cats");
		
		PosicaoAcessoResponse dog = new PosicaoAcessoResponse();
		dog.setPosition(2);
		dog.setUrl("dogs");
		
		PosicaoAcessoResponse bid = new PosicaoAcessoResponse();
		dog.setPosition(3);
		dog.setUrl("bid");

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

	@Test
	public void buildInfos() {
		PosicaoAcessoResponse metricResponse = new PosicaoAcessoResponse();
		metricResponse.setPosition(1);
		metricResponse.setUrl("cats");
		assert(metricResponse.getPosition().equals(1) && metricResponse.getUrl().equals("cats"));
	}
	
	@Test
	public void buildAcessosRegiao() {
		AcessosModel acessos = new AcessosModel();
		acessos.setAnimal("gato");
		acessos.setRegiao(1);
		acessos.setAcessos(2);
		assert(acessos.getAnimal().equals("gato") && acessos.getRegiao().equals(1) && acessos.getAcessos().equals(2));
	}
	*/
	
	@Test
	public void ordenarListaDecrescente() {
		RegiaoModel regiao = new RegiaoModel();
		AcessosModel acessos1 = new AcessosModel();
		acessos1.setAcessos(1);
		AcessosModel acessos2 = new AcessosModel();
		acessos2.setAcessos(2);
		List<AcessosModel> acessosModel = new ArrayList<AcessosModel>();
		acessosModel.add(acessos1);
		acessosModel.add(acessos2);
		regiao.setAcessos(acessosModel);

		List<AcessosModel> listaOrdenada = regiao.getAcessos().stream()
				.sorted(Comparator.comparing(AcessosModel::getAcessos).reversed())
				.collect(Collectors.toList()); 
		assert(listaOrdenada.get(0).getAcessos().equals(2));
	}
	
	@Test
	public void testeConsultaTerceiraMetrica() {
		Integer catsInteger = 1;
		Integer dogsInteger = 2;
		Integer bidsInteger = 3;
		String msgReturn = "Cat: " + catsInteger + "///// Dog: " + dogsInteger + "/////bids: " + bidsInteger;
		assert(msgReturn.equals("Cat: 1///// Dog: 2/////bids: 3"));
	}
	
	@Test
	public void testeConsultaQuartaMetrica() {
		String data = "11/02/1964";
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date)formatter.parse(data);

		Timestamp timestamp = new Timestamp(date.getTime());
		Integer catsInteger = 1;
		Integer dogsInteger = 2;
		Integer bidsInteger = 3;


		List<PosicaoAcessoResponse> response = new ArrayList<PosicaoAcessoResponse>();

		PosicaoAcessoResponse cat = new PosicaoAcessoResponse();
		PosicaoAcessoResponse dog = new PosicaoAcessoResponse();
		PosicaoAcessoResponse bid = new PosicaoAcessoResponse();
		
		cat.setPosition(2);
		cat.setUrl("/pets/exotic/cats/10");
		
		dog.setPosition(1);
		dog.setUrl("/pets/guaipeca/dogs/1");
		
		bid.setPosition(0);
		bid.setUrl("/tiggers/bid/now");


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
	
}
