package com.financeiro.financeiroapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.financeiroapi.filter.EstadosFilter;
import com.financeiro.financeiroapi.model.Estados;
import com.financeiro.financeiroapi.repository.EstadosRepository;
import com.financeiro.financeiroapi.service.EstadosService;

@RestController
@RequestMapping("/estados")
public class EstadosController {
	
	@Autowired private EstadosRepository estadosRepository;
	@Autowired public EstadosService estadosService;
	
	
	// Lista de estados com Paginacao
	@GetMapping
	public Page<Estados> pesquisar(EstadosFilter estadosFilter, Pageable pageable) {
	    return estadosRepository.filtrar(estadosFilter, pageable);
	}
	
	// Lista de estados sem Paginacao
	@GetMapping("/list")
	public List<Estados> pesquisar(EstadosFilter estadosFilter) {
		return estadosRepository.filtrar(estadosFilter);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estados> BuscarPorId(@PathVariable Long id) {
		
		return estadosRepository.findById(id)
				.map(estados -> ResponseEntity.ok(estados))
				.orElse(ResponseEntity.notFound().build());
 		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estados inserir(@Valid @RequestBody Estados estados) {
		return estadosService.salvar(estados);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Estados> atualizar(@Valid @PathVariable Long id, @RequestBody Estados estados){
		
		if(!estadosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		estados.setId(id);
		estados = estadosService.salvar(estados);
		return ResponseEntity.ok(estados);
			
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!estadosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		estadosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}
	
}
