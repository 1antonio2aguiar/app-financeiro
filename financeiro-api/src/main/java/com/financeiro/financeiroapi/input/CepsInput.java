package com.financeiro.financeiroapi.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.financeiro.financeiroapi.model.enums.IdentificacaoCep;

import lombok.Data;

@Data
public class CepsInput {
	
	private Long id;
	
	@NotNull
	private String cep;
	
	@NotNull
	private Long numeroIni;
	
	@NotNull
	private Long numeroFin;
	
	@NotNull
	private IdentificacaoCep identificacao;
	
	@Valid
	@NotNull
	private Long bairrosId;
	
	@Valid
	@NotNull
	private Long logradourosId;
	
	
}
