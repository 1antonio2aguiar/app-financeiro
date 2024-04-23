package com.financeiro.financeiroapi.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TiposGruposFinanceirosInput {
private Long id;
	
	@Valid
	@NotNull
	private Long grupoFinanceiroId;
	
	@NotNull
	private String descricao;
	
	public String getDescricao() {
		return descricao == null ? null :descricao.toUpperCase();
	}
}
