package br.com.itarocha.hospedagem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.itarocha.hospedagem.model.audit.UserDateAudit;

@Entity
@Table(name="tipo_leito")
public class TipoLeito extends UserDateAudit implements Serializable, IEntity {

	private static final long serialVersionUID = -732012434360084121L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message="Descrição é obrigatória")
	@Size(max = 32, message="Descrição da Destinação da Hospedagem não pode ter mais que 32 caracteres")
	private String descricao;
	
	@NotNull(message="Quantidade de Camas precisa ser informada")
	@Min(value=1, message="Quantidade de Leitos deve ser no mínimo 1" )
	@Max(value=2, message="Quantidade de Leitos deve ser no máximo 2" )
	@Column(name = "quantidade_camas")
	private Integer quantidadeCamas;

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

	public Integer getQuantidadeCamas() {
		return quantidadeCamas;
	}

	public void setQuantidadeCamas(Integer quantidadeCamas) {
		this.quantidadeCamas = quantidadeCamas;
	}
	
}
