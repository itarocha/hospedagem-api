package br.com.itarocha.hospedagem.dto;

import br.com.itarocha.hospedagem.model.DestinacaoHospedagem;
import br.com.itarocha.hospedagem.model.TipoUtilizacaoHospedagem;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HospedagemDTO implements Serializable {

	private static final long serialVersionUID = 1841335162635443594L;

	private Long id;

	private EntidadeDTO entidade;
	
	private EncaminhadorDTO encaminhador;

	@NotNull(message="Destinação da Hospedagem é obrigatória")
	private DestinacaoHospedagem destinacaoHospedagem;
			
	@NotNull(message="Data de Entrada precisa ser informado")
	private LocalDate dataEntrada;
	
	@NotNull(message="Data Prevista de Saída precisa ser informada")
	private LocalDate dataPrevistaSaida;
	
	private LocalDate dataEfetivaSaida;

	@NotNull(message="Tipo de Utilização precisa ser informada")
	private TipoUtilizacaoHospedagem tipoUtilizacao;
	
	private String observacoes;
	
	private List<HospedeDTO> hospedes = new ArrayList<HospedeDTO>();
	
	private List<HospedagemTipoServicoDTO> servicos = new ArrayList<HospedagemTipoServicoDTO>();
	
	public HospedagemDTO() {
		this.tipoUtilizacao = TipoUtilizacaoHospedagem.T;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntidadeDTO getEntidade() {
		return this.entidade;
	}

	public void setEntidade(EntidadeDTO entidade) {
		this.entidade = entidade;
	}

	public EncaminhadorDTO getEncaminhador() {
		return this.encaminhador;
	}

	public void setEncaminhador(EncaminhadorDTO encaminhador) {
		this.encaminhador = encaminhador;
	}

	public LocalDate getDataEntrada() {
		return this.dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDate getDataPrevistaSaida() {
		return this.dataPrevistaSaida;
	}

	public void setDataPrevistaSaida(LocalDate dataPrevistaSaida) {
		this.dataPrevistaSaida = dataPrevistaSaida;
	}

	public LocalDate getDataEfetivaSaida() {
		return dataEfetivaSaida;
	}

	public void setDataEfetivaSaida(LocalDate dataEfetivaSaida) {
		this.dataEfetivaSaida = dataEfetivaSaida;
	}

	public String getObservacoes() {
		return this.observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public DestinacaoHospedagem getDestinacaoHospedagem() {
		return destinacaoHospedagem;
	}

	public void setDestinacaoHospedagem(DestinacaoHospedagem destinacaoHospedagem) {
		this.destinacaoHospedagem = destinacaoHospedagem;
	}

	public TipoUtilizacaoHospedagem getTipoUtilizacao() {
		return tipoUtilizacao;
	}

	public void setTipoUtilizacao(TipoUtilizacaoHospedagem tipoUtilizacao) {
		this.tipoUtilizacao = tipoUtilizacao;
	}

	public List<HospedeDTO> getHospedes() {
		return this.hospedes;
	}

	public void setHospedes(List<HospedeDTO> hospedes) {
		this.hospedes = hospedes;
	}

	public List<HospedagemTipoServicoDTO> getServicos() {
		return servicos;
	}

	public void setServicos(List<HospedagemTipoServicoDTO> servicos) {
		this.servicos = servicos;
	}
}
