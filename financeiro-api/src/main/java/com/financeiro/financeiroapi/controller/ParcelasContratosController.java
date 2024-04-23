package com.financeiro.financeiroapi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.financeiroapi.filter.ContratosFilter;
import com.financeiro.financeiroapi.filter.ParcelasContratosFilter;
import com.financeiro.financeiroapi.input.ParcelasContratosInput;
import com.financeiro.financeiroapi.model.Contratos;
import com.financeiro.financeiroapi.model.ParcelasContratos;
import com.financeiro.financeiroapi.outputs.ParcelasContratosOutString;
import com.financeiro.financeiroapi.outputs.ParcelasContratosOutput;
import com.financeiro.financeiroapi.outputs.ParcelasContratosParcOut;
import com.financeiro.financeiroapi.repository.ParcelasContratosRepository;
import com.financeiro.financeiroapi.repository.parcelasContratos.ParcelasContratosCustonRepository;
import com.financeiro.financeiroapi.service.ParcelasContratosService;

@RestController
@RequestMapping("/parcelasContratos")
public class ParcelasContratosController {
	
	@Autowired private ParcelasContratosService parcelasContratosService;
	@Autowired private ParcelasContratosRepository parcelasContratosRepository;
	@Autowired private ParcelasContratosCustonRepository parcelasContratosCustonRepository;
	
	// Lista de Parcelas do Contrato com Paginacao
	@GetMapping
	public Page<ParcelasContratos> pesquisar(ParcelasContratosFilter parcelasContratosFilter, Pageable pageable) {
		//System.err.println("Buscando lista de parcelas! " + parcelasContratosFilter.getContratoId());
		
	    return parcelasContratosRepository.filtrar(parcelasContratosFilter, pageable);
	}
	
	//Busca a proxima parcela a ser cadastrada para o contrato
	@Transactional
	@GetMapping(value = "findMaxParcela") 
	public ResponseEntity<ParcelasContratosParcOut> findByContratoIdParc(@RequestParam(name = "contratoId") Long contratoId){
		//System.err.println("Bateu aqui " + contratoId);
		
		ParcelasContratosParcOut parcelasContratosParcOut = null;
		parcelasContratosParcOut = new ParcelasContratosParcOut();
	    
	    Long parcela = parcelasContratosCustonRepository.findMaxParcela(contratoId);

	    parcelasContratosParcOut = new ParcelasContratosParcOut();
	    parcelasContratosParcOut.setParcela(parcela + 1);
	    		
		return parcelasContratosParcOut != null
		        ? ResponseEntity.ok(parcelasContratosParcOut)
		        : ResponseEntity.notFound().build();
	}
	
	//Tonhas busca lista de parcealas por contrato sem paginacao
	@GetMapping(value = "findByContratosId") 
	public ResponseEntity<List<ParcelasContratosOutput>> findByContratoId(@RequestParam(name = "contratoId") Long contratoId){
		//System.err.println("Bateu aqui " + contratoId);
		
		List<ParcelasContratosOutput> parcelasContratosOutputList = new ArrayList<ParcelasContratosOutput>();
		 
		List<ParcelasContratos> parcelasContratosList = parcelasContratosRepository.findByContratosId(contratoId);
		
		if(!parcelasContratosList.isEmpty()) {
			
			for(int i = 0; i < parcelasContratosList.size(); i++){
				ParcelasContratosOutput pco = new ParcelasContratosOutput();
				
			    if(parcelasContratosList.get(i).getDataVencimento() != null){
			    	SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		    		String dataFormatada = null;
		    		dataFormatada = formatoDesejado.format(parcelasContratosList.get(i).getDataVencimento());
		    		pco.setDataVencimento(dataFormatada);
			    } 
			    
			    if(parcelasContratosList.get(i).getDataPagamento() != null){
		    		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
		    		String dataFormatada = null;
		    		dataFormatada = formatoDesejado.format(parcelasContratosList.get(i).getDataPagamento());
		    		pco.setDataPagamento(dataFormatada);
		    	}
			    BeanUtils.copyProperties(parcelasContratosList.get(i), pco);
			    parcelasContratosOutputList.add(pco);
			}
			parcelasContratosOutputList.stream()
			.sorted((p1, p2) -> p1.getParcela().compareTo(p2.getParcela()));
			//.forEach(p -> System.out.println(p));
			
			return new ResponseEntity<List<ParcelasContratosOutput>>(parcelasContratosOutputList, HttpStatus.OK);
		};
		return null;
	}
	
