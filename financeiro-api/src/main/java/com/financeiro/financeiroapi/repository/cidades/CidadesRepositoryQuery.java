package com.financeiro.financeiroapi.repository.cidades;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.financeiroapi.filter.CidadesFilter;
import com.financeiro.financeiroapi.model.Cidades;

public interface CidadesRepositoryQuery {
	
	public Page<Cidades> filtrar(CidadesFilter cidadesFilter, Pageable pageable);

} 
