package com.financeiro.financeiroapi.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.financeiro.financeiroapi.model.enums.ReceitaDespesaEnum;

import lombok.Data;

@Data
public class GruposFinanceirosInput {
	private Long id;
	
	@Valid
	@NotNull
	private ReceitaDespesaEnum receitaDespesa;
	
	@NotNull
	private String descricao;
	
	public String getDescricao() {
		return descricao == null ? null :descricao.toUpperCase();
	}
}
