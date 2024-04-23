package com.financeiro.financeiroapi.filter;

import lombok.Data;

@Data
public class ContatosFilter {
	
	private Long id;
	private PessoasFilter pessoasFilter = new PessoasFilter();

}
