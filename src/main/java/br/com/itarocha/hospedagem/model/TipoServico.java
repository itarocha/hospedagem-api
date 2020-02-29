package br.com.itarocha.hospedagem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.itarocha.hospedagem.model.audit.UserDateAudit;

@Entity
@Table(name="tipo_servico")
public class TipoServico extends UserDateAudit implements Serializable, IEntity {

	private static final long serialVersionUID = -7511416572575687871L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message="Descrição é obrigatória")
	@Size(min = 3, max = 32, message="Descrição deve ter entre 3 e 32 caracteres")
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(length=1)
	@NotNull(message="Ativo é obrigatório")
	private Logico ativo;

	public TipoServico() {
		this.ativo = Logico.S;
	}
	
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

	public Logico getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Logico ativo) {
		this.ativo = ativo;
	}
}
