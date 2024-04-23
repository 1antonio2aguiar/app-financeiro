package com.financeiro.financeiroapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.ParcelasContratos;
import com.financeiro.financeiroapi.repository.parcelasContratos.ParcelasContratosRepositoryQuery;

@Repository
public interface ParcelasContratosRepository extends JpaRepository<ParcelasContratos, Long>, ParcelasContratosRepositoryQuery{
	
	List<ParcelasContratos> findByContratosId(Long contratosId);
	
	void deleteByContratosId(Long contratosId);

}
