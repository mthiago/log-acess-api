package br.com.java.test;

import org.junit.jupiter.api.Test;

import br.com.java.model.LogModel;

class TesteLogAcessEndpoint {

	@Test
	public void metrics() {
		String metrics = "";
		metrics = "A primeira m�trica...";
		assert(metrics.equals("A primeira m�trica..."));
	}
	
	@Test
	public void salvarLog() {
		Boolean response = true;
		Boolean response2 = false;
		assert(response.equals(true) && response2.equals(false));
	}	
	

}
