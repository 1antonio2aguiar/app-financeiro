package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.input.ContatosInput;
import com.financeiro.financeiroapi.model.Contatos;
import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.repository.ContatosRepository;
import com.financeiro.financeiroapi.repository.PessoasRepository;

@Service
public class ContatosService {
	
	@Autowired ContatosRepository contatosRepository;
	@Autowired PessoasRepository pessoasRepository;
	
	// Insert
	@Transactional 
	public Contatos save(ContatosInput contatosInput) {
		
		//System.err.println("Esta no service 1 - " + enderecosInput.getTipoEndereco());
		
		//Logica para verificar se o contato já esta cadastrado para a pessoa
		
		Contatos contatos = new Contatos();
		BeanUtils.copyProperties(contatosInput, contatos, "id");
		
		// BUSCO PESSOA E INSIRO NO ENDERECOS
		Pessoas pessoas = pessoasRepository.findById(contatosInput.getPessoa()).get();
		contatos.setPessoa(pessoas);
		
		Contatos contatosSalva = contatosRepository.save(contatos);
		
		return contatosSalva;
	}
	
	@Transactional
	public Contatos atualizar(Long id, ContatosInput contatosInput) {
		Contatos contatosSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(contatosInput, contatosSalva, "id");
		
		return contatosRepository.save(contatosSalva);
	}
		
	public Contatos buscarPeloCodigo(Long id) {
		Optional<Contatos> contatosSalva = contatosRepository.findById(id);
		if (contatosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return contatosSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		contatosRepository.deleteById(id);
	}
	
	@Transactional
	public void excuirPorPessoa(Long id) {
		contatosRepository.deleteByPessoaId(id);
	}

}
