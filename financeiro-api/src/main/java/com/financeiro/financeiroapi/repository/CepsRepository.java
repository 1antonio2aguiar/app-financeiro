package com.financeiro.financeiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Ceps;
import com.financeiro.financeiroapi.repository.ceps.CepsRepositoryQuery;

@Repository
public interface CepsRepository extends JpaRepository<Ceps, Long>, CepsRepositoryQuery{

}
