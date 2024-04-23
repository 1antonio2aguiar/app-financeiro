package com.financeiro.financeiroapi.filter;

import lombok.Data;

@Data
public class LogradourosFilter {
	
	private Long id;
	private String nome;
	
	private TiposLogradourosFilter tiposLogradouros;
	private CidadesFilter cidadesFilter = new CidadesFilter();
	
}
