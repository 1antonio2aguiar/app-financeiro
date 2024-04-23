package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.input.GruposFinanceirosInput;
import com.financeiro.financeiroapi.model.GruposFinanceiros;
import com.financeiro.financeiroapi.repository.GruposFinanceirosRepository;


@Service
public class GruposFinanceirosService {
	
	@Autowired public GruposFinanceirosRepository gruposFinanceirosRepository;
	
	public GruposFinanceiros buscar(Long id) {
		System.err.println(" e aqui passou?? " + id);
		return gruposFinanceirosRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Registro não encontrado!"));
	}
	
	// Insert
	@Transactional 
	public GruposFinanceiros save(GruposFinanceirosInput gruposFinanceirosInput) {
		
		GruposFinanceiros gruposFinanceiros = new GruposFinanceiros();
		BeanUtils.copyProperties(gruposFinanceirosInput, gruposFinanceiros, "id");
		
		GruposFinanceiros gruposFinanceirosSalva = gruposFinanceirosRepository.save(gruposFinanceiros);
		
		return gruposFinanceirosSalva;
	}
	
	//Update
	@Transactional
	public GruposFinanceiros atualizar(Long id, GruposFinanceirosInput gruposFinanceirosInput) {
		GruposFinanceiros gruposFinanceirosSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(gruposFinanceirosInput, gruposFinanceirosSalva, "id");

		return gruposFinanceirosRepository.save(gruposFinanceirosSalva);
	}
	
	public GruposFinanceiros buscarPeloCodigo(Long id) {
		Optional<GruposFinanceiros> gruposFinanceirosSalva = gruposFinanceirosRepository.findById(id);
		if (gruposFinanceirosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return gruposFinanceirosSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		gruposFinanceirosRepository.deleteById(id);
	}

}
