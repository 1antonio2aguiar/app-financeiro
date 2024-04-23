package com.financeiro.financeiroapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Estados;
import com.financeiro.financeiroapi.repository.estados.EstadosRepositoryQuery;

@Repository
public interface EstadosRepository extends JpaRepository<Estados, Long>, EstadosRepositoryQuery{
	
	Optional<Estados> findByUf(String uf);
	Optional<Estados> findById(Estados estados);

}
