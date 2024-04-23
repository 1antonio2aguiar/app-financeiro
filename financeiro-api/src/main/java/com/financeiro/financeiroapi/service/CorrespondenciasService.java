package com.financeiro.financeiroapi.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.model.Correspondencias;
import com.financeiro.financeiroapi.model.Enderecos;
import com.financeiro.financeiroapi.repository.EnderecosRepository;

@Service
public class CorrespondenciasService {
	
	@Autowired private EnderecosRepository enderecosRepository; 
	@Autowired private EnderecosService enderecosService; 
	
	/*@Transactional
	public Correspondencias registrar(Long enderecoId, String descricao) {
		
		Enderecos enderecos = enderecosRepository.findById(enderecoId)
				.orElseThrow(() -> new NegocioException("Endereço não Encontrado"));
		
		return enderecos.adicionarCorrespondencia(descricao);
		
	}*/
	
	/*@Transactional
	public void finalizar(Long enderecoId) {
		
		Enderecos enderecos = enderecosService.buscar(enderecoId);
		
		enderecos.finalizar();
		
		enderecosRepository.save(enderecos);
		
	}*/

}