	// Tonhas busca parcela por id.
	@GetMapping("/{id}")
	public ResponseEntity<ParcelasContratosOutString> buscarPeloCodigo(@PathVariable Long id) {
		//System.err.println("OLHA ONDE ELE VÊIO !! ");
		
		String valor;
		
		ParcelasContratosOutString parcelasContratosOutString = null;
	    Optional<ParcelasContratos> parcelasContratos = parcelasContratosRepository.findById(id);
	    
	    if(parcelasContratos.isPresent()) {
	    	parcelasContratosOutString = new ParcelasContratosOutString();
	    	
	    	if(parcelasContratos.get().getDataVencimento() != null){
	    		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
	    		String dataFormatada = null;
	    		dataFormatada = formatoDesejado.format(parcelasContratos.get().getDataVencimento());
	    		parcelasContratosOutString.setDataVencimento(dataFormatada);
	    	}
	    	
	    	if(parcelasContratos.get().getDataPagamento() != null){
	    		SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
	    		String dataFormatada = null;
	    		dataFormatada = formatoDesejado.format(parcelasContratos.get().getDataPagamento());
	    		parcelasContratosOutString.setDataPagamento(dataFormatada);
	    	}
	    	
	    	BeanUtils.copyProperties(parcelasContratos.get(), parcelasContratosOutString);
	    	parcelasContratosOutString.setContratos(parcelasContratos.get().getContratos());
	    }
	    
	    if(!parcelasContratos.get().getValor().toString().isEmpty()) {
	    	valor = parcelasContratos.get().getValor().toString();
	    	valor = valor.replace(".",",");
	    	parcelasContratosOutString.setValor(valor);	    	
	    }
	    
		if(parcelasContratos.get().getJuros() != null) {
			valor = parcelasContratos.get().getJuros().toString();
			valor = valor.replace(".",",");
			parcelasContratosOutString.setJuros(valor);			
		}
	    if(parcelasContratos.get().getMulta() != null) {
	    	valor = parcelasContratos.get().getMulta().toString();
	    	valor = valor.replace(".",",");
	    	parcelasContratosOutString.setMulta(valor);	    	
	    }
		
		if(parcelasContratos.get().getCorrecao() != null) {
			valor = parcelasContratos.get().getCorrecao().toString();
			valor = valor.replace(".",",");
			parcelasContratosOutString.setCorrecao(valor);			
		}
		
		if(parcelasContratos.get().getDesconto() != null) {
			valor = parcelasContratos.get().getDesconto().toString();
			valor = valor.replace(".",",");
			parcelasContratosOutString.setDesconto(valor);			
		}
		
		if(parcelasContratos.get().getValorPago() != null) {
			valor = parcelasContratos.get().getValorPago().toString();
			valor = valor.replace(".",",");
			parcelasContratosOutString.setValorPago(valor);			
		}
		
	    //return parcelasContratosOutput != null ? ResponseEntity.ok(parcelasContratosOutput) : ResponseEntity.notFound().build();
	    
	    return parcelasContratosOutString != null
	        ? ResponseEntity.ok(parcelasContratosOutString)
	        : ResponseEntity.notFound().build();
	}
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ParcelasContratos> 
		adicionar(@Valid @RequestBody ParcelasContratosInput parcelasContratosInput, HttpServletResponse response) {
		ParcelasContratos parcelasContratosSalva = parcelasContratosService.save(parcelasContratosInput);		
		return ResponseEntity.status(HttpStatus.CREATED).body(parcelasContratosSalva);
	}
	
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<ParcelasContratos> 
		atualizar(@PathVariable Long id, @Validated @RequestBody ParcelasContratosInput parcelasContratosInput) {
		ParcelasContratos parcelasContratosSalva = parcelasContratosService.atualizar(id, parcelasContratosInput);
		return ResponseEntity.ok(parcelasContratosSalva);
	}
	
	// Deletar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		
		if(!parcelasContratosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		parcelasContratosService.excluir(id);
		return ResponseEntity.noContent().build();		
		
	}
}
