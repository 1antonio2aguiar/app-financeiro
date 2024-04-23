package com.financeiro.financeiroapi.service;

import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.input.TiposGruposFinanceirosInput;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.GruposFinanceiros;
import com.financeiro.financeiroapi.model.TiposGruposFinanceiros;
import com.financeiro.financeiroapi.repository.GruposFinanceirosRepository;
import com.financeiro.financeiroapi.repository.TiposGruposFinanceirosRepository;

@Service
public class TiposGruposFinanceirosService {
	
	@Autowired public TiposGruposFinanceirosRepository tiposGruposFinanceirosRepository;
	@Autowired public GruposFinanceirosRepository gruposFinanceirosRepository;
	
	public TiposGruposFinanceiros buscar(Long id) {
		System.err.println(" e aqui passou?? " + id);
		return tiposGruposFinanceirosRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Registro não encontrado!"));
	}
	
	// Insert
	@Transactional 
	public TiposGruposFinanceiros save(TiposGruposFinanceirosInput tiposgruposFinanceirosInput) {
		TiposGruposFinanceiros tiposGruposFinanceiros = new TiposGruposFinanceiros();
		BeanUtils.copyProperties(tiposgruposFinanceirosInput, tiposGruposFinanceiros, "id");
		
		// BUSCO GRUPO FINANCEIRO E INSIRO EM TIPOS GRUPOS FINANCEIROS
		GruposFinanceiros gruposFinanceiros = gruposFinanceirosRepository.findById(tiposgruposFinanceirosInput.getGrupoFinanceiroId()).get();
		tiposGruposFinanceiros.setGruposFinanceiros(gruposFinanceiros);
		
		TiposGruposFinanceiros tiposgruposFinanceirosSalva = tiposGruposFinanceirosRepository.save(tiposGruposFinanceiros);
		return tiposgruposFinanceirosSalva;
	}
	
	//Update
	@Transactional
	public TiposGruposFinanceiros atualizar(Long id, TiposGruposFinanceirosInput tiposgruposFinanceirosInput) {
		TiposGruposFinanceiros tiposgruposFinanceirosSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(tiposgruposFinanceirosInput, tiposgruposFinanceirosSalva, "id");

		return tiposGruposFinanceirosRepository.save(tiposgruposFinanceirosSalva);
	}
	
	public TiposGruposFinanceiros buscarPeloCodigo(Long id) {
		Optional<TiposGruposFinanceiros> tiposgruposFinanceirosSalva = tiposGruposFinanceirosRepository.findById(id);
		if (tiposgruposFinanceirosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return tiposgruposFinanceirosSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		tiposGruposFinanceirosRepository.deleteById(id);
	}

}
