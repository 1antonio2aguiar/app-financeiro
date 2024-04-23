package com.financeiro.financeiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.repository.logradouros.LogradourosRepositoryQuery;

@Repository
public interface LogradourosRepository extends JpaRepository<Logradouros, Long>, LogradourosRepositoryQuery{

}
