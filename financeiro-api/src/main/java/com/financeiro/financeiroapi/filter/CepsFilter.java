package com.financeiro.financeiroapi.filter;

import lombok.Data;

@Data
public class CepsFilter {
	
	private Long id;
	private String cep;
	
	private BairrosFilter bairrosFilter = new BairrosFilter();
	private LogradourosFilter logradourosFilter = new LogradourosFilter();

}
