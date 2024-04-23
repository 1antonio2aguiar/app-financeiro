package com.financeiro.financeiroapi.input;

import java.math.BigDecimal;
import java.sql.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ParcelasContratosInput {
	
	private Long id ; 
	
	@Valid
	@NotNull
	private Long contratoId ;
	
	@Valid
	@NotNull
	private Long parcela ;
	
	@Valid
	@NotNull
	private BigDecimal valor ;
	private BigDecimal juros ;
	private BigDecimal multa ;
	private BigDecimal correcao ;
	private BigDecimal desconto ;
	private BigDecimal valorPago ;
	
	@Valid
	@NotNull
	private Date dataVencimento ;
	private Date dataPagamento ;
	private String documento ;
	private Long bancoId ;
	private String observacao ; 
	
}
