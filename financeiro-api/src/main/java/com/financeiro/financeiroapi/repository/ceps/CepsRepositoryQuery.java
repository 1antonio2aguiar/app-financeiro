package com.financeiro.financeiroapi.repository.ceps;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.CepsFilter;
import com.financeiro.financeiroapi.model.Ceps;

@Repository
public interface CepsRepositoryQuery {
	
	public Page<Ceps>  filtrar(CepsFilter cepsFilter, Pageable pageable);

}
