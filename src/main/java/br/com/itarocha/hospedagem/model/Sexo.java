package br.com.itarocha.hospedagem.model;

public enum Sexo {

	M("Masculino"),
	F("Feminino");
	
	private String descricao;
	
	Sexo(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
