package com.financeiro.financeiroapi.outputs;

import com.financeiro.financeiroapi.model.Bairros;
import com.financeiro.financeiroapi.model.Cidades;
import com.financeiro.financeiroapi.model.Logradouros;
import com.financeiro.financeiroapi.model.enums.IdentificacaoCep;

import lombok.Data;

@Data
public class CepsOutput {
	
	private Long id;
	private String cep;
	private Long numeroIni;
	private Long numeroFin;
	private IdentificacaoCep identificacao;
	private Cidades cidades;
	private Bairros bairros;
	private Logradouros logradouros;
	
}
