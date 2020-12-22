package br.com.itix.comissoescnu.dto;

import java.util.Date;

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
public class RubricaDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	@BindProperty
	private Long id;
	
	@BindProperty
	private String descricao;
	
	@BindProperty
	private Date dataAlteracao;

}
