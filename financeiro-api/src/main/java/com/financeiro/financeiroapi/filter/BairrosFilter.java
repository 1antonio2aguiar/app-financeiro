package com.financeiro.financeiroapi.filter;

import lombok.Data;

@Data
public class BairrosFilter {
	
	private Long id;
	private String nome;
	
	private CidadesFilter cidadesFilter = new CidadesFilter();

}
