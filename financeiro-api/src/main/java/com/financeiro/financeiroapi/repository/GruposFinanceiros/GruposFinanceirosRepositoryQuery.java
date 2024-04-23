package com.financeiro.financeiroapi.repository.GruposFinanceiros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.financeiroapi.filter.GruposFinanceirosFilter;
import com.financeiro.financeiroapi.model.GruposFinanceiros;

public interface GruposFinanceirosRepositoryQuery {
	public Page<GruposFinanceiros> filtrar(GruposFinanceirosFilter gruposFinanceirosFilter, Pageable pageable);
}
