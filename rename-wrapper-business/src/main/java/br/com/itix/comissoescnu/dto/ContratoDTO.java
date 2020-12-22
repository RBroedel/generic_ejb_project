package br.com.itix.comissoescnu.dto;

import java.time.LocalDateTime;

import br.com.itix.xdk.business.BaseDTO;
import br.com.itix.xdk.util.converter.BindProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )
public class ContratoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	@BindProperty
	private Long id;
	
	@BindProperty
	private String descricao;
	
	@BindProperty
	private Long tipoProdutoId;
	
	@BindProperty
	private Long produtoId;
	
	@BindProperty
	private Long unidadeNegocioId;
	
	@BindProperty
	private Long empresaId;
	
	@BindProperty		  	
	private LocalDateTime dataInicioVigencia;
	
	@BindProperty
	private LocalDateTime dataFimVigencia;
	
	@BindProperty
	private LocalDateTime dataAlteracao;
	
}
