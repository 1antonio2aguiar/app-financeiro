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

import com.financeiro.financeiroapi.filter.TiposLogradourosFilter;
import com.financeiro.financeiroapi.input.TiposLogradourosInput;
import com.financeiro.financeiroapi.model.TiposLogradouros;
import com.financeiro.financeiroapi.repository.TiposLogradourosRepository;
import com.financeiro.financeiroapi.service.TiposLogradourosService;

@RestController
@RequestMapping("/tiposLogradouros")
public class TiposLogradourosController {
	
	@Autowired private TiposLogradourosRepository tiposLogradourosRepository;
	@Autowired private TiposLogradourosService    tiposLogradourosService;
	
	// Lista de tipos logradouros com Paginacao
	@GetMapping
	public Page<TiposLogradouros> pesquisar(TiposLogradourosFilter tiposLogradourosFilter, Pageable pageable) {
	    return tiposLogradourosRepository.filtrar(tiposLogradourosFilter, pageable);
	}
	
	// Lista de tipos logradouros sem Paginacao
	@GetMapping("/list")
	public List<TiposLogradouros> pesquisar(TiposLogradourosFilter tiposLogradourosFilter) {
		return tiposLogradourosRepository.filtrar(tiposLogradourosFilter);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TiposLogradouros> BuscarPorId(@PathVariable Long id) {
		
		return tiposLogradourosRepository.findById(id)
				.map(tiposLogradouros -> ResponseEntity.ok(tiposLogradouros))
				.orElse(ResponseEntity.notFound().build());
 		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TiposLogradouros inserir(@Valid @RequestBody TiposLogradouros tiposLogradouros) {
		return tiposLogradourosService.salvar(tiposLogradouros);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TiposLogradouros> atualizar(@Valid @PathVariable Long id, @RequestBody TiposLogradouros tiposLogradouros){
		
		if(!tiposLogradourosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		tiposLogradouros.setId(id);
		tiposLogradouros = tiposLogradourosService.salvar(tiposLogradouros);
		return ResponseEntity.ok(tiposLogradouros);
			
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!tiposLogradourosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		tiposLogradourosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}

}
