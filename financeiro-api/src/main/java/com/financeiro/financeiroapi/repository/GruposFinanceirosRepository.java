package com.financeiro.financeiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.GruposFinanceiros;
import com.financeiro.financeiroapi.repository.GruposFinanceiros.GruposFinanceirosRepositoryQuery;

@Repository
public interface GruposFinanceirosRepository extends JpaRepository<GruposFinanceiros, Long>, GruposFinanceirosRepositoryQuery{

} 


