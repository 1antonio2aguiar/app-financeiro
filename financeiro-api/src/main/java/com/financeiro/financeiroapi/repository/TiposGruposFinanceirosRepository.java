package com.financeiro.financeiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.TiposGruposFinanceiros;
import com.financeiro.financeiroapi.repository.TiposGruposFinanceiros.TiposGruposFinanceirosRepositoryQuery;

@Repository
public interface TiposGruposFinanceirosRepository extends JpaRepository<TiposGruposFinanceiros, Long>, TiposGruposFinanceirosRepositoryQuery{

} 
