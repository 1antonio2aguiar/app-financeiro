package com.financeiro.financeiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.repository.cidades.CidadesRepositoryQuery;

@Repository
public interface CidadesRepository extends JpaRepository<Cidades, Long>, CidadesRepositoryQuery{

}
