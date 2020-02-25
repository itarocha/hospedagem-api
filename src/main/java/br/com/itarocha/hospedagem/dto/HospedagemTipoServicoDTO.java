package br.com.itarocha.hospedagem.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class HospedagemTipoServicoDTO implements Serializable {
	
	private static final long serialVersionUID = 4121535384605572478L;

	private Long id;
	
	@NotNull(message="Hospedagem precisa ser informado")
	private HospedagemDTO hospedagem;

	@NotNull(message="Tipo de Serviço precisa ser informado")
	private TipoServicoDTO tipoServico;

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

	public TipoServicoDTO getTipoServico() {
		return this.tipoServico;
	}

	public void setTipoServico(TipoServicoDTO tipoServico) {
		this.tipoServico = tipoServico;
	}

}
