package com.financeiro.financeiroapi.service;
 
import javax.transaction.Transactional;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.input.EnderecosInput;
import com.financeiro.financeiroapi.model.Bairros;
import com.financeiro.financeiroapi.model.Ceps;
import com.financeiro.financeiroapi.model.Enderecos;
import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.repository.BairrosRepository;
import com.financeiro.financeiroapi.repository.CepsRepository;
import com.financeiro.financeiroapi.repository.EnderecosRepository;
import com.financeiro.financeiroapi.repository.LogradourosRepository;
import com.financeiro.financeiroapi.repository.PessoasRepository;

@Service
public class EnderecosService {
	
	@Autowired EnderecosRepository enderecosRepository;
	@Autowired BairrosRepository bairrosRepository;
	@Autowired LogradourosRepository logradourosRepository;
	@Autowired PessoasRepository pessoasRepository;
	@Autowired CepsRepository cepsRepository;
	@Autowired PessoasService pessoasService;
	
	@Transactional
	public Enderecos criar(Enderecos enderecos) {
		Pessoas pessoas = pessoasService.buscar(enderecos.getPessoa().getId());
		
		enderecos.setPessoa(pessoas);
		//enderecos.setCadastro(OffsetDateTime.now());
		//enderecos.setStatus(StatusEndereco.ATIVO);
		
		return enderecosRepository.save(enderecos);
		
	}
	
	// Insert
	@Transactional 
	public Enderecos save(EnderecosInput enderecosInput) {
		
		//System.err.println("Esta no service 1 - " + enderecosInput.getTipoEndereco());
		
		//Logica para verificar se o endereço já esta cadastrado para a pessoa
		
		Enderecos enderecos = new Enderecos();
		BeanUtils.copyProperties(enderecosInput, enderecos, "id");
		
		// BUSCO PESSOA E INSIRO NO ENDERECOS
		Pessoas pessoas = pessoasRepository.findById(enderecosInput.getPessoa()).get();
		enderecos.setPessoa(pessoas);
		
		//BUSCO CEP E INSIRO EM ENDERECOS
		Ceps ceps = cepsRepository.findById(enderecosInput.getCep()).get();
		enderecos.setCeps(ceps);
		
		// BUSCO BAIRRO E INSIRO NO ENDERECOS
		Bairros bairros = bairrosRepository.findById(enderecosInput.getBairro()).get();
		enderecos.setBairros(bairros);
		
		//BUSCO LOGRADOURO E INSIRO EM ENDERECOS
		Logradouros logradouros = logradourosRepository.findById(enderecosInput.getLogradouro()).get();
		enderecos.setLogradouros(logradouros);
		
		Enderecos enderecosSalva = enderecosRepository.save(enderecos);
		
		return enderecosSalva;
	}
	
	//Update - No update não podem ser alterados a pessoa e o cep
	@Transactional
	public Enderecos atualizar(Long id, EnderecosInput enderecosInput) {
		Enderecos enderecosSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(enderecosInput, enderecosSalva, "id");
		
		Bairros bairros = bairrosRepository.findById(enderecosInput.getBairro()).get();
		enderecosSalva.setBairros(bairros);
		
		Logradouros logradouros = logradourosRepository.findById(enderecosInput.getLogradouro()).get();
		enderecosSalva.setLogradouros(logradouros);

		return enderecosRepository.save(enderecosSalva);
	}
		
	public Enderecos buscarPeloCodigo(Long id) {
		Optional<Enderecos> enderecosSalva = enderecosRepository.findById(id);
		if (enderecosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return enderecosSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		enderecosRepository.deleteById(id);
	}
	
	/*public Enderecos buscar(Long enderecoId) {
		return enderecosRepository.findById(enderecoId)
				.orElseThrow(() -> new NegocioException("Enderecço não encontrado"));
	}*/
	
	@Transactional
	public void excuirPorPessoa(Long id) {
		enderecosRepository.deleteByPessoaId(id);
	}
	
}
