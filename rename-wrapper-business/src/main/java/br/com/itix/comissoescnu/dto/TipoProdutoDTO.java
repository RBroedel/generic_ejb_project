package br.com.itix.comissoescnu.dto;

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
public class TipoProdutoDTO extends BaseDTO {

	private static final long serialVersionUID = -6696705664987852797L;

	@BindProperty
	private Long id;
	
	@BindProperty
	private String descricao;
	
}
