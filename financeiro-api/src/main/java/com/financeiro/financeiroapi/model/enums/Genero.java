package com.financeiro.financeiroapi.model.enums;

public enum Genero {
	
	M("MASCULINO"),
	F("FEMININO");
	
	private String descricao;

	Genero(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
