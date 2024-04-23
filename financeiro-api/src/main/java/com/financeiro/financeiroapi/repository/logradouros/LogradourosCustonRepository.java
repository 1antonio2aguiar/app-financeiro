package com.financeiro.financeiroapi.repository.logradouros;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.financeiro.financeiroapi.model.Logradouros;

import lombok.var;

@Repository
public class LogradourosCustonRepository {
	
	@PersistenceContext private EntityManager manager;
	
	// Query para pesquisar se já existe um nome de rua/tipo logradouro para uma cidade.
	public List<Logradouros> find(Long cidade, Long tipoLogradouro, String nome){
		
		//System.err.println("cidade: " + cidade + " tipo: " + tipoLogradouro + " Nome: " + nome);
		
		String query = "select L from Logradouros as L where L.cidades.id = :cidade and L.tiposLogradouros.id = :tipoLogradouro and L.nome = :nome" ;
		
		var queryRes = manager.createQuery(query, Logradouros.class);
		
		queryRes.setParameter("cidade", cidade);
		queryRes.setParameter("tipoLogradouro", tipoLogradouro);
		queryRes.setParameter("nome", nome);
		
		return queryRes.getResultList();  
	}

}
