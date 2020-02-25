package br.com.itarocha.hospedagem.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DestinacaoHospedagemDTO {

    private Long id;

    @NotNull(message="Descrição é obrigatória")
	@Size(min = 3, max = 32, message="Descrição deve ter entre 3 e 32 caracteres")
    private String descricao;

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

}
