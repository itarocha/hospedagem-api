package br.com.itarocha.hospedagem.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.itarocha.hospedagem.model.audit.UserDateAudit;

@Entity
@Table(name="tipo_hospede")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoHospede extends UserDateAudit implements Serializable, IEntity {

	private static final long serialVersionUID = 3527441284759034033L;

	/*
	PACIENTE("Paciente"),
	ACOMPANHANTE("Acompanhante");	
*/	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Descrição é obrigatória")
	@Size(max = 32, message="Descrição do Tipo de Hóspede não pode ter mais que 32 caracteres")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
