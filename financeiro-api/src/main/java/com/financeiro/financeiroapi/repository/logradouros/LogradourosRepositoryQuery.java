package com.financeiro.financeiroapi.repository.logradouros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.LogradourosFilter;
import com.financeiro.financeiroapi.model.Logradouros;

@Repository
public interface LogradourosRepositoryQuery {
	
	public Page<Logradouros>  filtrar(LogradourosFilter logradourosFilter, Pageable pageable);

}
