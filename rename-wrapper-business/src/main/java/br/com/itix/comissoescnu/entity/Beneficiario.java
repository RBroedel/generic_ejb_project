package br.com.itix.comissoescnu.entity;

import java.time.LocalDateTime;
import java.util.Date;

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
@Table( name = "TabBeneficiario" )
public class Beneficiario extends BaseEntity {

	private static final long serialVersionUID = 1L;

	//TODO
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "COD_BENEFICIARIO" )
	private Long id;
	
	@Column( name = "NUM_CPF")
	private String cpf;
	
	@Column( name = "NUM_ASSOCIADO")
	private Long numeroAssociado;

	@Column( name = "NOME_ASSOCIADO" )
	private String nome;
	
	@Column( name = "DATA_NASCIMENTO" )
	private Date dataNascimento;
	
	@Column( name = "NOME_MAE" )
	private String nomeMae;
	
	@Column( name = "DATA_INCLUSAO" )
	private LocalDateTime dataInclusao;
	
	@Column( name = "DATA_EXCLUSAO" )
	private LocalDateTime dataExclusao;
	
	@Column( name = "NUM_CONTRATO")
	private Long contratoId;
	
	@Column( name = "DATA_ALTERACAO" )
	private LocalDateTime dataAlteracao;

}
