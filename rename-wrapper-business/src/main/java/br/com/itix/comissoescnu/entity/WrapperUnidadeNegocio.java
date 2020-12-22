package br.com.itix.comissoescnu.entity;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table( name = "WrapperUnidadeNegocio" )
public class WrapperUnidadeNegocio extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column
	private Long id;

	@Column( nullable = false )
	private Long idUnidadeNegocio;

	@Column( nullable = false )
	private UUID tag;

	@Column
	private LocalDateTime dataAlteracao;
}
