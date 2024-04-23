package com.financeiro.financeiroapi.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.model.TiposLogradouros;
import com.financeiro.financeiroapi.repository.TiposLogradourosRepository;

@Service
public class TiposLogradourosService {
	
	@Autowired public TiposLogradourosRepository tiposLogradourosRepository;
	
	public TiposLogradouros buscar(Long id) {
		//System.err.println(" e aqui passou?? " + id);
		return tiposLogradourosRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Tipo Logradouro não encontrado!"));
	}
	
	// Insert
	@Transactional 
	public TiposLogradouros salvar(TiposLogradouros tiposLogradouros) {
		//Antes de salvar verifica se a DESCRICAO já esta cadastrado. se sim retorna erro.
		// O metodo retorna optional esta logica transforma boolean
		boolean descCadastrada = tiposLogradourosRepository.findByDescricao(tiposLogradouros.getDescricao())
				.stream()
				.anyMatch(descDuplicada -> !descDuplicada.equals(tiposLogradouros));
		
		//System.err.println("UF " + ufCadastrada);
		
		if(descCadastrada) {
			// Se a DESCRICAO já estiver cadastrado entra aqui.
			throw new NegocioException("TIPO LOGRADOURO já cadastrado!");
		}
		
		return tiposLogradourosRepository.save(tiposLogradouros);
	}
	
	@Transactional 
	public void excluir(Long id) {
		tiposLogradourosRepository.deleteById(id);
	}
}
