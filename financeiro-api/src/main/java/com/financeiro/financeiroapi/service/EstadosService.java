package com.financeiro.financeiroapi.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.model.Estados;
import com.financeiro.financeiroapi.repository.EstadosRepository;

@Service
public class EstadosService {
	
	@Autowired public EstadosRepository estadosRepository;
	
	public Estados buscar(Long id) {
		//System.err.println(" e aqui passou?? " + id);
		return estadosRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Estado não encontrado!"));
	}
	
	// Insert
	@Transactional 
	public Estados salvar(Estados estados) {
		//Antes de salvar verifica se a UF já esta cadastrado. se sim retorna erro.
		// O metodo retorna optional esta logica transforma boolean
		boolean ufCadastrada = estadosRepository.findByUf(estados.getUf())
				.stream()
				.anyMatch(ufDuplicada -> !ufDuplicada.equals(estados));
		
		//System.err.println("UF " + ufCadastrada);
		
		if(ufCadastrada) {
			// Se a UF já estiver cadastrado entra aqui.
			throw new NegocioException("ESTADO já cadastrado!");
		}
		
		return estadosRepository.save(estados);
	}
	
	@Transactional 
	public void excluir(Long id) {
		estadosRepository.deleteById(id);
	}

}
