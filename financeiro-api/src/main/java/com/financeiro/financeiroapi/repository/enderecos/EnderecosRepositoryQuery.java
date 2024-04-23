package com.financeiro.financeiroapi.repository.enderecos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.EnderecosFilter;
import com.financeiro.financeiroapi.model.Enderecos;

@Repository
public interface EnderecosRepositoryQuery {
	
	public Page<Enderecos>  filtrar(EnderecosFilter enderecosFilter, Pageable pageable);

}
 