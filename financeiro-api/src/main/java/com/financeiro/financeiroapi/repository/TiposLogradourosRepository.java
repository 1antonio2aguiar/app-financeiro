package com.financeiro.financeiroapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.TiposLogradouros;
import com.financeiro.financeiroapi.repository.tiposLogradouros.TiposLogradourosRepositoryQuery;

@Repository
public interface TiposLogradourosRepository extends JpaRepository<TiposLogradouros, Long>, TiposLogradourosRepositoryQuery{

	Optional<TiposLogradouros> findByDescricao(String descricao);

} 

