package br.com.itix.comissoescnu.dto;

import java.time.LocalDateTime;

import br.com.itix.xdk.business.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode( callSuper = false )
public class UnidadeNegocioDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String descricao;

	private LocalDateTime dataAlteracao;

}
