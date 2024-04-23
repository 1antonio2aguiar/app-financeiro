package com.financeiro.financeiroapi.model.enums;

public enum FisicaJuridica {
	
	F("FISICA"),
	J("JURÍDICA");
	
	private String descricao;

	FisicaJuridica(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
