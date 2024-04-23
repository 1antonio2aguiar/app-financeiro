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
import com.financeiro.financeiroapi.filter.TiposGruposFinanceirosFilter;
import com.financeiro.financeiroapi.input.GruposFinanceirosInput;
import com.financeiro.financeiroapi.input.TiposGruposFinanceirosInput;
import com.financeiro.financeiroapi.model.GruposFinanceiros;
import com.financeiro.financeiroapi.model.TiposGruposFinanceiros;
import com.financeiro.financeiroapi.repository.TiposGruposFinanceirosRepository;
import com.financeiro.financeiroapi.service.TiposGruposFinanceirosService;

@RestController
@RequestMapping("/tiposGruposFinanceiros")
public class TiposGruposFinanceirosController {
	
	@Autowired private TiposGruposFinanceirosRepository tiposGruposFinanceirosRepository;
	@Autowired private TiposGruposFinanceirosService tiposGruposFinanceirosService;
	
	// Lista de grupos financeiros com Paginacao
	@GetMapping
	public Page<TiposGruposFinanceiros> pesquisar(TiposGruposFinanceirosFilter tiposGruposFinanceirosFilter, Pageable pageable) {
	    return tiposGruposFinanceirosRepository.filtrar(tiposGruposFinanceirosFilter, pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TiposGruposFinanceiros> BuscarPorId(@PathVariable Long id) {
		
		return tiposGruposFinanceirosRepository.findById(id)
				.map(tiposGruposFinanceiros -> ResponseEntity.ok(tiposGruposFinanceiros))
				.orElse(ResponseEntity.notFound().build());
 		
	}
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TiposGruposFinanceiros> 
		adicionar(@Valid @RequestBody TiposGruposFinanceirosInput tiposGruposFinanceirosInput, HttpServletResponse response) {
		TiposGruposFinanceiros tiposGruposFinanceirosSalva = 
				tiposGruposFinanceirosService.save(tiposGruposFinanceirosInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(tiposGruposFinanceirosSalva);
	}
	 
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<TiposGruposFinanceiros> 
	atualizar(@PathVariable Long id, @Validated @RequestBody TiposGruposFinanceirosInput tiposGruposFinanceirosInput) {
		TiposGruposFinanceiros tiposGruposFinanceirosSalva = 
				tiposGruposFinanceirosService.atualizar(id, tiposGruposFinanceirosInput);
		return ResponseEntity.ok(tiposGruposFinanceirosSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!tiposGruposFinanceirosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		tiposGruposFinanceirosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}	
}
