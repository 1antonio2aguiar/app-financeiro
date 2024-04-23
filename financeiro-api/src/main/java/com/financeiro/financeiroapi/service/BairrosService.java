package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.input.BairrosInput;
import com.financeiro.financeiroapi.input.CidadesInput;
import com.financeiro.financeiroapi.model.Bairros;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.Estados;
import com.financeiro.financeiroapi.repository.BairrosRepository;
import com.financeiro.financeiroapi.repository.CidadesRepository;
import com.financeiro.financeiroapi.repository.EstadosRepository;
import com.financeiro.financeiroapi.repository.bairros.BairrosCustonRepository;
import com.financeiro.financeiroapi.repository.cidades.CidadesCustonRepository;

@Service
public class BairrosService {
	
	@Autowired public BairrosRepository bairrosRepository;
	@Autowired public CidadesRepository cidadesRepository;
	@Autowired public BairrosCustonRepository bairrosCustonRepository;
	
	
	// Insert
	@Transactional 
	public Bairros save(BairrosInput bairrosInput) {
		
		//System.err.println("Esta no service 1 - " + cidadesInput.getEstado());
		
		Bairros bairros = new Bairros();
		BeanUtils.copyProperties(bairrosInput, bairros, "id");
		
		//Antes de salvar verifica se a bairro/cidade já esta cadastrado. se sim retorna erro.
		// O metodo retorna optional esta logica transforma para boolean
		boolean ufCadastrada = bairrosCustonRepository.find(bairrosInput.getCidadesId(), bairrosInput.getNome())
				.stream()
				.anyMatch(ufDuplicada -> !ufDuplicada.equals(bairros));
		
		if(ufCadastrada) {
			// Se a UF já estiver cadastrado entra aqui.
			throw new NegocioException("BAIRRO já cadastrado!");
		}

		// BUSCO CIDADE E INSIRO NO BAIRROS
		Cidades cidades = cidadesRepository.findById(bairrosInput.getCidadesId()).get();
		bairros.setCidades(cidades);
		Bairros bairrosSalva = bairrosRepository.save(bairros);
		
		return bairrosSalva;
	}
	
	//Update
	@Transactional
	public Bairros atualizar(Long id, BairrosInput bairrosInput) {
		Bairros bairrosSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(bairrosInput, bairrosSalva, "id");
		
		Cidades cidades = cidadesRepository.findById(bairrosInput.getCidadesId()).get();
		bairrosSalva.setCidades(cidades);

		return bairrosRepository.save(bairrosSalva);
	}
		
	public Bairros buscarPeloCodigo(Long id) {
		Optional<Bairros> bairrosSalva = bairrosRepository.findById(id);
		if (bairrosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return bairrosSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		bairrosRepository.deleteById(id);
	}

}
