package com.financeiro.financeiroapi.filter;

import lombok.Data;

@Data
public class ParcelasContratosFilter {
	private Long id;
	private Long contratoId;
	private ContratosFilter contratosFilter = new ContratosFilter();
}
