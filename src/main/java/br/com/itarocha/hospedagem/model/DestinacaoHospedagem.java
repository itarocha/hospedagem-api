package br.com.itarocha.hospedagem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.itarocha.hospedagem.model.audit.UserDateAudit;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(name="destinacao_hospedagem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Schema(description = "Destinação de Hospedagem")
public class DestinacaoHospedagem  extends UserDateAudit implements IEntity, Serializable {

	private static final long serialVersionUID = 397874357784755819L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 32)
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
