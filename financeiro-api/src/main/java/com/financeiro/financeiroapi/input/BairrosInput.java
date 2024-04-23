package com.financeiro.financeiroapi.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BairrosInput {
	
private Long id;
	
	@Valid
	@NotNull
	private Long cidadesId;
	
	@NotNull
	private String nome;
	
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}
}
