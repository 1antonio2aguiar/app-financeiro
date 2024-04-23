package com.financeiro.financeiroapi.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.financeiro.financeiroapi.model.enums.TipoEndereco;

import lombok.Data;

@Data
public class EnderecosInput {
	
	private Long id;
	
	@NotNull
	private TipoEndereco tipoEndereco;
	
	@Valid
	@NotNull
	private Long bairro;
	
	@Valid
	@NotNull
	private Long logradouro;
	
	@Valid
	@NotNull
	private Long cep;
	
	@Valid
	@NotNull
	private Long pessoa;
	
	@NotNull
	private Long numero;
	private String complemento;	
}
