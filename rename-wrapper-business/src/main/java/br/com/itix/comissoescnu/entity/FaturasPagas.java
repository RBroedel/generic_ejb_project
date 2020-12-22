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
@Table( name = "TabFaturasPagas" )
public class FaturasPagas extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "CODIGO_FATURA" )
	private Long id;

	@Column( name = "COD_CONTRATO" )
	private Long contrato;

	@Column( name = "COMPETENCIA_FATURA" )
	private LocalDateTime competenciaFatura;

	@Column( name = "DATA_ALTERACAO" )
	private LocalDateTime dataAlteracao;
}
