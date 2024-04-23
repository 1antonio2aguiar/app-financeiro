package com.financeiro.financeiroapi.model.enums;

public enum EstadoCivil {
	
	A("AMAZIADO(A)"),
	C("CASADO(A)"),
	S("SOLTEIRO(A)"),
	U("UNIÃO ESTÁVEL"),
	V("VIUVO(A)"),
	O("OUTRO");
	
	private String descricao;

	EstadoCivil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
