package br.com.itarocha.hospedagem.dto;

import br.com.itarocha.hospedagem.model.Logico;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EncaminhadorDTO implements Serializable {
	
	private static final long serialVersionUID = 4127725617611839075L;

	private Long id;
	
	@NotNull(message="Entidade é obrigatória")
	private EntidadeDTO entidade;
	
	@NotNull(message="Nome é obrigatório")
	@Size(min = 2, max = 64, message="Nome deve ter entre 2 a 64 caracteres")
	private String nome;
	
	@NotNull(message="Cargo é obrigatório")
	@Size(min = 2, max = 64, message="Cargo deve ter entre 2 a 64 caracteres")
	private String cargo;

	@NotNull(message="Telefone é obrigatório")
	@Size(max = 16, message="Telefone não pode ter mais que 16 caracteres")
	private String telefone;
	
	@Email(message="Email inválido")
	@Size(max = 64, message="Email deve ter no máximo 64 caracteres")
	private String email;
	
	@NotNull(message="Ativo é obrigatório")
	private Logico ativo;

	public EncaminhadorDTO() {
		this.ativo = Logico.S;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntidadeDTO getEntidade() {
		return entidade;
	}

	public void setEntidade(EntidadeDTO entidade) {
		this.entidade = entidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
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
	
	public Logico getAtivo() {
		return ativo;
	}

	public void setAtivo(Logico ativo) {
		this.ativo = ativo;
	}
	
}
