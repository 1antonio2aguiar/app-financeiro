package com.financeiro.financeiroapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.outputs.PessoasOutput;
import com.financeiro.financeiroapi.repository.pessoas.PessoasRepositoryQuery;

@Repository
public interface PessoasRepository extends JpaRepository<Pessoas, Long>, PessoasRepositoryQuery{
	
	Optional<Pessoas> findByCpfCnpj(String cpfCnpj);
	
}
