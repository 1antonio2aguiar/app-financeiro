package com.financeiro.financeiroapi.filter;

import lombok.Data;

@Data
public class CidadesFilter {
	
	private Long id;
	private String nome;
	
	private EstadosFilter estadosFilter = new EstadosFilter();

}
