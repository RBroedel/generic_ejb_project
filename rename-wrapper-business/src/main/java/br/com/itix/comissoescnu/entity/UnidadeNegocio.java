package br.com.itix.comissoescnu.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.itix.xdk.business.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode( callSuper = false )
@Table( name = "TabUnidadeNegocio" )
public class UnidadeNegocio extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "COD_UNIDADE_DE_NEGOCIO" )
	private Long id;

	@Column( name = "NOM_UNIDADE_DE_NEGOCIO" )
	private String descricao;

	@Column( name = "DT_ALTERACAO" )
	private LocalDateTime dataAlteracao;

}
