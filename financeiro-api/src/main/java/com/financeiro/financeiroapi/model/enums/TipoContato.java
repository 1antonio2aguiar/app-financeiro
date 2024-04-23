package com.financeiro.financeiroapi.model.enums;

public enum TipoContato {
	
	TR("TELEFONE RESIDENCIAL"),
	FAX("FAX"),
	EMAIL("E_MAIL"),
	WEB("PAGINA WEB"),
	RECADO("TELEFONE PARA RECADO"),
	CELULAR("TELEFONE CELULAR"),
	WHATSUP("WHATSUP"),
	OUTRO("OUTRO");
	
	private String descricao;

	TipoContato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
