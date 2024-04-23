package com.financeiro.financeiroapi.repository.estados;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.financeiroapi.filter.EstadosFilter;
import com.financeiro.financeiroapi.model.Estados;

public interface EstadosRepositoryQuery {
	
	public Page<Estados> filtrar(EstadosFilter estadosFilter, Pageable pageable);
	
	public List<Estados> filtrar(EstadosFilter estadosFilter);

}