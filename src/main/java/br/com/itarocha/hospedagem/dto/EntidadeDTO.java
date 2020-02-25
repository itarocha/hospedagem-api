package br.com.itarocha.hospedagem.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class EntidadeDTO implements Serializable{
	
	private static final long serialVersionUID = 9099025388150371771L;

	private Long id;

	@NotNull(message="Nome é obrigatório")
	@Size(min = 3, max = 64, message="Nome deve ter entre 3 a 64 caracteres")
	private String nome;

	@NotNull(message="CNPJ é obrigatório")
	@Size(min = 14, max = 14, message="CNPJ deve ter 14 caracteres")
	private String cnpj;
	
	@Valid
	@NotNull(message="Endereço é obrigatório")
	private EnderecoDTO endereco;
	
	@Size(max = 16, message="Telefone não pode ter mais que 16 caracteres")
	private String telefone;

	@Size(max = 16, message="Telefone2 não pode ter mais que 16 caracteres")
	private String telefone2;
	
	@Email(message="Email inválido")
	@Size(max = 64, message="Email deve ter no máximo 64 caracteres")
	private String email;
	
	private String observacoes;

	private List<EncaminhadorDTO> encaminhadores;
	
	public EntidadeDTO(){
		this.endereco = new EnderecoDTO();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public List<EncaminhadorDTO> getEncaminhadores() {
		return encaminhadores;
	}

	public void setEncaminhadores(List<EncaminhadorDTO> encaminhadores) {
		this.encaminhadores = encaminhadores;
	}

	@Override
    public String toString() {
        return String.format("EntidadeDTO[id=%d, nome='%s']",id, nome);
    }	

}
