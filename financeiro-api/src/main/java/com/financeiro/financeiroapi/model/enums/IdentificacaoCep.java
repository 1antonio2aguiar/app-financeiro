package com.financeiro.financeiroapi.model.enums;

public enum IdentificacaoCep {
	
	U("ÚNICO"), D("DIREITA"), E("ESQUERDA"), I("IMPAR"), P("PAR"), A("AMBOS");
	
	private String descricao;

	IdentificacaoCep(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
	
}
