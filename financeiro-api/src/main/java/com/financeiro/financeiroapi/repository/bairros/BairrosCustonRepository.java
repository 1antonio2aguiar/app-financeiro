package com.financeiro.financeiroapi.repository.bairros;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Bairros;

import lombok.var;

@Repository
public class BairrosCustonRepository {
	
	@PersistenceContext private EntityManager manager;
	
	// Query para pesquisar se já existe um nome de bairro para uma cidade.
	public List<Bairros> find(Long cidade, String nome){
		
		String query = "select B from Bairros as B where B.cidades.id = :cidade and B.nome = :nome";
		
		var queryRes = manager.createQuery(query, Bairros.class);
		
		queryRes.setParameter("cidade", cidade);
		queryRes.setParameter("nome", nome);
		
		return queryRes.getResultList();  
	}

}
