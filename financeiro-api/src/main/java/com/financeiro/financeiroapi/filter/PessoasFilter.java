package com.financeiro.financeiroapi.filter;

import java.sql.Date;

import lombok.Data;

@Data
public class PessoasFilter {
	
	private Long id;
	private String nome;
	private String cpfCnpj;
	private Date dataRegistro;

}
