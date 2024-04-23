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

import com.financeiro.financeiroapi.filter.CidadesFilter;
import com.financeiro.financeiroapi.input.CidadesInput;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.Estados;
import com.financeiro.financeiroapi.repository.CidadesRepository;
import com.financeiro.financeiroapi.service.CidadesService;

@RestController
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired private CidadesRepository cidadesRepository;
	@Autowired private CidadesService cidadesService;

	// Lista de cidades com Paginacao
	@GetMapping
	public Page<Cidades> pesquisar(CidadesFilter cidadesFilter, Pageable pageable) {
	    return cidadesRepository.filtrar(cidadesFilter, pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cidades> BuscarPorId(@PathVariable Long id) {
		
		return cidadesRepository.findById(id)
				.map(cidades -> ResponseEntity.ok(cidades))
				.orElse(ResponseEntity.notFound().build());
 		
	}
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cidades> adicionar(@Valid @RequestBody CidadesInput cidadesInput, HttpServletResponse response) {
		
		Cidades cidadesSalva = cidadesService.save(cidadesInput);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadesSalva);
		
	}
	 
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<Cidades> atualizar(@PathVariable Long id, @Validated @RequestBody CidadesInput cidadesInput) {
		Cidades cidadesSalva = cidadesService.atualizar(id, cidadesInput);
		return ResponseEntity.ok(cidadesSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!cidadesRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		cidadesService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}	
}
