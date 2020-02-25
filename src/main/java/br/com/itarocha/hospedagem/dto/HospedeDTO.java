package br.com.itarocha.hospedagem.dto;

import br.com.itarocha.hospedagem.model.IEntity;
import br.com.itarocha.hospedagem.model.Logico;
import br.com.itarocha.hospedagem.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hospedagem"}) // "leitos",
public class HospedeDTO extends UserDateAudit implements Serializable, IEntity {
	
	private static final long serialVersionUID = 7525841265591324037L;

	private Long id;
	
	@NotNull(message="Hospedagem precisa ser informado")
	private HospedagemDTO hospedagem;

	@NotNull(message="Pessoa precisa ser informada")
	private PessoaDTO pessoa;

	@NotNull(message="Tipo de Hóspede precisa ser informado")
	private TipoHospedeDTO tipoHospede;

	private Logico baixado;

	private List<HospedeLeitoDTO> leitos = new ArrayList<HospedeLeitoDTO>();

	public HospedeDTO() {
		this.baixado = Logico.N;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HospedagemDTO getHospedagem() {
		return this.hospedagem;
	}

	public void setHospedagem(HospedagemDTO hospedagem) {
		this.hospedagem = hospedagem;
	}

	public PessoaDTO getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(PessoaDTO pessoa) {
		this.pessoa = pessoa;
	}

	public TipoHospedeDTO getTipoHospede() {
		return this.tipoHospede;
	}

	public void setTipoHospede(TipoHospedeDTO tipoHospede) {
		this.tipoHospede = tipoHospede;
	}

	public Logico getBaixado() {
		return baixado;
	}

	public void setBaixado(Logico baixado) {
		this.baixado = baixado;
	}

	public List<HospedeLeitoDTO> getLeitos() {
		return leitos;
	}

	public void setLeitos(List<HospedeLeitoDTO> leitos) {
		this.leitos = leitos;
	}

}
