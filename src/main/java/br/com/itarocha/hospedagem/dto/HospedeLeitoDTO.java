package br.com.itarocha.hospedagem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "quarto", "leito", "hospede"})
public class HospedeLeitoDTO implements Serializable {
	
	private static final long serialVersionUID = 5590030840087022870L;

	private Long id;
	
	@NotNull(message="Hóspede precisa ser informado")
	private HospedeDTO hospede;


	@NotNull(message="Data de Entrada precisa ser informado")
	private LocalDate dataEntrada;

	@NotNull(message="Data de Saída precisa ser informado")
	private LocalDate dataSaida;

	@NotNull(message="Quarto precisa ser informado")
	private QuartoDTO quarto;

	@NotNull(message="Leito precisa ser informado")
	private LeitoDTO leito;

	private Integer quartoNumero;

	private Integer leitoNumero;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HospedeDTO getHospede() {
		return hospede;
	}

	public void setHospede(HospedeDTO hospede) {
		this.hospede = hospede;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}

	public QuartoDTO getQuarto() {
		return quarto;
	}

	public void setQuarto(QuartoDTO quarto) {
		this.quarto = quarto;
	}

	public LeitoDTO getLeito() {
		return leito;
	}

	public void setLeito(LeitoDTO leito) {
		this.leito = leito;
	}

	public Integer getQuartoNumero() {
		return quartoNumero;
	}

	public void setQuartoNumero(Integer quartoNumero) {
		this.quartoNumero = quartoNumero;
	}

	public Integer getLeitoNumero() {
		return leitoNumero;
	}

	public void setLeitoNumero(Integer leitoNumero) {
		this.leitoNumero = leitoNumero;
	}
}
