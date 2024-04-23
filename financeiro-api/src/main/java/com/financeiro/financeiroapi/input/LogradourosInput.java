package com.financeiro.financeiroapi.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LogradourosInput {
	
	private Long id;
	
	@NotNull
	private String nome;
	
	@Valid
	@NotNull
	private Long cidadesId;
	
	@Valid
	@NotNull
	private Long tiposLogradourosId;
	
	private String observacao;
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}
	
	public String getObservacao() {
		return observacao == null ? null :observacao.toUpperCase();
	}
}
