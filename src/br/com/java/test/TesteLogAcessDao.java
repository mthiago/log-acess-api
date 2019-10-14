package br.com.java.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TesteLogAcessDao {

	@Test
	public void TesteConsultaDadosUrl() {
		String retorno = "1";
		Integer quantidade = Integer.valueOf(retorno);
		assert(quantidade.equals(1));		
	}
	
	public void TesteConsultaDadosTimestamp() {
		List<Long> listTimestamp = new ArrayList<>();
		Long timestamp = Long.valueOf(124123123);
		listTimestamp.add(timestamp);
		assert(listTimestamp.get(0).equals(Long.valueOf(124123123)));
	}
	
}
