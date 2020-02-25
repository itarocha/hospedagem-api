package br.com.itarocha.hospedagem.dto;

import br.com.itarocha.hospedagem.model.Logico;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TipoServicoDTO implements Serializable {

	private static final long serialVersionUID = -7511416572575687871L;

	private Long id;
	
	@NotNull(message="Descrição é obrigatória")
	@Size(min = 3, max = 32, message="Descrição deve ter entre 3 e 32 caracteres")
	private String descricao;
	
	@NotNull(message="Ativo é obrigatório")
	private Logico ativo;

	public TipoServicoDTO() {
		this.ativo = Logico.S;
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

	public Logico getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Logico ativo) {
		this.ativo = ativo;
	}
}
