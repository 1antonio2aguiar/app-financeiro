package com.financeiro.financeiroapi.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.financeiroapi.filter.EnderecosFilter;
import com.financeiro.financeiroapi.input.EnderecosInput;
import com.financeiro.financeiroapi.model.Enderecos;
import com.financeiro.financeiroapi.repository.EnderecosRepository;
import com.financeiro.financeiroapi.service.EnderecosService;

@RestController
@RequestMapping("/enderecos")
public class EnderecosController {
	
	@Autowired private EnderecosService enderecosService;
	@Autowired private EnderecosRepository enderecosRepository;
	//@Autowired private EnderecosAssembler enderecosAssembler;
	
	// Lista de endereços com Paginacao
	@GetMapping
	public Page<Enderecos> pesquisar(EnderecosFilter enderecosFilter, Pageable pageable) {
	    return enderecosRepository.filtrar(enderecosFilter, pageable);
	}
	
	//Tonhas busca lista de enderecos por pessoa
	@GetMapping(value = "findByPessoaId")
	@ResponseBody
	public ResponseEntity<List<Enderecos>> findByPessoaId(@RequestParam(name = "pessoaId") Long pessoaId){
		//System.err.println("Bateu aqui " + pessoaId);
		List<Enderecos> enderecos = enderecosRepository.findByPessoaId(pessoaId);
		return new ResponseEntity<List<Enderecos>>(enderecos, HttpStatus.OK);
	}
	
	// Tonhas busca endereco por id.
	@GetMapping("/{id}")
	public ResponseEntity<Enderecos> buscarPeloCodigo(@PathVariable Long id) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
	    Optional<Enderecos> enderecos = enderecosRepository.findById(id);
	    return enderecos != null
	        ? ResponseEntity.ok(enderecos.get())
	        : ResponseEntity.notFound().build();
	}
	 
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Enderecos> adicionar(@Valid @RequestBody EnderecosInput enderecosInput, HttpServletResponse response) {
		Enderecos enderecosSalva = enderecosService.save(enderecosInput);		
		return ResponseEntity.status(HttpStatus.CREATED).body(enderecosSalva);
	}
	
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<Enderecos> atualizar(@PathVariable Long id, @Validated @RequestBody EnderecosInput enderecosInput) {
		Enderecos enderecosSalva = enderecosService.atualizar(id, enderecosInput);
		return ResponseEntity.ok(enderecosSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!enderecosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		enderecosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}
	
	/*@GetMapping("/{id}")
	public ResponseEntity<EnderecosDTO> buscar(@PathVariable Long id) {
		return enderecosRepository.findById(id)
				.map(endereco -> ResponseEntity.ok(enderecosAssembler.toModel(endereco)))
				.orElse(ResponseEntity.notFound().build());
	}*/  	
} 

/* 
 * @PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EnderecosDTO criar(@Valid @RequestBody EnderecosInput enderecosInput) {
		Enderecos novoEndereco = enderecosAssembler.toEntity(enderecosInput);
		Enderecos enderecosCriar = enderecosService.criar(novoEndereco);
		return enderecosAssembler.toModel(enderecosCriar);
}*/
