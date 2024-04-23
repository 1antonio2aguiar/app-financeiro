package com.financeiro.financeiroapi.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class PessoasDTO {
	
	private Long id;
	private String nome;
	private String tipoPessoa;
	private String cpfCnpj;
	private Date dataNascimento;
	private String observacao;

}
