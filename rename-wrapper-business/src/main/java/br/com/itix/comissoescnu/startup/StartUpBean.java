package br.com.itix.comissoescnu.startup;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;

import br.com.itix.comissoescnu.facade.ContratoFacade;
import br.com.itix.comissoescnu.facade.BeneficiarioFacade;
import br.com.itix.comissoescnu.facade.EmpresaFacade;
import br.com.itix.comissoescnu.facade.FaturasPagasFacade;
import br.com.itix.comissoescnu.facade.ProdutoFacade;
import br.com.itix.comissoescnu.facade.RubricaFacade;
import br.com.itix.comissoescnu.facade.TipoProdutoFacade;
import br.com.itix.comissoescnu.facade.UnidadeNegocioFacade;

@Startup
@Singleton
@DependsOn( { "QueueNames" } )
public class StartUpBean {

	@Resource
	ManagedScheduledExecutorService scheduler;

	@EJB
	private EmpresaFacade empresaFacade;

	@EJB
	private ContratoFacade contratoFacade;

	@EJB
	private RubricaFacade rubricaFacade;

	@EJB
	private UnidadeNegocioFacade unidadeNegocioFacade;

	@EJB
	private ProdutoFacade produtoFacade;

	@EJB
	private TipoProdutoFacade tipoProdutoFacade;

	@EJB
	private BeneficiarioFacade beneficiarioFacade;

	@EJB
	private FaturasPagasFacade faturasPagasFacade;

	@PostConstruct
	public void startAccess() {
		filasEntidades();
	}

	private void filasEntidades() {
		try {
			this.scheduler.scheduleAtFixedRate( () -> {
				if( Boolean.valueOf( System.getProperty( "comissao-wrapper.loadLegado" ) ) ) {
					System.out.println( "-----> wrapper - iniciar queues!" );

					ThreadPoolExecutor executor = new ThreadPoolExecutor( 2, 3, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy() );

					executor.execute( () -> empresaFacade.load() );
					executor.execute( () -> contratoFacade.load() );
					executor.execute( () -> rubricaFacade.load() );
					executor.execute( () -> unidadeNegocioFacade.load() );
					executor.execute( () -> produtoFacade.load() );
					executor.execute( () -> tipoProdutoFacade.load() );
					executor.execute( () -> beneficiarioFacade.load() );
					executor.execute( () -> faturasPagasFacade.load() );
					executor.shutdown();

				}

			}, 1, 5, TimeUnit.MINUTES ); /* retardo de 1 minuto para dar tempo de todos os serviços iniciarem. */
		} catch ( Exception e ) {

			e.printStackTrace();
			System.out.println( "-----> wrapper - Falha ao instanciar queues!" );

		}

	}

}
