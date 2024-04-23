package com.financeiro.financeiroapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Contatos;
import com.financeiro.financeiroapi.model.Documentos;
import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.repository.contatos.ContatosRepositoryQuery;

@Repository
public interface ContatosRepository extends JpaRepository<Contatos, Long>, ContatosRepositoryQuery{
	List<Contatos> findByPessoaId(Long pessoaId);
	
	void deleteByPessoaId(Long id);
}
