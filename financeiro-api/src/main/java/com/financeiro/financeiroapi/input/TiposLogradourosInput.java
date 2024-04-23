package com.financeiro.financeiroapi.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TiposLogradourosInput {
	
	private Long id;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private String sigla;
	
	public String getDescricao() {
		return descricao == null ? null :descricao.toUpperCase();
	}

	public String getSigla() {
		return sigla == null ? null :sigla.toUpperCase();
	}

}
