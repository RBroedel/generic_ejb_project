package br.com.itix.comissoescnu.entity;

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
@Table( name = "TabTipoProduto" )
public class TipoProduto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "COD_TIPO_PRODUTO" )
	private Long id;

	@Column( name = "NOM_TIPO_PRODUTO" )
	private String descricao;

	@Column( name = "DATA_ALTERACAO" )
	private Date dataAlteracao;

}
