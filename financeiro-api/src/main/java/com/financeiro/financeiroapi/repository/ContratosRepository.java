package com.financeiro.financeiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Contratos;
import com.financeiro.financeiroapi.repository.contratos.ContratosRepositoryQuery;

@Repository
public interface ContratosRepository extends JpaRepository<Contratos, Long>, ContratosRepositoryQuery{

}
