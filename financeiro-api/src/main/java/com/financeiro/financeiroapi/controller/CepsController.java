package com.financeiro.financeiroapi.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
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

import com.financeiro.financeiroapi.filter.CepsFilter;
import com.financeiro.financeiroapi.input.CepsInput;
import com.financeiro.financeiroapi.model.Ceps;
import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.outputs.CepsOutput;
import com.financeiro.financeiroapi.repository.CepsRepository;
import com.financeiro.financeiroapi.service.CepsService;

@RestController
@RequestMapping("/ceps")
public class CepsController {
	
	@Autowired private CepsRepository cepsRepository;
	@Autowired private CepsService  cepsService;
	
	// Lista de ceps com Paginacao
	@GetMapping
	public Page<Ceps> pesquisar(CepsFilter cepsFilter, Pageable pageable) {
		System.err.println("Bateu aqui " );
	    return cepsRepository.filtrar(cepsFilter, pageable);
	}
	
	// Busca por id
	@GetMapping("/{id}")
	public ResponseEntity<CepsOutput> BuscarPorId(@PathVariable Long id) {
		
		//busca dados do cep 
	    Optional<Ceps> ceps = cepsRepository.findById(id);
	    CepsOutput cepsOutput = null;
	    
	    if(ceps.isPresent()) {
	    	// Se tiver o cep cadastrado copia para logradouros output
	    	cepsOutput = new CepsOutput();
	    	BeanUtils.copyProperties(ceps.get(), cepsOutput);
	    	cepsOutput.setCidades(ceps.get().getBairros().getCidades());
	    }
	    
	    return cepsOutput != null ? ResponseEntity.ok(cepsOutput) : ResponseEntity.notFound().build();
	}
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Ceps> adicionar(@Valid @RequestBody CepsInput cepsInput, HttpServletResponse response) {
		
		Ceps cepsSalva = cepsService.save(cepsInput);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cepsSalva);
		
	}

	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<Ceps> atualizar(@PathVariable Long id, @Validated @RequestBody CepsInput cepsInput) {
		Ceps cepsSalva = cepsService.atualizar(id, cepsInput);
		return ResponseEntity.ok(cepsSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!cepsRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		cepsService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}
}
