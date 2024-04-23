package com.financeiro.financeiroapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.financeiro.financeiroapi.model.Enderecos;
import com.financeiro.financeiroapi.repository.enderecos.EnderecosRepositoryQuery;

@Repository
public interface EnderecosRepository extends JpaRepository<Enderecos, Long>, EnderecosRepositoryQuery{
	
	List<Enderecos> findByPessoaId(Long pessoaId);

	void deleteByPessoaId(Long id);

}
