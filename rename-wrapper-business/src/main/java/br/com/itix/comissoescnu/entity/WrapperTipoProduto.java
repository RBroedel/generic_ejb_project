package br.com.itix.comissoescnu.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class WrapperTipoProduto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column
	private Long id;

	@Column(nullable = false )
	private Long idTipoProduto;

	@Column( nullable = false )
	private UUID tag;

	@Column
	private Date dataAlteracao;

	
}
