package com.financeiro.financeiroapi.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CidadesInput {
	
	private Long id;
	
	@Valid
	@NotNull
	private Long estadosId;
	
	@NotNull
	private String nome;
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}
}
