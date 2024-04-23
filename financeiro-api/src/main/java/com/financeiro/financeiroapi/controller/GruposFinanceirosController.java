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

import com.financeiro.financeiroapi.filter.GruposFinanceirosFilter;
import com.financeiro.financeiroapi.input.GruposFinanceirosInput;
import com.financeiro.financeiroapi.model.GruposFinanceiros;
import com.financeiro.financeiroapi.repository.GruposFinanceirosRepository;
import com.financeiro.financeiroapi.service.GruposFinanceirosService;

@RestController
@RequestMapping("/gruposFinanceiros")
public class GruposFinanceirosController {
	
	@Autowired private GruposFinanceirosRepository gruposFinanceirosRepository;
	@Autowired private GruposFinanceirosService gruposFinanceirosService;
	
	// Lista de grupos financeiros com Paginacao
	@GetMapping
	public Page<GruposFinanceiros> pesquisar(GruposFinanceirosFilter gruposFinanceirosFilter, Pageable pageable) {
	    return gruposFinanceirosRepository.filtrar(gruposFinanceirosFilter, pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GruposFinanceiros> BuscarPorId(@PathVariable Long id) {
		
		return gruposFinanceirosRepository.findById(id)
				.map(gruposFinanceiros -> ResponseEntity.ok(gruposFinanceiros))
				.orElse(ResponseEntity.notFound().build());
 		
	}
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<GruposFinanceiros> 
		adicionar(@Valid @RequestBody GruposFinanceirosInput gruposFinanceirosInput, HttpServletResponse response) {
		GruposFinanceiros gruposFinanceirosSalva = gruposFinanceirosService.save(gruposFinanceirosInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(gruposFinanceirosSalva);
	}
	 
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<GruposFinanceiros> 
	atualizar(@PathVariable Long id, @Validated @RequestBody GruposFinanceirosInput gruposFinanceirosInput) {
		GruposFinanceiros gruposFinanceirosSalva = gruposFinanceirosService.atualizar(id, gruposFinanceirosInput);
		return ResponseEntity.ok(gruposFinanceirosSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!gruposFinanceirosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		gruposFinanceirosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}	
}
