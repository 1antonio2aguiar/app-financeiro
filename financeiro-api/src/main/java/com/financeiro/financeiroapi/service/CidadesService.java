package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.input.CidadesInput;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.Estados;
import com.financeiro.financeiroapi.repository.CidadesRepository;
import com.financeiro.financeiroapi.repository.EstadosRepository;
import com.financeiro.financeiroapi.repository.cidades.CidadesCustonRepository;
@Service
public class CidadesService {
	
	@Autowired public CidadesRepository cidadesRepository;
	@Autowired public EstadosRepository estadosRepository;
	@Autowired public CidadesCustonRepository ccr;
	
	public Cidades buscar(Long id) {
		System.err.println(" e aqui passou?? " + id);
		return cidadesRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Cidade não encontrada!"));
	}
	
	// Insert
	@Transactional 
	public Cidades save(CidadesInput cidadesInput) {
		
		//System.err.println("Esta no service 1 - " + cidadesInput.getEstado());
		
		Cidades cidades = new Cidades();
		BeanUtils.copyProperties(cidadesInput, cidades, "id");
		
		//Antes de salvar verifica se a cidade/estado já esta cadastrado. se sim retorna erro.
		// O metodo retorna optional esta logica transforma para boolean
		boolean ufCadastrada = ccr.find(cidadesInput.getEstadosId(), cidadesInput.getNome())
				.stream()
				.anyMatch(ufDuplicada -> !ufDuplicada.equals(cidades));
		
		//System.err.println("UF " + ufCadastrada);
		
		if(ufCadastrada) {
			// Se a UF já estiver cadastrado entra aqui.
			throw new NegocioException("CIDADE já cadastrada!");
		}

		// BUSCO ESTADO E INSIRO NO CIDADES
		Estados estados = estadosRepository.findById(cidadesInput.getEstadosId()).get();
		cidades.setEstados(estados);
		Cidades cidadesSalva = cidadesRepository.save(cidades);
		
		
		return cidadesSalva;
	}
	
	//Update
	@Transactional
	public Cidades atualizar(Long id, CidadesInput cidadesInput) {
		Cidades cidadesSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(cidadesInput, cidadesSalva, "id");
		
		Estados estados = estadosRepository.findById(cidadesInput.getEstadosId()).get();
		cidadesSalva.setEstados(estados);

		return cidadesRepository.save(cidadesSalva);
	}
	
	public Cidades buscarPeloCodigo(Long id) {
		Optional<Cidades> cidadesSalva = cidadesRepository.findById(id);
		if (cidadesSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return cidadesSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		cidadesRepository.deleteById(id);
	}

}
