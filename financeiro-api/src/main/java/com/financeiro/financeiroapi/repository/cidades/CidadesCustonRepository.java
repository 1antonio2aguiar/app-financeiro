package com.financeiro.financeiroapi.repository.cidades;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Cidades;

import lombok.var;

@Repository
public class CidadesCustonRepository {
	
	@PersistenceContext private EntityManager manager;
	
	// Query para pesquisar se já existe um nome de cidade para um estado.
	public List<Cidades> find(Long estado, String nome){
		
		String query = "select C from Cidades as C where C.estados.id = :estado and C.nome = :nome";
		
		var queryRes = manager.createQuery(query, Cidades.class);
		
		queryRes.setParameter("estado", estado);
		queryRes.setParameter("nome", nome);
		
		return queryRes.getResultList(); 
	}

}
