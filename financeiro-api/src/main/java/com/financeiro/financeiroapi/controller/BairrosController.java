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

import com.financeiro.financeiroapi.filter.BairrosFilter;
import com.financeiro.financeiroapi.input.BairrosInput;
import com.financeiro.financeiroapi.model.Bairros;
import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.repository.BairrosRepository;
import com.financeiro.financeiroapi.service.BairrosService;

@RestController
@RequestMapping("/bairros")
public class BairrosController {
	
	
	@Autowired private BairrosRepository bairrosRepository;
	@Autowired private BairrosService    bairrosService;
	
	// Lista de bairros com Paginacao
	@GetMapping
	public Page<Bairros> pesquisar(BairrosFilter bairrosFilter, Pageable pageable) {
	    return bairrosRepository.filtrar(bairrosFilter, pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Bairros> BuscarPorId(@PathVariable Long id) {
		
		return bairrosRepository.findById(id)
				.map(bairros -> ResponseEntity.ok(bairros))
				.orElse(ResponseEntity.notFound().build());
 	}
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Bairros> adicionar(@Valid @RequestBody BairrosInput bairrosInput, HttpServletResponse response) {
		
		Bairros bairrosSalva = bairrosService.save(bairrosInput);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bairrosSalva);
		
	}
	
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<Bairros> atualizar(@PathVariable Long id, @Validated @RequestBody BairrosInput bairrosInput) {
		Bairros bairrosSalva = bairrosService.atualizar(id, bairrosInput);
		return ResponseEntity.ok(bairrosSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!bairrosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		bairrosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}
}
