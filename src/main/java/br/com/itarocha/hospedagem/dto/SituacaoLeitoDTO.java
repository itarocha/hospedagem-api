package br.com.itarocha.hospedagem.dto;

import br.com.itarocha.hospedagem.model.Logico;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SituacaoLeitoDTO implements Serializable {

	private static final long serialVersionUID = -6750385228764487323L;

	private Long id;
	
	@NotNull(message="Descrição é obrigatória")
	@Size(min = 3, max = 32, message="Descrição deve ter entre 3 e 32 caracteres")
	private String descricao;
	
	private Logico disponivel;

	public SituacaoLeitoDTO() {
		this.disponivel = Logico.S;
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

	public Logico getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Logico disponivel) {
		this.disponivel = disponivel;
	}
	
}
