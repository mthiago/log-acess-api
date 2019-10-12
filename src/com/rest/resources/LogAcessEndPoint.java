package com.rest.resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import request.LogRequest;

@Path("/log-acess")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogAcessEndPoint {

	@GET	
	@Path("/laar/metrics3/{data}")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public String metrics3(@PathParam("data") String data) {

		String novaData = data.replace("data=", "").replace("-", "/");


		try {

			Connection connection = DatabaseConnection.getConexaoMySQL();
			String quartaMetrica = consultaQuartaMetrica(connection, novaData);


			return quartaMetrica;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	

	private String consultaQuartaMetrica(Connection connection, String data) {

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

	@GET
	@Path("/laar/metrics")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public String metrics() {



		try {

			Connection connection = DatabaseConnection.getConexaoMySQL();
			//String primeiraMetrica = consultaPrimeiraMetrica(connection);
			//String segundaMetrica = consultaSegundaMetrica(connection);
			//String terceiraMetrica = consultaTerceiraMetrica(connection);
			String quintaMetrica = consultaQuintaMetrica(connection);

			return quintaMetrica;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String consultaQuintaMetrica(Connection connection) {

		String msgReturn = "";
		try {



			String query = "select timestamp from logs";


			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//PEGANDO CONTAGEM DE VISITANTES
			PreparedStatement Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();


			AllTimestamp Alltimestamp = new AllTimestamp();
			List<Long> asdadqd = new ArrayList<>();
			while (rs.next()) {
				Long ada = rs.getLong("timestamp");

				asdadqd.add(ada);
			}


			List<AcessosMinuto> acessosMinuto = new ArrayList<AcessosMinuto>();
			for (int i=0; i<=59; i++) {
				AcessosMinuto acessoMinuto = new AcessosMinuto();
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



					for (AcessosMinuto asdqdq : acessosMinuto) {
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

	private String consultaTerceiraMetrica(Connection connection) {
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

	private String consultaSegundaMetrica(Connection connection) {

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


			Regioes regioes = new Regioes();
			List<Regiao> listRegiao = new ArrayList<Regiao>();
			Regiao regiao1 = new Regiao();
			Regiao regiao2 = new Regiao();
			Regiao regiao3 = new Regiao();

			List<Acessos> acessosReg1 = new ArrayList<Acessos>();
			List<Acessos> acessosReg2 = new ArrayList<Acessos>();
			List<Acessos> acessosReg3 = new ArrayList<Acessos>();

			Acessos acessosCatReg1 = new Acessos();
			acessosCatReg1.setAnimal("cat");
			acessosCatReg1.setRegiao(1);
			acessosCatReg1.setAcessos(catsIntegerRegion1);
			acessosReg1.add(acessosCatReg1);





			Acessos acessosCatReg2 = new Acessos();
			acessosCatReg2.setAnimal("cat");
			acessosCatReg2.setRegiao(2);
			acessosCatReg2.setAcessos(catsIntegerRegion2);
			acessosReg2.add(acessosCatReg2);

			Acessos acessosCatReg3 = new Acessos();
			acessosCatReg3.setAnimal("cat");
			acessosCatReg3.setRegiao(3);
			acessosCatReg3.setAcessos(catsIntegerRegion3);
			acessosReg3.add(acessosCatReg3);

			Acessos acessosDogReg1 = new Acessos();
			acessosDogReg1.setAnimal("dog");
			acessosDogReg1.setRegiao(1);
			acessosDogReg1.setAcessos(dogsIntegerRegion1);
			acessosReg1.add(acessosDogReg1);           

			Acessos acessosDogReg2 = new Acessos();
			acessosDogReg2.setAnimal("dog");
			acessosDogReg2.setRegiao(2);
			acessosDogReg2.setAcessos(dogsIntegerRegion2);
			acessosReg2.add(acessosDogReg2);

			Acessos acessosDogReg3 = new Acessos();
			acessosDogReg3.setAnimal("dog");
			acessosDogReg3.setRegiao(1);
			acessosDogReg3.setAcessos(dogsIntegerRegion3);
			acessosReg3.add(acessosDogReg3);

			Acessos acessosBidReg1 = new Acessos();
			acessosBidReg1.setAnimal("bid");
			acessosBidReg1.setRegiao(1);
			acessosBidReg1.setAcessos(bidsIntegerRegion1);
			acessosReg1.add(acessosBidReg1);



			Acessos acessosBidReg2 = new Acessos();
			acessosBidReg2.setAnimal("bid");
			acessosBidReg2.setRegiao(2);
			acessosBidReg2.setAcessos(bidsIntegerRegion2);
			acessosReg2.add(acessosBidReg2);

			Acessos acessosBidReg3 = new Acessos();
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




			List<Acessos> accreg1 = regioes.getRegiao().get(0).getAcessos().stream()
					.sorted(Comparator.comparing(Acessos::getAcessos).reversed())
					.collect(Collectors.toList()); 

			List<Acessos> accreg2 = regioes.getRegiao().get(0).getAcessos().stream()
					.sorted(Comparator.comparing(Acessos::getAcessos).reversed())
					.collect(Collectors.toList()); 

			List<Acessos> accreg3 = regioes.getRegiao().get(0).getAcessos().stream()
					.sorted(Comparator.comparing(Acessos::getAcessos).reversed())
					.collect(Collectors.toList()); 	          

			String infos = "";

			StringBuilder msg = new StringBuilder();



			for (Acessos ac : accreg1) {
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

	@POST
	@Path("/laar/ingest")
	@Produces("application/json;charset=utf-8")
	@Consumes("application/json")
	public Boolean salvarLog(LogRequest request) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Connection connection = DatabaseConnection.getConexaoMySQL();

			// the mysql insert statement
			String query = "INSERT INTO LOGS(url, timestamp, userId, region) VALUES(?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			pstmt = connection.prepareStatement(query);

			pstmt.setString(1, request.getUrl());
			pstmt.setLong(2, request.getTimestamp());
			pstmt.setString(3, request.getUserId());
			pstmt.setInt(4, request.getRegion());
			pstmt.executeUpdate();
			pstmt.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String consultaPrimeiraMetrica(Connection connection){
		String msgReturn = "";
		try {
			String query = "select count(*) from logs where url like '%/pets/exotic/cats/10%'";


			PreparedStatement pstmt = null;
			ResultSet rs = null;
			//PEGANDO CONTAGEM DE VISITANTES
			PreparedStatement Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricCats = rs.getString(1);
			Integer catsInteger = Integer.valueOf(metricCats);

			Stmt.close();


			query = "select count(*) from logs where url like '%/pets/guaipeca/dogs/1%'";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricDogs = rs.getString(1);
			Integer dogsInteger = Integer.valueOf(metricDogs);


			Stmt.close();


			query = "select count(*) from logs where url like '%/tiggers/bid/now%'";


			//PEGANDO CONTAGEM DE VISITANTES
			Stmt = connection.prepareStatement(query);

			rs = Stmt.executeQuery();
			rs.next();
			String metricBid = rs.getString(1);
			Integer bidInteger = Integer.valueOf(metricBid);


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
		}
		catch (Exception e) {

		}

		return msgReturn;
	}

}