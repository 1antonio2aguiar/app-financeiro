package com.financeiro.financeiroapi.repository.parcelasContratos;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.financeiro.financeiroapi.filter.ParcelasContratosFilter;
import com.financeiro.financeiroapi.model.ParcelasContratos;

@Repository
public interface ParcelasContratosRepositoryQuery {
	Page<ParcelasContratos> filtrar(ParcelasContratosFilter parcelasContratosFilter, Pageable pageable);
}
