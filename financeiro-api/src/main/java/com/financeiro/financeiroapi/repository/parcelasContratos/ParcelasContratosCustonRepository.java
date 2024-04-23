package com.financeiro.financeiroapi.repository.parcelasContratos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import lombok.var;

@Repository
public class ParcelasContratosCustonRepository {
	
	@PersistenceContext private EntityManager manager;
	
	public Long findMaxParcela(Long contrato) {
		
		String query = "SELECT max(PC.parcela) FROM ParcelasContratos as PC where PC.contratos.id = :id";				
		//Query query = manager.createQuery("SELECT max(PC.parcela) FROM ParcelasContratos as PC where PC.contratos.id = :id" );
		
		try {
			var queryRes = manager.createQuery(query);
			queryRes.setParameter("id", contrato);
			
			Long parcela = Long.parseLong(queryRes.getSingleResult().toString());
			return parcela;			 			
		} catch (Exception e){
			return (long) 0;
		}
	} 
}
