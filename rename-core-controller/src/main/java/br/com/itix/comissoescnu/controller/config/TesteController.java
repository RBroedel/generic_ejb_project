package br.com.itix.comissoescnu.controller.config;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path( "teste" )
public class TesteController {

	@GET
	@Path( "/" )
	public String teste() {
		return "Teste";
	}
}
