package com.financeiro.financeiroapi.repository.documentos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.DocumentosFilter;
import com.financeiro.financeiroapi.model.Documentos;

@Repository
public interface DocumentosRepositoryQuery {
	
	public Page<Documentos> filtrar(DocumentosFilter documentosFilter, Pageable pageable);

}
