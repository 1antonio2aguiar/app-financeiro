package com.financeiro.financeiroapi.repository.contratos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.ContratosFilter;
import com.financeiro.financeiroapi.model.Contratos;

@Repository
public interface ContratosRepositoryQuery {
	Page<Contratos> filtrar(ContratosFilter contratosFilter, Pageable pageable);
}
