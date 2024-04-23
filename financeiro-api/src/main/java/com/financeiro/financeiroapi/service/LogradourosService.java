package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.input.LogradourosInput;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.model.TiposLogradouros;
import com.financeiro.financeiroapi.repository.CidadesRepository;
import com.financeiro.financeiroapi.repository.LogradourosRepository;
import com.financeiro.financeiroapi.repository.TiposLogradourosRepository;
import com.financeiro.financeiroapi.repository.logradouros.LogradourosCustonRepository;

@Service
public class LogradourosService {
	
	@Autowired public LogradourosRepository logradourosRepository;
	@Autowired public TiposLogradourosRepository tiposLogradourosRepository;
	@Autowired public CidadesRepository cidadesRepository;
	@Autowired public LogradourosCustonRepository logradourosCustonRepository;
	
	// Insert
	@Transactional 
	public Logradouros save(LogradourosInput logradourosInput) {
		
		//System.err.println("Esta no service 1 - " + cidadesInput.getEstado());
		
		Logradouros logradouros = new Logradouros();
		BeanUtils.copyProperties(logradourosInput, logradouros, "id");
		
		//Antes de salvar verifica se o logradouro já esta cadastrado para a cidade. se sim retorna erro.
		// O metodo retorna optional esta logica transforma para boolean
		boolean logradCadastrado = logradourosCustonRepository.find(logradourosInput.getCidadesId(), 
		                                                            logradourosInput.getTiposLogradourosId(), 
		                                                            logradourosInput.getNome())
				.stream()
				.anyMatch(logradDuplicado -> !logradDuplicado.equals(logradouros));
		
		if(logradCadastrado) {
			// Se o logradouro já estiver cadastrado entra aqui.
			throw new NegocioException("LOGRADOURO já cadastrado!");
		}

		// BUSCO CIDADE E INSIRO NO LOGRADOUROS
		Cidades cidades = cidadesRepository.findById(logradourosInput.getCidadesId()).get();
		logradouros.setCidades(cidades);
		
		//BUSCO TIPO LOGRADOURO E INSIRO EM LOGRADOUROS
		TiposLogradouros tiposLogradouros = tiposLogradourosRepository.findById(logradourosInput.getTiposLogradourosId()).get();
		logradouros.setTiposLogradouros(tiposLogradouros);
		
		Logradouros logradourosSalva = logradourosRepository.save(logradouros);
		
		return logradourosSalva;
	} 
	
	//Update
	@Transactional
	public Logradouros atualizar(Long id, LogradourosInput logradourosInput) {
		Logradouros logradourosSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(logradourosInput, logradourosSalva, "id");
		
		Cidades cidades = cidadesRepository.findById(logradourosInput.getCidadesId()).get();
		logradourosSalva.setCidades(cidades);

		return logradourosRepository.save(logradourosSalva);
	}
		
	public Logradouros buscarPeloCodigo(Long id) {
		Optional<Logradouros> logradourosSalva = logradourosRepository.findById(id);
		if (logradourosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return logradourosSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		logradourosRepository.deleteById(id);
	}
	
} 
