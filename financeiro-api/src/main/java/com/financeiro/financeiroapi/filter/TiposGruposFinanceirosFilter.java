package com.financeiro.financeiroapi.filter;

import lombok.Data;

@Data
public class TiposGruposFinanceirosFilter {
	private Long id;
	private GruposFinanceirosFilter gruposFinanceirosFilter = new GruposFinanceirosFilter();
	private String descricao;
}
