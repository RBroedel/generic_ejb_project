package br.com.itix.comissoescnu.mq;

import java.io.IOException;
import java.util.Properties;

import javax.ejb.Singleton;

@Singleton
public class QueueNames {

	public final String QUEUE_LOAD_CONTRATOS;
	public final String QUEUE_LOAD_RUBRICAS;
	public final String QUEUE_LOAD_UNIDADE_NEGOCIO;
	public final String QUEUE_LOAD_EMPRESAS;
	public final String QUEUE_LOAD_BENEFICIARIOS;
	public final String QUEUE_LOAD_TIPO_PRODUTO;
	public final String QUEUE_LOAD_PRODUTO;
	public final String QUEUE_LOAD_FATURAS_PAGAS;

	private final Properties properties = new Properties();

	public QueueNames() throws IOException {
		properties.load( getClass().getClassLoader().getResourceAsStream( "queue.properties" ) );
		QUEUE_LOAD_CONTRATOS = properties.getProperty( "QUEUE_LOAD_CONTRATOS" );
		QUEUE_LOAD_RUBRICAS = properties.getProperty( "QUEUE_LOAD_RUBRICAS" );
		QUEUE_LOAD_UNIDADE_NEGOCIO = properties.getProperty( "QUEUE_LOAD_UNIDADE_NEGOCIO" );
		QUEUE_LOAD_EMPRESAS = properties.getProperty( "QUEUE_LOAD_EMPRESAS" );
		QUEUE_LOAD_BENEFICIARIOS = properties.getProperty( "QUEUE_LOAD_BENEFICIARIOS" );
		QUEUE_LOAD_TIPO_PRODUTO = properties.getProperty( "QUEUE_LOAD_TIPO_PRODUTO" );
		QUEUE_LOAD_PRODUTO = properties.getProperty( "QUEUE_LOAD_PRODUTO" );
		QUEUE_LOAD_FATURAS_PAGAS = properties.getProperty( "QUEUE_LOAD_FATURAS_PAGAS" );
	}

}
