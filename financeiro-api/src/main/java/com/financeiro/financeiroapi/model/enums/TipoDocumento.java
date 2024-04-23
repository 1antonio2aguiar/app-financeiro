package com.financeiro.financeiroapi.model.enums;

public enum TipoDocumento {
	
	RG("REGISTRO GERAL"),
	CTPS("CARTEIRA PROFISSIONAL DE TRABALHO"),
	CNH("CARTEIRA NACIONAL DE HABILITAÇÃO"),
	RESERVISTA("RESERVISTA"),
	PASSAPORTE("PASSAPORTE"),
	TITELEITOR("TÍTULO DE ELEITOR"),
	INSCRIMUNICIPAL("INSCRIÇÃO MUNICIPAL"),
	OUTRO("OUTRO");
	
	private String descricao;

	TipoDocumento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
