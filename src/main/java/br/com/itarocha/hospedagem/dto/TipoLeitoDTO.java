package br.com.itarocha.hospedagem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoLeitoDTO implements Serializable {

	private static final long serialVersionUID = -732012434360084121L;

	private Long id;
	
	@NotNull(message="Descrição é obrigatória")
	@Size(max = 32, message="Descrição da Destinação da Hospedagem não pode ter mais que 32 caracteres")
	private String descricao;
	
	@NotNull(message="Quantidade de Camas precisa ser informada")
	@Min(value=1, message="Quantidade de Leitos deve ser no mínimo 1" )
	@Max(value=2, message="Quantidade de Leitos deve ser no máximo 2" )
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
