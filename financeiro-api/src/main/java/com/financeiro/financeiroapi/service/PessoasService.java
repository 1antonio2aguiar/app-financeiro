package com.financeiro.financeiroapi.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.exception.NegocioException;
import com.financeiro.financeiroapi.input.PessoasInput;
import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.repository.PessoasRepository;

import lombok.var;

@Service
public class PessoasService {
	
	@Autowired public PessoasRepository pessoasRepository;
	
	public Pessoas buscar(Long id) {
		return pessoasRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Pessoa não encontrada!"));
	}
	
	// Insert 
	@Transactional 
	public Pessoas save(PessoasInput pessoasInput) {
		
		//System.err.println("Esta no service 1 - " + cidadesInput.getEstado());
		
		Pessoas pessoas = new Pessoas();
		BeanUtils.copyProperties(pessoasInput, pessoas, "id");
		
		// Retira caracteres, traço e ponto do cpf/cnpj.
		var cpfCnpj = pessoasInput.getCpfCnpj().replaceAll("\\p{Punct}", "");
		pessoasInput.setCpfCnpj(cpfCnpj);
		pessoas.setCpfCnpj(cpfCnpj);
				
		//Antes de salvar verifica se o cpf/cnpj já esta cadastrado. se sim retorna erro.
		// O metodo retorna optional esta logica transforma boolean
		boolean cpfCnpjCadastrado = pessoasRepository.findByCpfCnpj(pessoasInput.getCpfCnpj())
				.stream()
				.anyMatch(cpfCnpjDuplicado -> !cpfCnpjDuplicado.equals(pessoas));
		
		if(cpfCnpjCadastrado) {
			// Se o cpf/cnpj já estiver cadastrado entra aqui.
			if(pessoasInput.getFisicaJuridica().getDescricao() == "FISÍCA") {
				throw new NegocioException("CPF já cadastrado.");
			} else {
				throw new NegocioException("CNPJ já cadastrado.");
			}
		}

		Pessoas pessoasSalva = pessoasRepository.save(pessoas);
		return pessoasSalva;
	}

	//Update
	@Transactional
	public Pessoas atualizar(Long id, PessoasInput pessoasInput) {
		
		//System.err.println("Esta tentando gravar");
		
		// Retira caracteres, traço e ponto do cpf/cnpj.
		var cpfCnpj = pessoasInput.getCpfCnpj().replaceAll("\\p{Punct}", "");
		pessoasInput.setCpfCnpj(cpfCnpj);
				
		Pessoas pessoasSalva = buscarPeloCodigo(id);
		
		//System.err.println("Estes são os dados - " + pessoasInput.getDataRegistro()+ " " + pessoasInput.getCpfCnpj());

		BeanUtils.copyProperties(pessoasInput, pessoasSalva, "id");
		
		return pessoasRepository.save(pessoasSalva);
	}
	
	public Pessoas buscarPeloCodigo(Long id) {
		Optional<Pessoas> pessoasSalva = pessoasRepository.findById(id);
		if (pessoasSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoasSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		pessoasRepository.deleteById(id);
	}

}


