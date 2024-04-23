package com.financeiro.financeiroapi.model.enums;

public enum TipoEndereco {
	
	R("RESIDENCIAL"), C("COMERCIAL");
	
	private String descricao;

	TipoEndereco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
