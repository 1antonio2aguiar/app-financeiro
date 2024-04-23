package com.financeiro.financeiroapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Documentos;
import com.financeiro.financeiroapi.repository.documentos.DocumentosRepositoryQuery;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Long>, DocumentosRepositoryQuery{
	List<Documentos> findByPessoaId(Long pessoaId);

	void deleteByPessoaId(Long id);
}
