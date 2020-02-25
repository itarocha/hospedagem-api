package br.com.itarocha.hospedagem.model;

public enum SituacaoHospedagem {

	A("Aberta"),
	F("Finalizada");
	
	private String descricao;
	
	SituacaoHospedagem(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
