package com.financeiro.financeiroapi.repository.tiposLogradouros;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.filter.TiposLogradourosFilter;
import com.financeiro.financeiroapi.model.TiposLogradouros;

@Repository
public interface TiposLogradourosRepositoryQuery {
	
	public Page<TiposLogradouros>  filtrar(TiposLogradourosFilter tiposLogradourosFilter, Pageable pageable);
	public List<TiposLogradouros> filtrar(TiposLogradourosFilter tiposLogradourosFilter);

}
