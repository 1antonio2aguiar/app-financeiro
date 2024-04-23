package com.financeiro.financeiroapi.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.financeiroapi.filter.ContratosFilter;
import com.financeiro.financeiroapi.input.ContratosInput;
import com.financeiro.financeiroapi.model.Contratos;
import com.financeiro.financeiroapi.outputs.ContratosOutput;
import com.financeiro.financeiroapi.repository.ContratosRepository;
import com.financeiro.financeiroapi.service.ContratosService;
import com.financeiro.financeiroapi.service.ParcelasContratosService;
import com.financeiro.financeiroapi.model.enums.SituacaoEnum;
 
@RestController
@RequestMapping("/contratos")
public class ContratosController {
	
	@Autowired public ContratosRepository contratosRepository;
	@Autowired public ContratosService contratosService;
	@Autowired public ParcelasContratosService parcelascontratosService;
	                  
	// Lista de Contratos com Paginacao
	@GetMapping
	public Page<Contratos> pesquisar(ContratosFilter contratosFilter, Pageable pageable) {
		//Page<Contratos> contratoslist = contratosRepository.filtrar(contratosFilter, pageable);
		//contratoslist.stream().forEach(e -> System.err.println(SituacaoEnum.toSituacaoEnum(e.getSituacao())));
		//return contratoslist;
		
	    return contratosRepository.filtrar(contratosFilter, pageable);
	}
	
	// Edit - Tonhas busca contrato por id.
	@GetMapping("/{id}")
	public ResponseEntity<ContratosOutput> BuscarPorId(@PathVariable Long id) {
		
		ContratosOutput contratosOutput = null;
		Optional<Contratos> contratos = contratosRepository.findById(id);
		
		if(contratos.isPresent()) {
			
			contratosOutput = new ContratosOutput();
		 	
			if(contratos.get().getDataContrato() != null){
				SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
				String dataFormatada = null;
				dataFormatada = formatoDesejado.format(contratos.get().getDataContrato());
				contratosOutput.setDataContrato(dataFormatada);
			}
			
			if(contratos.get().getDataInicio() != null){
				SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
				String dataFormatada = null;
				dataFormatada = formatoDesejado.format(contratos.get().getDataInicio());
				contratosOutput.setDataInicio(dataFormatada);
			}
			
			if(contratos.get().getDataFim() != null){
				SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd/MM/yyyy");
				String dataFormatada = null;
				dataFormatada = formatoDesejado.format(contratos.get().getDataFim());
				contratosOutput.setDataFim(dataFormatada);
			}
			
			BeanUtils.copyProperties(contratos.get(), contratosOutput);
			contratosOutput.setTiposGruposFinanceiros(contratos.get().getTiposGruposFinanceiros());
			contratosOutput.setPessoasContratante(contratos.get().getPessoaContratante());
			contratosOutput.setPessoasContratado(contratos.get().getPessoaContratado());
			contratosOutput.setContratanteId(contratos.get().getPessoaContratante().getId());
			contratosOutput.setContratadoId(contratos.get().getPessoaContratado().getId());
			contratosOutput.setTipoGrupoFinanceiroId(contratos.get().getTiposGruposFinanceiros().getId());
			
			String valorContrato = contratos.get().getValorContrato().toString();
			valorContrato = valorContrato.replace(".",",");
			contratosOutput.setValorContrato(valorContrato);
			
		} 
		
		//Gson gson = new Gson();
		//String json = gson.toJson(contratosOutput.getTiposGruposFinanceiros());
		//System.err.println("Entrou aqui " + json);
		return contratosOutput != null ? ResponseEntity.ok(contratosOutput) : ResponseEntity.notFound().build();
	}
	
	// Edit
	/*@GetMapping("/{id}")
	public ResponseEntity<Contratos> buscarPeloCodigo(@PathVariable Long id) {
		System.err.println("OLHA ONDE ELE VÊIO !! " );
	    Optional<Contratos> contratos = contratosRepository.findById(id);
	    return contratos != null
	        ? ResponseEntity.ok(contratos.get())
	        : ResponseEntity.notFound().build();
	}*/
	
	// AA - Inserir
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Contratos> adicionar(@Valid @RequestBody ContratosInput contratosInput, HttpServletResponse response) {
		Contratos contratosSalva = contratosService.save(contratosInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(contratosSalva);
	}
	
	// Alterar
	@PutMapping("/{id}")
	public ResponseEntity<Contratos> atualizar(@PathVariable Long id, @Validated @RequestBody ContratosInput contratosInput) {
		Contratos contratosSalva = contratosService.atualizar(id, contratosInput);
		return ResponseEntity.ok(contratosSalva);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		if(!contratosRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		parcelascontratosService.excuirPorContrato(id);
		contratosService.excluir(id);
		
		return ResponseEntity.noContent().build();				
	}
}
