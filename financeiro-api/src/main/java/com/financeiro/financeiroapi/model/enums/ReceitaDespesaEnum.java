package com.financeiro.financeiroapi.model.enums;

public enum ReceitaDespesaEnum {
	RECEITA("RECEITA"),
	DESPESA("DESPESA");
	
	private String descricao;

	ReceitaDespesaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
