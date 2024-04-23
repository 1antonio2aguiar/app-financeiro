package com.financeiro.financeiroapi.repository.TiposGruposFinanceiros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.financeiroapi.filter.TiposGruposFinanceirosFilter;
import com.financeiro.financeiroapi.model.TiposGruposFinanceiros;

public interface TiposGruposFinanceirosRepositoryQuery {
	public Page<TiposGruposFinanceiros> filtrar(TiposGruposFinanceirosFilter tiposGruposFinanceirosFilter, Pageable pageable);
}                                                                           
