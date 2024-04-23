package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.input.DocumentosInput;
import com.financeiro.financeiroapi.model.Documentos;
import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.repository.DocumentosRepository;
import com.financeiro.financeiroapi.repository.PessoasRepository;

@Service
public class DocumentosService {
	
	@Autowired DocumentosRepository documentosRepository;
	@Autowired PessoasRepository pessoasRepository;
	
	// Insert
	@Transactional 
	public Documentos save(DocumentosInput documentosInput) {
		Documentos documentos = new Documentos();
		BeanUtils.copyProperties(documentosInput, documentos, "id");
		
		// BUSCO PESSOA E INSIRO NO ENDERECOS
		Pessoas pessoas = pessoasRepository.findById(documentosInput.getPessoa()).get();
		documentos.setPessoa(pessoas);
		
		Documentos documentosSalva = documentosRepository.save(documentos);
		
		return documentosSalva;
	}
	
	@Transactional
	public Documentos atualizar(Long id, DocumentosInput documentosInput) {
		Documentos documentosSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(documentosInput, documentosSalva, "id");
		
		return documentosRepository.save(documentosSalva);
	}
		
	public Documentos buscarPeloCodigo(Long id) {
		Optional<Documentos> documentosSalva = documentosRepository.findById(id);
		if (documentosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return documentosSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		documentosRepository.deleteById(id);
	}
	
	@Transactional
	public void excuirPorPessoa(Long id) {
		documentosRepository.deleteByPessoaId(id);
	}

}
