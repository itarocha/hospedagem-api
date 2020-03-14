package br.com.itarocha.hospedagem.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.itarocha.hospedagem.model.audit.UserDateAudit;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(name="destinacao_hospedagem")
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DestinacaoHospedagem that = (DestinacaoHospedagem) o;
		return id.equals(that.id) &&
				descricao.equals(that.descricao);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, descricao);
	}
}
