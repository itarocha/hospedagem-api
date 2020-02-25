package br.com.itarocha.hospedagem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//import org.codehaus.jackson.annotate.JsonIgnore;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "quarto"}) // "leitos",
public class LeitoDTO implements Serializable {
	
	private static final long serialVersionUID = 5765750404479537331L;

	private Long id;
	
	@NotNull
	private QuartoDTO quarto;

	@NotNull(message="Número Sequencial precisa ser informada")
	@Min(value=1, message="Número deve ser no mínimo 1" )
	private Integer numero;
	
	@NotNull(message="Tipo de Leito deve ser informado")
	private TipoLeitoDTO tipoLeito;

	@NotNull(message="Situação do Leito deve ser informada")
	private SituacaoLeitoDTO situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuartoDTO getQuarto() {
		return this.quarto;
	}

	public void setQuarto(QuartoDTO quarto) {
		this.quarto = quarto;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public TipoLeitoDTO getTipoLeito() {
		return this.tipoLeito;
	}

	public void setTipoLeito(TipoLeitoDTO tipoLeito) {
		this.tipoLeito = tipoLeito;
	}

	public SituacaoLeitoDTO getSituacao() {
		return this.situacao;
	}

	public void setSituacao(SituacaoLeitoDTO situacao) {
		this.situacao = situacao;
	}
	
}
