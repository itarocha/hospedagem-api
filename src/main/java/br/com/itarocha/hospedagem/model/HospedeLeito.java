package br.com.itarocha.hospedagem.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.json.bind.annotation.JsonbTransient;

//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.itarocha.hospedagem.model.audit.UserDateAudit;

@Entity
@Table(name="hospede_leito")
public class HospedeLeito extends UserDateAudit implements Serializable, IEntity {
	
	private static final long serialVersionUID = 5590030840087022870L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hospede_id")
	@NotNull(message="Hóspede precisa ser informado")
	@JsonbTransient
	private Hospede hospede;
	
	
	@NotNull(message="Data de Entrada precisa ser informado")
	@Column(name="data_entrada")
	private LocalDate dataEntrada;
	
	@NotNull(message="Data de Saída precisa ser informado")
	@Column(name="data_saida")
	private LocalDate dataSaida;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="quarto_id")
	@NotNull(message="Quarto precisa ser informado")
	@JsonbTransient
	private Quarto quarto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="leito_id")
	@NotNull(message="Leito precisa ser informado")
	@JsonbTransient
	private Leito leito;
	
	@Transient
	private Integer quartoNumero;
	
	@Transient
	private Integer leitoNumero;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hospede getHospede() {
		return hospede;
	}

	public void setHospede(Hospede hospede) {
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

	public Quarto getQuarto() {
		return quarto;
	}

	public void setQuarto(Quarto quarto) {
		this.quarto = quarto;
	}

	public Leito getLeito() {
		return leito;
	}

	public void setLeito(Leito leito) {
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
