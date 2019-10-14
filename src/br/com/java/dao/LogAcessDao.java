package br.com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import br.com.java.database.DatabaseConnection;
import br.com.java.model.AcessosMinutoModel;
import br.com.java.model.AcessosModel;
import br.com.java.model.LogModel;
import br.com.java.model.Metric1Response;
import br.com.java.model.RegiaoModel;
import br.com.java.model.RegioesModel;
import br.com.java.model.TimestampModel;

public class LogAcessDao {

	public Boolean salvarLog(LogModel request) {
		PreparedStatement pstmt = null;
		try {
			Connection connection = DatabaseConnection.getConnection();
			String query = "INSERT INTO LOGS(url, timestamp, userId, region) VALUES(?, ?, ?, ?)";
			pstmt = connection.prepareStatement(query);

			if (request.getUrl() != null) pstmt.setString(1, request.getUrl());
			if (request.getTimestamp() != null) pstmt.setLong(2, request.getTimestamp());
			if (request.getUserId() != null) pstmt.setString(3, request.getUserId());
			if (request.getRegion() != null) pstmt.setInt(4, request.getRegion());

			pstmt.executeUpdate();
			pstmt.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Integer> consultaPrimeiraMetrica(Connection connection) {
		List<Integer> results = new ArrayList<Integer>();
		try {
			Integer catsInteger = consultaCatsPrimeiraMetrica(connection); 
			Integer dogsInteger = consultaDogsPrimeiraMetrica(connection);
			Integer bidInteger = consultaBidPrimeiraMetrica(connection);
			results.add(catsInteger);
			results.add(dogsInteger);
			results.add(bidInteger);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return results;
	}
	
	public Integer consultaCatsPrimeiraMetrica(Connection connection) throws SQLException {
		String query = "select count(*) from logs where url like '%/pets/exotic/cats/10%'";
		PreparedStatement Stmt = connection.prepareStatement(query);
		ResultSet rs = Stmt.executeQuery();
		rs.next();
		String metricCats = rs.getString(1);
		Integer catsInteger = Integer.valueOf(metricCats);
		Stmt.close();
		return catsInteger;
	}
	
	public Integer consultaDogsPrimeiraMetrica(Connection connection) throws SQLException {
		String query = "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'";
		PreparedStatement Stmt = connection.prepareStatement(query);
		ResultSet rs = Stmt.executeQuery();
		rs.next();
		String metricDogs = rs.getString(1);
		Integer dogsInteger = Integer.valueOf(metricDogs);
		Stmt.close();
		return dogsInteger;
	}
	
	public Integer consultaBidPrimeiraMetrica(Connection connection) throws SQLException {
		String query = "select count(*) from logs where url like '%/tiggers/bid/now%'";
		PreparedStatement Stmt = connection.prepareStatement(query);
		ResultSet rs = Stmt.executeQuery();
		rs.next();
		String metricBid = rs.getString(1);
		Integer bidInteger = Integer.valueOf(metricBid);
		Stmt.close();
		return bidInteger;
	}
	
	public String consultaSegundaMetrica(Connection connection) {

		String msgReturn = "";
		try {
			String query = "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 1";


			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//PEGANDO CONTAGEM DE VISITANTES
			PreparedStatement Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricCatsRegion1 = rs.getString(1);
			Integer catsIntegerRegion1 = Integer.valueOf(metricCatsRegion1);

			Stmt.close();


			query = "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 2";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricCatsRegion2 = rs.getString(1);
			Integer catsIntegerRegion2 = Integer.valueOf(metricCatsRegion2);


			Stmt.close();


			query = "select count(*) from logs where url like '%/pets/exotic/cats/10%' and region = 3";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricCatsRegion3 = rs.getString(1);
			Integer catsIntegerRegion3 = Integer.valueOf(metricCatsRegion3);


			Stmt.close();


			query = "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 1";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricDogsRegion1 = rs.getString(1);
			Integer dogsIntegerRegion1 = Integer.valueOf(metricDogsRegion1);

			Stmt.close();	           

			query = "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 2";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricDogsRegion2 = rs.getString(1);
			Integer dogsIntegerRegion2 = Integer.valueOf(metricDogsRegion2);

			Stmt.close();		           


			query = "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and region = 3";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricDogsRegion3 = rs.getString(1);
			Integer dogsIntegerRegion3 = Integer.valueOf(metricDogsRegion3);

			Stmt.close();		           



			query = "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 1";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricBidsRegion1 = rs.getString(1);
			Integer bidsIntegerRegion1 = Integer.valueOf(metricBidsRegion1);

			Stmt.close();		           





			query = "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 2";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricBidsRegion2 = rs.getString(1);
			Integer bidsIntegerRegion2 = Integer.valueOf(metricBidsRegion2);

			Stmt.close();		           	           



			query = "select count(*) from logs where url like '%/tiggers/bid/now%' and region = 3";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricBidsRegion3 = rs.getString(1);
			Integer bidsIntegerRegion3 = Integer.valueOf(metricBidsRegion3);

			Stmt.close();		           	           


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
			acessosCatReg1.setAcessos(catsIntegerRegion1);
			acessosReg1.add(acessosCatReg1);





			AcessosModel acessosCatReg2 = new AcessosModel();
			acessosCatReg2.setAnimal("cat");
			acessosCatReg2.setRegiao(2);
			acessosCatReg2.setAcessos(catsIntegerRegion2);
			acessosReg2.add(acessosCatReg2);

			AcessosModel acessosCatReg3 = new AcessosModel();
			acessosCatReg3.setAnimal("cat");
			acessosCatReg3.setRegiao(3);
			acessosCatReg3.setAcessos(catsIntegerRegion3);
			acessosReg3.add(acessosCatReg3);

			AcessosModel acessosDogReg1 = new AcessosModel();
			acessosDogReg1.setAnimal("dog");
			acessosDogReg1.setRegiao(1);
			acessosDogReg1.setAcessos(dogsIntegerRegion1);
			acessosReg1.add(acessosDogReg1);           

			AcessosModel acessosDogReg2 = new AcessosModel();
			acessosDogReg2.setAnimal("dog");
			acessosDogReg2.setRegiao(2);
			acessosDogReg2.setAcessos(dogsIntegerRegion2);
			acessosReg2.add(acessosDogReg2);

			AcessosModel acessosDogReg3 = new AcessosModel();
			acessosDogReg3.setAnimal("dog");
			acessosDogReg3.setRegiao(1);
			acessosDogReg3.setAcessos(dogsIntegerRegion3);
			acessosReg3.add(acessosDogReg3);

			AcessosModel acessosBidReg1 = new AcessosModel();
			acessosBidReg1.setAnimal("bid");
			acessosBidReg1.setRegiao(1);
			acessosBidReg1.setAcessos(bidsIntegerRegion1);
			acessosReg1.add(acessosBidReg1);



			AcessosModel acessosBidReg2 = new AcessosModel();
			acessosBidReg2.setAnimal("bid");
			acessosBidReg2.setRegiao(2);
			acessosBidReg2.setAcessos(bidsIntegerRegion2);
			acessosReg2.add(acessosBidReg2);

			AcessosModel acessosBidReg3 = new AcessosModel();
			acessosBidReg3.setAnimal("bid");
			acessosBidReg3.setRegiao(2);
			acessosBidReg3.setAcessos(bidsIntegerRegion3);
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


			msgReturn = msg.toString();
			/*	          
	          StringBuilder msg = new StringBuilder();
	      for (Metric1Response r : ordenada) {
	    	  if (r.getPosition() != null && r.getUrl() != null) {
	    		  infos = "A url mais acessada no mundo é " + r.getUrl() + " e ela teve " + r.getPosition() + " acessos\n";
	    		  msg.append(infos);
	    	  }
	      }
			 */
			//msgReturn = msg.toString();
		}
		catch (Exception e) {

		}

		return msgReturn;

	}
	
	public String consultaTerceiraMetrica(Connection connection) {
		String msgReturn = "";
		try {
			String query = "select count(*) from logs where url like '%/pets/exotic/cats/10%'";


			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//PEGANDO CONTAGEM DE VISITANTES
			PreparedStatement Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricCatsRegion1 = rs.getString(1);
			Integer catsInteger = Integer.valueOf(metricCatsRegion1);

			Stmt.close();



			query = "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricdogsRegion1 = rs.getString(1);
			Integer dogsInteger = Integer.valueOf(metricdogsRegion1);

			Stmt.close();


			query = "select count(*) from logs where url like '%/tiggers/bid/now%'";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String d1dw = rs.getString(1);
			Integer bidsInteger = Integer.valueOf(d1dw);

			Stmt.close();	       


			msgReturn = "Cat: " + catsInteger + "///// Dog: " + dogsInteger + "/////bids: " + bidsInteger;



		} catch (Exception e) {

		}
		return msgReturn;
	}
	
	public String consultaQuartaMetrica(Connection connection, String data) {

		String msgReturn = "";

		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = (Date)formatter.parse(data);

			Timestamp timestamp = new Timestamp(date.getTime());



			String query = "select count(*) from logs where url like '%/pets/exotic/cats/10%' and timestamp like '%" + timestamp.getTime() + "%'";


			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//PEGANDO CONTAGEM DE VISITANTES
			PreparedStatement Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricCats = rs.getString(1);
			Integer catsInteger = Integer.valueOf(metricCats);

			Stmt.close();





			query = "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%' and timestamp = " + timestamp.getTime();

			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricDogs = rs.getString(1);
			Integer dogsInteger = Integer.valueOf(metricDogs);

			Stmt.close();




			query = "select count(*) from logs where url like '%/tiggers/bid/now%' and timestamp = " + timestamp.getTime();

			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricBids = rs.getString(1);
			Integer bidsInteger = Integer.valueOf(metricBids);

			Stmt.close();







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
		catch (Exception e) {

		}

		return msgReturn;



	}
	
	
	public String consultaQuintaMetrica(Connection connection) {

		String msgReturn = "";
		try {



			String query = "select timestamp from logs";


			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//PEGANDO CONTAGEM DE VISITANTES
			PreparedStatement Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();


			TimestampModel Alltimestamp = new TimestampModel();
			List<Long> asdadqd = new ArrayList<>();
			while (rs.next()) {
				Long ada = rs.getLong("timestamp");

				asdadqd.add(ada);
			}


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

			return "a";

		} catch (Exception e) {

		}
		return msgReturn;
	}

}
