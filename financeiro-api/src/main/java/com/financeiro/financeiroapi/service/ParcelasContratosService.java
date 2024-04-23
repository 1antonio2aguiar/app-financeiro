package com.financeiro.financeiroapi.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.financeiro.financeiroapi.input.ParcelasContratosInput;
import com.financeiro.financeiroapi.model.Contratos;
import com.financeiro.financeiroapi.model.ParcelasContratos;
import com.financeiro.financeiroapi.repository.ContratosRepository;
import com.financeiro.financeiroapi.repository.ParcelasContratosRepository;

@Service
public class ParcelasContratosService {
	@Autowired private ParcelasContratosRepository parcelasContratosRepository;
	@Autowired private ContratosRepository contratosRepository;
	
	BigDecimal valorPago 	 = new BigDecimal(0.00); 
	BigDecimal valorMulta    = new BigDecimal(0.00);  
	BigDecimal valorJuros 	 = new BigDecimal(0.00);
	BigDecimal valorCorrecao = new BigDecimal(0.00);
	BigDecimal valorDesconto = new BigDecimal(0.00);
	
	// Insert
	@Transactional 
	public ParcelasContratos save(ParcelasContratosInput parcelasContratosInput) {		
		//System.err.println("Esta no service 1 - " + parcelasContratosInput.getDataVencimento());
		
		ParcelasContratos parcelasContratos = new ParcelasContratos();
		BeanUtils.copyProperties(parcelasContratosInput, parcelasContratos, "id");
		
		// BUSCO ID DO CONTRATO E INSIRO EM PARCELAS CONTRATOS
		Contratos contratos = contratosRepository.findById(parcelasContratosInput.getContratoId()).get();
		parcelasContratos.setContratos(contratos);
		
		if(parcelasContratos.getDataPagamento() == null) {
			parcelasContratos.setValorPago(null);
		} else {
			
			if((parcelasContratos.getMulta() != null)) {
				valorMulta = parcelasContratos.getMulta();
			} else { valorMulta = new BigDecimal(0.00); };
			
			if(parcelasContratos.getJuros() != null) {
				valorJuros = parcelasContratos.getJuros();
			} else { valorJuros = new BigDecimal(0.00); };
			
			if(parcelasContratos.getCorrecao() != null ) {
				valorCorrecao = parcelasContratos.getCorrecao();
			} else { valorCorrecao = new BigDecimal(0.00); };
			
			if(parcelasContratos.getDesconto() != null ) {
				valorDesconto = parcelasContratos.getDesconto();
			} else { valorDesconto = new BigDecimal(0.00); };
			
			valorPago = parcelasContratos.getValor()
					.add(valorMulta)
					.add(valorJuros)
					.add(valorCorrecao)
					.subtract(valorDesconto);
			
			parcelasContratos.setValorPago(valorPago);
		}
		
		ParcelasContratos parcelasContratosSalva = parcelasContratosRepository.save(parcelasContratos);
		
		return parcelasContratosSalva;
	} 
	
	//Update
	@Transactional
	public ParcelasContratos atualizar(Long id, ParcelasContratosInput parcelasContratosInput) {
		//System.err.println("Esta no service 1 - " + parcelasContratosInput.getDataPagamento());
		
		ParcelasContratos parcelasContratosSalva = buscarPeloCodigo(id);

		BeanUtils.copyProperties(parcelasContratosInput, parcelasContratosSalva, "id");
		
		if(parcelasContratosSalva.getDataPagamento() == null) {
			parcelasContratosSalva.setValorPago(null);
		} else {
			
			if((parcelasContratosSalva.getMulta() != null)) {
				valorMulta = parcelasContratosSalva.getMulta();
			} else { valorMulta = new BigDecimal(0.00); };
			
			if(parcelasContratosSalva.getJuros() != null) {
				valorJuros = parcelasContratosSalva.getJuros();
			} else { valorJuros = new BigDecimal(0.00); };
			
			if(parcelasContratosSalva.getCorrecao() != null ) {
				valorCorrecao = parcelasContratosSalva.getCorrecao();
			} else { valorCorrecao = new BigDecimal(0.00); };
			
			if(parcelasContratosSalva.getDesconto() != null ) {
				valorDesconto = parcelasContratosSalva.getDesconto();
			} else { valorDesconto = new BigDecimal(0.00); };
			
			valorPago = parcelasContratosSalva.getValor()
					.add(valorMulta)
					.add(valorJuros)
					.add(valorCorrecao)
					.subtract(valorDesconto);
			
			parcelasContratosSalva.setValorPago(valorPago);
		}
		
		return parcelasContratosRepository.save(parcelasContratosSalva);
	}
		
	public ParcelasContratos buscarPeloCodigo(Long id) {
		Optional<ParcelasContratos> parcelasContratosSalva = parcelasContratosRepository.findById(id);
		if (parcelasContratosSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return parcelasContratosSalva.get();
	}
	
	@Transactional 
	public void excluir(Long id) {
		parcelasContratosRepository.deleteById(id);
	}
	
	@Transactional
	public void excuirPorContrato(Long id) {
		parcelasContratosRepository.deleteByContratosId(id);
	}
}
