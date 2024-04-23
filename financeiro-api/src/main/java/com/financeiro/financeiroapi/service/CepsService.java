package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.input.CepsInput;
import com.financeiro.financeiroapi.model.Bairros;
import com.financeiro.financeiroapi.model.Ceps;
import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.repository.BairrosRepository;
import com.financeiro.financeiroapi.repository.CepsRepository;
import com.financeiro.financeiroapi.repository.LogradourosRepository;

@Service
public class CepsService {
	
	@Autowired public CepsRepository cepsRepository;
	@Autowired public BairrosRepository bairrosRepository;
	@Autowired public LogradourosRepository logradourosRepository;
	
	// Insert
	@Transactional 
	public Ceps save(CepsInput cepsInput) {
		
		//System.err.println("Esta no service 1 - " + cepsInput.getNumeroIni() + ' ' + cepsInput.getNumeroFin());
		
		if(cepsInput.getNumeroIni() >= cepsInput.getNumeroFin()) {
			/* Se o numero inicial não pode ser maior que o numero final. */
			throw new NegocioException("Número Inicial deve ser menor que o Número Final!");
		}
		
		Ceps ceps = new Ceps();
		BeanUtils.copyProperties(cepsInput, ceps, "id");

		// BUSCO BAIRRO E INSIRO NO CEPS
		Bairros bairros = bairrosRepository.findById(cepsInput.getBairrosId()).get();
		ceps.setBairros(bairros);
		
		//BUSCO LOGRADOURO E INSIRO EM CEPS
		Logradouros logradouros = logradourosRepository.findById(cepsInput.getLogradourosId()).get();
		ceps.setLogradouros(logradouros);
		
		Ceps cepsSalva = cepsRepository.save(ceps);
		
		return cepsSalva;
	}
	
	//Update
	@Transactional
	public Ceps atualizar(Long id, CepsInput cepsInput) {
		Ceps cepsSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(cepsInput, cepsSalva, "id");
		
		Bairros bairros = bairrosRepository.findById(cepsInput.getBairrosId()).get();
		cepsSalva.setBairros(bairros);
		
		Logradouros logradouros = logradourosRepository.findById(cepsInput.getLogradourosId()).get();
		cepsSalva.setLogradouros(logradouros);
		
		System.err.println("Esta no service 1 - " + cepsSalva.getIdentificacao()+ " " + cepsSalva.getCep());

		return cepsRepository.save(cepsSalva);
	}
		
	public Ceps buscarPeloCodigo(Long id) {
		Optional<Ceps> cepsSalva = cepsRepository.findById(id);
		if (cepsSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return cepsSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		cepsRepository.deleteById(id);
	}
	

}
