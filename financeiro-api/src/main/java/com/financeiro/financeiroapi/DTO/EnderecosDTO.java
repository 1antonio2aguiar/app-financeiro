package com.financeiro.financeiroapi.DTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.financeiro.financeiroapi.model.enums.StatusEndereco;

import lombok.Data;

@Data
public class EnderecosDTO {
	
	private Long id;
	private String nomePessoa;
	private Bairro bairro;
	private Logradouro logradouro;
	private Long numero;
	private String complemento;
	private BigDecimal taxa;
	private StatusEndereco status;
	private OffsetDateTime cadastro;
	private OffsetDateTime alteracao;

}
