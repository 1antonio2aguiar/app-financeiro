package com.financeiro.financeiroapi.repository.bairros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.BairrosFilter;
import com.financeiro.financeiroapi.model.Bairros;

@Repository
public interface BairrosRepositoryQuery {
	
	public Page<Bairros>  filtrar(BairrosFilter bairrosFilter, Pageable pageable);

}
