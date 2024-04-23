package com.financeiro.financeiroapi.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.financeiroapi.filter.DocumentosFilter;
import com.financeiro.financeiroapi.input.DocumentosInput;
import com.financeiro.financeiroapi.model.Documentos;
import com.financeiro.financeiroapi.outputs.DocumentosOutput;
import com.financeiro.financeiroapi.repository.DocumentosRepository;
import com.financeiro.financeiroapi.service.DocumentosService;

@RestController
@RequestMapping("/documentos")
public class DocumentosController {
	
	@Autowired private DocumentosService documentosService;
	@Autowired private DocumentosRepository documentosRepository;
	
	// Lista de endereços com Paginacao
	@GetMapping
	public Page<Documentos> pesquisar(DocumentosFilter documentosFilter, Pageable pageable) {
	    return documentosRepository.filtrar(documentosFilter, pageable);
	}
	
	//Tonhas busca lista de documentos por pessoa
	@GetMapping(value = "findByPessoaId")
	@ResponseBody
	public ResponseEntity<List<Documentos>> findByPessoaId(@RequestParam(name = "pessoaId") Long pessoaId){
	//System.err.println("Bateu aqui " + pessoaId);
		List<Documentos> documentos = documentosRepository.findByPessoaId(pessoaId);
		return new ResponseEntity<List<Documentos>>(documentos, HttpStatus.OK);
	}
	
	// Tonhas busca endereco por id.
	@GetMapping("/{id}")
	public ResponseEntity<DocumentosOutput> BuscarPorId(@PathVariable Long id) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
//	    Optional<Documentos> documentos = documentosRepository.findById(id);
//	    return documentos != null
//	        ? ResponseEntity.ok(documentos.get())
//	        : ResponseEntity.notFound().build();
		//  OU  //
		
		DocumentosOutput documentosOutput = null;
		Optional<Documentos> documentos = documentosRepository.findById(id);
		if(documentos.isPresent()) {
			documentosOutput = new DocumentosOutput();
			
			if(documentos.get().getDataDocumento() != null){
				SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
				String dataFormatada = null;
				dataFormatada = formatoDesejado.format(documentos.get().getDataDocumento());
				documentosOutput.setDataDocumento(dataFormatada);
			}
			
			if(documentos.get().getDataExpedicao() != null){
				SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
				String dataFormatada = null;
				dataFormatada = formatoDesejado.format(documentos.get().getDataExpedicao());
				documentosOutput.setDataExpedicao(dataFormatada);
			}
			
			if(documentos.get().getDataValidade() != null){
				SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
				String dataFormatada = null;
				dataFormatada = formatoDesejado.format(documentos.get().getDataValidade());
				documentosOutput.setDataValidade(dataFormatada);
			}
			BeanUtils.copyProperties(documentos.get(), documentosOutput);
		}
		return documentosOutput != null ? ResponseEntity.ok(documentosOutput) : ResponseEntity.notFound().build();
	}
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Documentos> adicionar(@Valid @RequestBody DocumentosInput documentosInput, HttpServletResponse response) {
		
		Documentos documentosSalva = documentosService.save(documentosInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(documentosSalva);
		
	}
	
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<Documentos> atualizar(@PathVariable Long id, @Validated @RequestBody DocumentosInput documentosInput) {
		Documentos documentosSalva = documentosService.atualizar(id, documentosInput);
		return ResponseEntity.ok(documentosSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!documentosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		documentosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}	
}
