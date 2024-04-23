package com.financeiro.financeiroapi.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.financeiro.financeiroapi.model.enums.TipoContato;

import lombok.Data;

@Data
public class ContatosInput {
	
	private Long id;
	
	@NotNull
	private TipoContato tipoContato;
	
	@Valid
	@NotNull
	private Long pessoa;
	
	@NotNull
	private String contato;

}
