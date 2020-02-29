package br.com.itarocha.hospedagem.model;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.itarocha.hospedagem.model.audit.UserDateAudit;

@Entity
@Table(name="leito")
public class Leito extends UserDateAudit implements Serializable, IEntity {
	
	private static final long serialVersionUID = 5765750404479537331L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="quarto_id")
	@NotNull
	@JsonbTransient
	private Quarto quarto;

	@NotNull(message="Número Sequencial precisa ser informada")
	@Min(value=1, message="Número deve ser no mínimo 1" )
	private Integer numero;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_leito_id")
	@NotNull(message="Tipo de Leito deve ser informado")
	private TipoLeito tipoLeito;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="situacao_leito_id")
	@NotNull(message="Situação do Leito deve ser informada")
	private SituacaoLeito situacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Quarto getQuarto() {
		return this.quarto;
	}

	public void setQuarto(Quarto quarto) {
		this.quarto = quarto;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public TipoLeito getTipoLeito() {
		return this.tipoLeito;
	}

	public void setTipoLeito(TipoLeito tipoLeito) {
		this.tipoLeito = tipoLeito;
	}

	public SituacaoLeito getSituacao() {
		return this.situacao;
	}

	public void setSituacao(SituacaoLeito situacao) {
		this.situacao = situacao;
	}
	
}
