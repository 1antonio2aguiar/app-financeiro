package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.input.ContratosInput;
import com.financeiro.financeiroapi.model.Contratos;
import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.model.TiposGruposFinanceiros;
import com.financeiro.financeiroapi.repository.ContratosRepository;
import com.financeiro.financeiroapi.repository.PessoasRepository;
import com.financeiro.financeiroapi.repository.TiposGruposFinanceirosRepository;

@Service
public class ContratosService {
	
	@Autowired private ContratosRepository contratosRepository;
	@Autowired private PessoasRepository pessoasRepository;
	@Autowired private TiposGruposFinanceirosRepository tiposGruposFinanceirosRepository;
	
	// Insert 
	@Transactional 
	public Contratos save(ContratosInput contratosInput) {
		
		Contratos contratos = new Contratos();
		BeanUtils.copyProperties(contratosInput, contratos, "id");
		
		// BUSCO PESSOA CONTRATANTE
		Pessoas pessoasContratante = pessoasRepository.findById(contratosInput.getContratanteId()).get();
		contratos.setPessoaContratante(pessoasContratante);
		
		// BUSCO PESSOA CONTRATADO
		Pessoas pessoasContratado = pessoasRepository.findById(contratosInput.getContratadoId()).get();
		contratos.setPessoaContratado(pessoasContratado);
		
		// BUSCO TIPO GRUPO FINANCEIRO
		TiposGruposFinanceiros tiposGruposFinanceiros = 
				tiposGruposFinanceirosRepository.findById(contratosInput.getTipoGrupoFinanceiroId()).get();
		contratos.setTiposGruposFinanceiros(tiposGruposFinanceiros);
		
		Contratos contratosSalva = contratosRepository.save(contratos);
		return contratosSalva;
	}
	
	//Update
	@Transactional
	public Contratos atualizar(Long id, ContratosInput contratosInput) {
		Contratos contratosSalva = buscarPeloCodigo(id);
		
		BeanUtils.copyProperties(contratosInput, contratosSalva, "id");
		
		// BUSCO PESSOA CONTRATANTE
		Pessoas pessoasContratante = pessoasRepository.findById(contratosInput.getContratanteId()).get();
		contratosSalva.setPessoaContratante(pessoasContratante);
		
		// BUSCO PESSOA CONTRATADO
		Pessoas pessoasContratado = pessoasRepository.findById(contratosInput.getContratadoId()).get();
		contratosSalva.setPessoaContratado(pessoasContratado);
		
		// BUSCO TIPO GRUPO FINANCEIRO
		TiposGruposFinanceiros tiposGruposFinanceiros = 
				tiposGruposFinanceirosRepository.findById(contratosInput.getTipoGrupoFinanceiroId()).get();
		contratosSalva.setTiposGruposFinanceiros(tiposGruposFinanceiros);

		return contratosRepository.save(contratosSalva);
	}
	
	public Contratos buscarPeloCodigo(Long id) {
		Optional<Contratos> contratos = contratosRepository.findById(id);
		if (contratos == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return contratos.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		contratosRepository.deleteById(id);
	}
}
