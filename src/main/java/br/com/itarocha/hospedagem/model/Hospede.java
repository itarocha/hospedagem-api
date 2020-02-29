package br.com.itarocha.hospedagem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.itarocha.hospedagem.model.audit.UserDateAudit;

@Entity
@Table(name="hospede")
public class Hospede extends UserDateAudit implements Serializable, IEntity {
	
	private static final long serialVersionUID = 7525841265591324037L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hospedagem_id")
	@NotNull(message="Hospedagem precisa ser informado")
	@JsonbTransient
	private Hospedagem hospedagem;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pessoa_id")
	@NotNull(message="Pessoa precisa ser informada")
	private Pessoa pessoa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tipo_hospede_id")
	@NotNull(message="Tipo de Hóspede precisa ser informado")
	private TipoHospede tipoHospede;
	
	@Enumerated(EnumType.STRING)
	@Column(length=1)
	private Logico baixado;
	
	@OneToMany(mappedBy = "hospede",fetch=FetchType.LAZY)
	private List<HospedeLeito> leitos = new ArrayList<HospedeLeito>();
	
	public Hospede() {
		this.baixado = Logico.N;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hospedagem getHospedagem() {
		return this.hospedagem;
	}

	public void setHospedagem(Hospedagem hospedagem) {
		this.hospedagem = hospedagem;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public TipoHospede getTipoHospede() {
		return this.tipoHospede;
	}

	public void setTipoHospede(TipoHospede tipoHospede) {
		this.tipoHospede = tipoHospede;
	}

	public Logico getBaixado() {
		return baixado;
	}

	public void setBaixado(Logico baixado) {
		this.baixado = baixado;
	}

	public List<HospedeLeito> getLeitos() {
		return leitos;
	}

	public void setLeitos(List<HospedeLeito> leitos) {
		this.leitos = leitos;
	}

}
