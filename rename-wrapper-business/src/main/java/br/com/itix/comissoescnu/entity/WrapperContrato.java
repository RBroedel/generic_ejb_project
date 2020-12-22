package br.com.itix.comissoescnu.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "WrapperContrato" )
public class WrapperContrato {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column(name = "id" )
	private Long id;
	
	@Column
	private Long idContrato;
	
	@Column
	private UUID tag;
	
	@Column
	private Date dataAlteracao;
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public Long getIdContrato() {
		return idContrato;
	}
	
	public void setIdContrato( Long idContrato ) {
		this.idContrato = idContrato;
	}
	
	public UUID getTag() {
		return tag;
	}
	
	public void setTag( UUID tag ) {
		this.tag = tag;
	}
	
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	
	public void setDataAlteracao( Date dataAlteracao ) {
		this.dataAlteracao = dataAlteracao;
	}
	
}
