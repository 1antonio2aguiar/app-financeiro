package com.financeiro.financeiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Bairros;
import com.financeiro.financeiroapi.repository.bairros.BairrosRepositoryQuery;

@Repository
public interface BairrosRepository extends JpaRepository<Bairros, Long>, BairrosRepositoryQuery{

}
