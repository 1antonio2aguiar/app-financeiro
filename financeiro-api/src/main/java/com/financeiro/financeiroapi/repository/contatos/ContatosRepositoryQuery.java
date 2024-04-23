package com.financeiro.financeiroapi.repository.contatos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.ContatosFilter;
import com.financeiro.financeiroapi.model.Contatos;

@Repository
public interface ContatosRepositoryQuery {
	
	Page<Contatos> filtrar(ContatosFilter contatosFilter, Pageable pageable);

}
