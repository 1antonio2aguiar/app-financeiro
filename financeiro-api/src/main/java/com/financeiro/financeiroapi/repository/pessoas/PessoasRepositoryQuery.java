package com.financeiro.financeiroapi.repository.pessoas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.PessoasFilter;
import com.financeiro.financeiroapi.model.Pessoas;

@Repository
public interface PessoasRepositoryQuery {
	
	public Page<Pessoas>  filtrar(PessoasFilter pessoasFilter, Pageable pageable);
	
}
