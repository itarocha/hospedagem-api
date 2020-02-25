package br.com.itarocha.hospedagem.dto;

import br.com.itarocha.hospedagem.model.DestinacaoHospedagem;
import br.com.itarocha.hospedagem.model.Logico;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuartoDTO implements Serializable {

	private static final long serialVersionUID = -6172158858365759661L;
	
	private Long id;
	
	@NotNull(message="Número precisa ser informado")
	private Integer numero;
	
	@NotNull(message="Descrição é obrigatória")
	@Size(max = 255, message="Descrição não pode ter mais que 255 caracteres")
	private String descricao;

    private Set<DestinacaoHospedagem> destinacoes = new HashSet<>();
	
	private List<LeitoDTO> leitos;

	private Logico ativo;

	private String displayText;

	public QuartoDTO() {
		this.ativo = Logico.S;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
		this.displayText = "Quarto " + ((this.numero != null) ? this.numero.toString() : "???");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

    public Set<DestinacaoHospedagem> getDestinacoes() {
        return this.destinacoes;
    }

    public void setDestinacoes(Set<DestinacaoHospedagem> destinacoes) {
        this.destinacoes = destinacoes;
    }


	public List<LeitoDTO> getLeitos() {
		return this.leitos;
	}

	public void setLeitos(List<LeitoDTO> leitos) {
		this.leitos = leitos;
	}

	public Logico getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Logico ativo) {
		this.ativo = ativo;
	}		
	
	public String getDisplayText() {
		return "Quarto " + ((this.numero != null) ? this.numero.toString() : "???");
	}
	
	public void setDisplayText(String value) {
		
	}
	
}
