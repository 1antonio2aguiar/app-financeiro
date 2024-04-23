package com.financeiro.financeiroapi.filter;

import lombok.Data;

@Data
public class EnderecosFilter {

	private Long id;
	private CepsFilter cepsFilter = new CepsFilter();
	private PessoasFilter pessoasFilter = new PessoasFilter();
	private BairrosFilter bairrosFilter = new BairrosFilter();
	private LogradourosFilter logradourosFilter = new LogradourosFilter();
	
}
