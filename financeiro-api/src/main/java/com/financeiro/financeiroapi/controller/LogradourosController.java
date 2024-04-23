package com.financeiro.financeiroapi.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.financeiroapi.filter.LogradourosFilter;
import com.financeiro.financeiroapi.input.BairrosInput;
import com.financeiro.financeiroapi.input.LogradourosInput;
import com.financeiro.financeiroapi.model.Bairros;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.repository.LogradourosRepository;
import com.financeiro.financeiroapi.service.LogradourosService;

@RestController
@RequestMapping("/logradouros")
public class LogradourosController {
	
	@Autowired private LogradourosRepository logradourosRepository;
	@Autowired private LogradourosService  logradourosService;
	
	// Lista de logradouros com Paginacao
	@GetMapping
	public Page<Logradouros> pesquisar(LogradourosFilter logradourosFilter, Pageable pageable) {
	    return logradourosRepository.filtrar(logradourosFilter, pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Logradouros> BuscarPorId(@PathVariable Long id) {
		
		return logradourosRepository.findById(id)
				.map(logradouros -> ResponseEntity.ok(logradouros))
				.orElse(ResponseEntity.notFound().build());
 		
	}
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Logradouros> adicionar(@Valid @RequestBody LogradourosInput logradourosInput, HttpServletResponse response) {
		
		Logradouros logradourosSalva = logradourosService.save(logradourosInput);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(logradourosSalva);
		
	}
	
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<Logradouros> atualizar(@PathVariable Long id, @Validated @RequestBody LogradourosInput logradourosInput) {
		Logradouros logradourosSalva = logradourosService.atualizar(id, logradourosInput);
		return ResponseEntity.ok(logradourosSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!logradourosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		logradourosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}

}
