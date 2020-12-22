package br.com.itix.comissoescnu.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.itix.xdk.business.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode( callSuper = false )

@Entity
@Table( name = "CARGO_CONTRATO" )
public class Contrato extends BaseEntity {
	
	private static final long serialVersionUID = 1L;	
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "COD_CONTRATO" )
	private Long id;
	
	@Column( name = "DESCRICAO" )
	private String descricao;
	
	@Column( name = "COD_TIPO_PRODUTO" )
	private Long tipoProdutoId;
	
	@Column( name = "CODIGO_PRODUTO" )
	private Long produtoid;
	
	@Column( name = "COD_UNIDADE_DE_NEGOCIO" )
	private Long unidadeDeNegocioId;
	
	@Column( name = "COD_EMPRESA" )
	private Long empresaId;
	
	@Column( name = "DATA_INICIO_VIGENCIA" )
	private LocalDateTime dataInicioVigencia;
	
	@Column( name = "DATA_FIM_VIGENCIA" )
	private LocalDateTime dataFimVigencia;
	
	@Column( name = "DATA_ALTERACAO" )
	private LocalDateTime dataAlteracao;
	
}
